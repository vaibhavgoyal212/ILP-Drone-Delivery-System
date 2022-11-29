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
    private HashMap<String, Integer> menuMap;

    public int movesToDeliver = 0;



    public Restaurant(){}
    public HashMap<String, Integer> getMenuMap(){
        if(menuMap == null){
            menuMap = new HashMap<>();
            for(Menu item: menu){
                menuMap.put(item.itemName, item.price);
            }
        }
        return menuMap;
    }

    public Menu[] getMenu(){
        return this.menu;
    }

    public LngLat getLngLat(){
        return new LngLat(lng, lat);
    }

    public void setMovesToDeliver(int moves){
        this.movesToDeliver = moves;
    }

}

