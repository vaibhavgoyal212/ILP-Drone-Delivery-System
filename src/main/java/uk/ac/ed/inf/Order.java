package uk.ac.ed.inf;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Order {
    @JsonProperty ("orderNo")
    public String orderNo;
    @JsonProperty("orderDate")
    public String orderDate;
    @JsonProperty("customer")
    public String customer;
    @JsonProperty("creditCardNumber")
    private String creditCardNumber;
    @JsonProperty("creditCardExpiry")
    private String creditCardExpiry;
    @JsonProperty("cvv")
    private String cvv;
    @JsonProperty("priceTotalInPence")
    public int priceTotalInPence;
    @JsonProperty("orderItems")
    public String[] orderItems;

    /**
     * This field stores the outcome of the order.
     * The outcome is initialised to INVALID but is overwritten depending on further evaluation.
     */
    private OrderOutcome outcome = OrderOutcome.INVALID;

    /**
     * This field stores the restaurant that the order belongs to
     */
    public Restaurant restaurant=null;

    public Order(){}

    /**
     * this method returns whether the combination of pizzas in the order is valid or not
     * this method will also set the restaurant field of the order if the combination is valid
     * combination of pizzas is valid if all the pizzas in the order are from the same restaurant
     * @param restaurants list of all restaurants
     * @param pizzas list of all pizzas in an order
     * @return true if the combination of pizzas is valid, false otherwise
     */
    public boolean invalidCombination(Restaurant[] restaurants, String[] pizzas) {

        for(Restaurant restaurant: restaurants){
            if(checkCombination(restaurant, pizzas)){
                this.restaurant = restaurant;
                return true;
            }
        }
        return false;
    }


    /**
     * this is a helper function to check if the combination of pizzas exists for a restaurant
     * @param restaurant Restaurant for which to check
     * @param pizzas the combination of pizzas
     * @return True if the combination exists for the restaurant, false otherwise
     */
    private boolean checkCombination(Restaurant restaurant, String[] pizzas){
        List<String> pizzasList = Arrays.asList(pizzas);
        HashMap<String, Integer> menuMap = restaurant.getMenuMap();
        return (menuMap.keySet().containsAll(pizzasList));
    }


    /**
     * helper function to calculate total cost of pizzas by fetching price from menus
     * @param restaurant restaurant for which to calculate cost of pizzas
     * @param pizzas combination of pizzas to be ordered
     * @return integer value depicting total cost (without £1 delivery)
     */
    private int getOrderedPizzasCost(Restaurant restaurant, String[] pizzas){
        int cost=0;
        for(String pizza : pizzas){
            cost += restaurant.getMenuMap().get(pizza);
        }
        return cost;
    }

    /**
     * this method checks if the credit card number is valid or not
     * employes Luhn's algorithm to check if the credit card number is valid
     * @param creditCardNumber credit card number associated with the order
     * @return true if the credit card number is valid, false otherwise
     */
    private boolean checkCreditCardNumber(String creditCardNumber){
        if(creditCardNumber.length()!=16 || creditCardNumber.isBlank() || creditCardNumber.contains(" ")) {
            return false;
        }
        int sum = 0;
        boolean alternate = false;
        for (int i = creditCardNumber.length() - 1; i >= 0; i--)
        {
            int n = Integer.parseInt(creditCardNumber.substring(i, i + 1));
            if (alternate)
            {
                n *= 2;
                if (n > 9)
                {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        return (sum % 10 == 0);
    }

    /**
     * this method checks if the credit card expiry date is valid or not
     * method initially matches the date to a regular expression
     * if the date matches the regular expression, it checks if the order date is after the expiry date or not
     * @param cardDate credit card expiry date associated with the order
     * @return true if the credit card expiry date is valid, false otherwise
     */
    private boolean checkCardDate(String cardDate){
        if(cardDate.length()!=5 || cardDate.isBlank() || !cardDate.matches("([0-9]{2})/([0-9]{2})")){
            return false;
        }
        String[] date = cardDate.split("/");
        int month = Integer.parseInt(date[0]);
        int year = Integer.parseInt(date[1]);
        if(month>12 || month<1){
            return false;
        }
        LocalDate expiry = LocalDate.parse("01/"+cardDate , DateTimeFormatter.ofPattern(new SimpleDateFormat("dd/MM/yy").toPattern()));
        expiry = expiry.withDayOfMonth(expiry.lengthOfMonth());
        LocalDate orderDate = LocalDate.parse(this.orderDate, DateTimeFormatter.ofPattern(new SimpleDateFormat("yyyy-MM-dd").toPattern()));
        return !orderDate.isAfter(expiry);
    }

    /**
     * this method checks if the cvv is valid or not
     * valid cvv is a 3-digit number and not blank
     * @param cvv cvv associated with the order
     * @return true if the cvv is valid, false otherwise
     */
    private boolean checkCVV(String cvv){
        return (cvv.length() == 3) && !cvv.isBlank() && !cvv.contains(" ");
    }

    /**
     * this method checks if all the pizzas in an order exist
     * @param restaurants array of all participating restaurants
     * @param pizzas combination of pizzas in an order
     * @return true if all the pizzas exist, false if any of the pizzas does not exist for any restaurant
     */
    private boolean pizzaValidity(Restaurant[] restaurants, String[] pizzas){
        List<String> pizzasList = Arrays.asList(pizzas);
        List<String> allPizzas = new ArrayList<>();
        for(Restaurant restaurant: restaurants){
            allPizzas.addAll(restaurant.getMenuMap().keySet());
        }
        return allPizzas.containsAll(pizzasList);

    }

    /**
     * this method checks the constraints on the number of pizzas in an order
     * number of pizzas can't be less than 1 or more than 4
     * @param pizzas combination of pizzas in an order
     * @return true if the number of pizzas is valid, false otherwise
     */
    private boolean validPizzaCount(String[] pizzas){
        return pizzas.length<=4 && pizzas.length>=1;
    }

    /**
     * this method checks if the total cost associated with the order is equal to the sum of cost of pizzas and delivery cost
     * @return true if the total cost is valid, false otherwise
     */
    private boolean validTotalPrice(){
        return this.priceTotalInPence==getOrderedPizzasCost(this.restaurant, this.orderItems)+100;
    }

    /**
     * this method checks all the above conditions and returns true if all the conditions are satisfied
     * this method also sets the outcome of the order depending on the conditions
     * @param restaurants array of all participating restaurants
     * @return true if all the conditions are satisfied, false otherwise
     */
    public boolean isOrderValid(Restaurant[] restaurants){
        boolean res = invalidCombination(restaurants, this.orderItems);


        if(!pizzaValidity(restaurants, this.orderItems)){
            this.outcome = OrderOutcome.INVALIDPIZZANOTDEFINED;
            return false;
        }
        if(!invalidCombination(restaurants, this.orderItems)){
            this.outcome = OrderOutcome.INVALIDPIZZACOMBINATIONMULTIPLESUPPLIERS;
            return false;
        }

        if(!checkCreditCardNumber(this.creditCardNumber)){
            this.outcome = OrderOutcome.INVALIDCARDNUMBER;
            return false;
        }
        if(!checkCardDate(this.creditCardExpiry)){
            this.outcome = OrderOutcome.INVALIDEXPIRYDATE;
            return false;
        }
        if(!checkCVV(this.cvv)){
            this.outcome = OrderOutcome.INVALIDCVV;
            return false;
        }
        if(!validTotalPrice()){
            this.outcome = OrderOutcome.INVALIDTOTAL;
            return false;
        }

        if(!validPizzaCount(this.orderItems)){
            this.outcome = OrderOutcome.INVALIDPIZZACOUNT;
            return false;
        }
        return true;
    }

    /**
     * setter for the outcome of the order
     * @param outcome outcome of the order to be set
     */
    public void setOutcome(OrderOutcome outcome) {
        this.outcome = outcome;
    }

    /**
     * getter for the outcome of the order
     * @return outcome of the order
     */
    public OrderOutcome getOutcome() {
        return outcome;
    }












}
