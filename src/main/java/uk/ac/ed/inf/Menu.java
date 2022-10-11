package uk.ac.ed.inf;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Menu {
    @JsonProperty("name")
    public String itemName;

    @JsonProperty("priceInPence")
    public int price;

    public Menu(){}

}
