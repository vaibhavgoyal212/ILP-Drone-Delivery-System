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

    private boolean checkCombination(Restaurant restaurant, String[] pizzas){
        List<String> pizzasList = Arrays.asList(pizzas);
        boolean check = false;
        List<String> menuPizzas = new ArrayList<>();
        Menu[] menuItems = restaurant.getMenuItems();
        for(Menu menuItem : menuItems){
            menuPizzas.add(menuItem.itemName);
        }
        if(menuPizzas.containsAll(pizzasList)){
            check=true;
        }
        return check;
    }

    private int getOrderedPizzasCost(Restaurant[] restaurants, String[] pizzas){
        int cost=0;
        for(Restaurant restaurant : restaurants){
            if (checkCombination(restaurant,pizzas)){
                for(String pizza : pizzas){
                    Menu[] menuItems = restaurant.getMenuItems();
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
