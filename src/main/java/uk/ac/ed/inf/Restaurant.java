package uk.ac.ed.inf;


import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;

public class Restaurant {
    @JsonProperty ("name")
    public String name;
    @JsonProperty("longitude")
    public double lng;
    @JsonProperty("menu")
    private Menu[] menu;
    @JsonProperty("latitude")
    public double lat;

    /**
     * Field to store a map of the menu items and their prices for a restaurant
     */
    private HashMap<String, Integer> menuMap;

    /**
     * Field to store the number of moves to deliver to a restaurant
     */
    private int movesToDeliver = 0;



    public Restaurant(){}

    /**
     * Method to get the hashmap of menu items and their prices for the restaurant
     * @return hashmap of menu items and their prices
     */
    public HashMap<String, Integer> getMenuMap(){
        if(menuMap == null){
            menuMap = new HashMap<>();
            for(Menu item: menu){
                menuMap.put(item.itemName, item.price);
            }
        }
        return menuMap;
    }

    /**
     * getter for list of menu items for a restaurant
     * @return list of menu items
     */
    public Menu[] getMenu(){
        return this.menu;
    }

    /**
     * getter for the coordinates of a restaurant as a LngLat object
     * @return LngLat object of the restaurant's coordinates
     */
    public LngLat getLngLat(){
        return new LngLat(lng, lat);
    }

    /**
     * setter for the number of moves to deliver to a restaurant (calculated and set in the drone class)
     * @param moves number of moves to deliver to a restaurant
     */
    public void setMovesToDeliver(int moves){
        this.movesToDeliver = moves;
    }

    /**
     * getter for the number of moves to deliver to a restaurant
     * @return number of moves to deliver to a restaurant
     */
    public int getMovesToDeliver() {
        return movesToDeliver;
    }
}

