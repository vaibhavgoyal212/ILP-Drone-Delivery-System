package uk.ac.ed.inf;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties("name")
public record LngLat(
        @JsonProperty("longitude")
        double lng ,
        @JsonProperty ("latitude")
        double lat) {

    /**
     * This field stores the distance tolerance of the drone
     */
    private static final double DIST_TOLERANCE= 0.00015;


    /**
     * Implementation of euclidean distance between points
     * @param point point from which to calculate distance
     * @return double value depicting the distance
     */
    public double distanceTo(LngLat point){
        return Math.sqrt(((point.lng- this.lng)*(point.lng- this.lng)) + ((point.lat- this.lat)*(point.lat- this.lat)));
    }

    /**
     * This method checks if the distance between two points is less than the tolerance
     * @param point point from which to calculate distance
     * @return boolean value depicting if the distance is less than the tolerance
     */
    public boolean closeTo(LngLat point){
        return distanceTo(point) <= DIST_TOLERANCE;
    }

    /**
     * This method gives the next point in the given direction that the drone will travel to
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
