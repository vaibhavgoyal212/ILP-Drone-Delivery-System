package uk.ac.ed.inf;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties("name")
public record LngLat(
        @JsonProperty("longitude")
        double lng ,
        @JsonProperty ("latitude")
        double lat) {

    private static final double DIST_TOLERANCE= 0.00015;

    /**
     * Implementation of RayCasting algorithm to find if point is in a polygon
     *
     * @return boolean value depicting whether a point is in Central area or not
     */
    public boolean inCentralArea(){

        FetchResponse response = FetchResponse.getInstance();

        LngLat[] points= response.getCentralArea("centralArea");
        int i;
        int j;
        boolean result = false;
        for (i = 0, j = points.length - 1; i < points.length; j = i++) {
            if ((points[i].lat >= this.lat) != (points[j].lat >= this.lat) &&
                    (this.lng <= (points[j].lng - points[i].lng) * (this.lat - points[i].lat) / (points[j].lat-points[i].lat) + points[i].lng)) {
                result = !result;
            }
        }
        return result;
    }


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
