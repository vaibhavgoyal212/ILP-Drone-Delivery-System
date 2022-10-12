package uk.ac.ed.inf;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;

public class Restaurant {
    @JsonProperty ("name")
    public String name;
    @JsonProperty("longitude")
    public double lng;
    @JsonProperty("menu")
    private Menu[] menu;
    @JsonProperty("latitude")
    public double lat;

    public Restaurant(){}

    public static Restaurant[] getRestaurantsFromRestServer(URL serverBaseAddress){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            URL url = new URL(serverBaseAddress+"restaurants");

            return objectMapper.readValue(url, Restaurant[].class);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Menu[] getMenu(){
        return this.menu;
    }

}

