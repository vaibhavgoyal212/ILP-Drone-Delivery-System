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

    public OrderOutcome outcome;
    public Restaurant restaurant=null;

    public Order(){}

    public boolean invalidCombination(Restaurant[] restaurants, String[] pizzas) {
        boolean check = false;
        for(Restaurant restaurant: restaurants){
            if(checkCombination(restaurant, pizzas)){
                this.restaurant = restaurant;
                check = true;
            }
        }
        return check;
    }


    /**
     * @param restaurants array of all participating restaurants
     * @param pizzas combination of pizzas for which to calculate delivery cost
     * @return integer value depicting total cost of delivering the given combination of pizzas
     * @throws InvalidPizzaCombinationException if the combination of pizzas entered don't belong to any restaurant menu
     */
    public int getDeliveryCost(Restaurant[] restaurants, String[] pizzas) throws InvalidPizzaCombinationException {
        if(invalidCombination(restaurants, pizzas)){
            return getOrderedPizzasCost(this.restaurant,pizzas)+100;
        }
        else{
            throw new InvalidPizzaCombinationException("the combination of pizzas entered don't belong to any restaurant menu");
        }
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

    private boolean checkCreditCardNumber(String creditCardNumber){
        if(creditCardNumber.length()>16 || creditCardNumber.length()<13 || creditCardNumber.isBlank() || creditCardNumber.contains(" ")) {
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

    private boolean checkCVV(String cvv){
        return (cvv.length() == 3) && !cvv.isBlank();
    }

    private boolean pizzaValidity(Restaurant[] restaurants, String[] pizzas){
        List<String> pizzasList = Arrays.asList(pizzas);
        List<String> allPizzas = new ArrayList<>();
        for(Restaurant restaurant: restaurants){
            allPizzas.addAll(restaurant.getMenuMap().keySet());
        }
        return allPizzas.containsAll(pizzasList);

    }

    private boolean validPizzaCount(String[] pizzas){
        return pizzas.length<=4 && pizzas.length>=1;
    }

    private boolean validTotalPrice(){
        return this.priceTotalInPence==getOrderedPizzasCost(this.restaurant, this.orderItems)+100;
    }

    public boolean isOrderValid(Restaurant[] restaurants, String[] pizzas){
        boolean res = invalidCombination(restaurants, pizzas);

        if(!res){
            if(!pizzaValidity(restaurants, pizzas)){
                this.outcome = OrderOutcome.INVALIDPIZZANOTDEFINED;
                return false;
            }
            if(!invalidCombination(restaurants, pizzas)){
                this.outcome = OrderOutcome.INVALIDPIZZACOMBINATIONMULTIPLESUPPLIERS;
                return false;
            }

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

        if(!validPizzaCount(pizzas)){
            this.outcome = OrderOutcome.INVALIDPIZZACOUNT;
            return false;
        }


        return true;
    }











}
