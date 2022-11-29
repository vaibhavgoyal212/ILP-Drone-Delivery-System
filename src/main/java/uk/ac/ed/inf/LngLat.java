package uk.ac.ed.inf;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties("name")
public record LngLat(
        @JsonProperty("longitude")
        double lng ,
        @JsonProperty ("latitude")
        double lat) {

    private static final double DIST_TOLERANCE= 0.00015;


    /**
     * Implementation of euclidean distance between points
     * @param point point from which to calculate distance
     * @return double value depicting the distance
     */
    public double distanceTo(LngLat point){
        return Math.sqrt(((point.lng- this.lng)*(point.lng- this.lng)) + ((point.lat- this.lat)*(point.lat- this.lat)));
    }

    public boolean closeTo(LngLat point){
        return distanceTo(point) <= DIST_TOLERANCE;
    }

    /**
     *
     * @param direction compass direction in which to move
     * @return LngLat object depicting next position
     */
    public LngLat nextPosition(Compass direction){
        double angleRad = Math.toRadians(direction.getDegreeVal());

        double deltaLat = Math.sin(angleRad)*DIST_TOLERANCE;
        double deltaLng = Math.cos(angleRad)*DIST_TOLERANCE;

        return new LngLat(this.lng+deltaLng, this.lat+deltaLat);

    }



}
