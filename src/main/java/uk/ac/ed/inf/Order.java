package uk.ac.ed.inf;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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
     * @param restaurants array of all participating restaurants
     * @param pizzas combination of pizzas for which to calculate delivery cost
     * @return integer value depicting total cost of delivering the given combination of pizzas
     * @throws InvalidPizzaCombinationException if the combination of pizzas entered don't belong to any restaurant menu
     */
    public int getDeliveryCost(Restaurant[] restaurants, String[] pizzas) throws InvalidPizzaCombinationException {
       List<Boolean> allRestaurantsCheck = new ArrayList<>();
       for(Restaurant restaurant: restaurants){
           allRestaurantsCheck.add(checkCombination(restaurant,pizzas));
       }
       if(!allRestaurantsCheck.contains(true)){
           throw new InvalidPizzaCombinationException("The combination of pizzas you have entered is incorrect");
       }
       return (getOrderedPizzasCost(restaurants,pizzas)+100);

    }

    /**
     * this is a helper function to check if the combination of pizzas exists for a restaurant
     * @param restaurant Restaurant for which to check
     * @param pizzas the combination of pizzas
     * @return True if the combination exists for the restaurant, false otherwise
     */
    private boolean checkCombination(Restaurant restaurant, String[] pizzas){
        List<String> pizzasList = Arrays.asList(pizzas);
        boolean check = false;
        List<String> menuPizzas = new ArrayList<>();
        Menu[] menuItems = restaurant.getMenu();
        for(Menu menuItem : menuItems){
            menuPizzas.add(menuItem.itemName);
        }
        if(menuPizzas.containsAll(pizzasList)){
            check=true;
        }
        return check;
    }


    /**
     * helper function to calculate total cost of pizzas by fetching price from menus
     * @param restaurants array of all participating restaurants
     * @param pizzas combination of pizzas to be ordered
     * @return integer value depicting total cost (without £1 delivery)
     */
    private int getOrderedPizzasCost(Restaurant[] restaurants, String[] pizzas){
        int cost=0;
        for(Restaurant restaurant : restaurants){
            if (checkCombination(restaurant,pizzas)){
                for(String pizza : pizzas){
                    Menu[] menuItems = restaurant.getMenu();
                    for(Menu menuItem : menuItems){
                        if(menuItem.itemName.equals(pizza)){
                            cost+= menuItem.price;
                        }
                    }
                }
            }
        }
        return cost;
    }
}
