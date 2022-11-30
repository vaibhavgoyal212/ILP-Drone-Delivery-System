package uk.ac.ed.inf;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class NoFlyZone {
    @JsonProperty("name")
    public String name;
    @JsonProperty("coordinates")
    public List<double[]> coordinates;


    public NoFlyZone(){}

    /**
     * getter for the name associated with a no-fly zone
     * @return the name of the no-fly zone
     */
    public String getName(){
        return this.name;
    }

    /**
     * getter for the coordinates associated with a no-fly zone
     * @return the coordinates of the no-fly zone as a list of LngLat objects
     */
    public LngLat[] getCoordinates(){
        LngLat[] lngLatArray = new LngLat[coordinates.size()];
        for(double[] coordinate: coordinates){
            LngLat lngLat = new LngLat(coordinate[0], coordinate[1]);
            lngLatArray[coordinates.indexOf(coordinate)] = lngLat;
            }

        return  lngLatArray;
    }



}
