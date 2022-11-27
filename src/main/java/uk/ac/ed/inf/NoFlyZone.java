package uk.ac.ed.inf;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class NoFlyZone {
    @JsonProperty("name")
    public String name;
    @JsonProperty("coordinates")
    public List<double[]> coordinates;


    public NoFlyZone(){}

    public String getName(){
        return this.name;
    }

    public LngLat[] getCoordinates(){
        LngLat[] lngLatArray = new LngLat[coordinates.size()];
        for(double[] coordinate: coordinates){
            LngLat lngLat = new LngLat(coordinate[0], coordinate[1]);
            lngLatArray[coordinates.indexOf(coordinate)] = lngLat;
            }

        return  lngLatArray;
    }



}
