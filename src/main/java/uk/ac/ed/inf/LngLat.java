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

    public boolean inCentralArea(){

        FetchResponse response = FetchResponse.getInstance();

        LngLat[] points= response.getCentralArea("centralArea");
        int i;
        int j;
        boolean result = false;
        for (i = 0, j = points.length - 1; i < points.length; j = i++) {
            if ((points[i].lat > this.lat) != (points[j].lat > this.lat) &&
                    (this.lng < (points[j].lng - points[i].lng) * (this.lat - points[i].lat) / (points[j].lat-points[i].lat) + points[i].lng)) {
                result = !result;
            }
        }
        return result;
    }
//    boolean result = false;
//        for (int i=0; i< points.length; i++){
//        double x0 = points[i].lng;
//        double y0=points[i].lat;
//        double x1 = points[(i+1) % points.length].lng;
//        double y1=points[(i+1) % points.length].lat;
//        if(!((Math.min(y0,y1)<this.lat) && (this.lat<=Math.max(y0,y1)))){
//            continue;
//        }
//        if(this.lng<Math.min(x0,x1)){
//            continue;
//        }
//        double current_x;
//        if(x0==x1){current_x=x0;}
//        else{current_x = x0+((this.lat-y0)*(x1-x0)/(y1-y0));}
//        result = this.lng<current_x;
//
//    }
//
//        return result;

    public double distanceTo(LngLat point){
        return Math.sqrt(((point.lng- this.lng)*(point.lng- this.lng)) + ((point.lat- this.lat)*(point.lat- this.lat)));
    }

    public boolean closeTo(LngLat point){
        return distanceTo(point) <= DIST_TOLERANCE;
    }

    public LngLat nextPosition(Compass direction){
        double angleRad = Math.toRadians(direction.getDegreeVal());

        double deltaLat = Math.sin(angleRad)*DIST_TOLERANCE;
        double deltaLng = Math.cos(angleRad)*DIST_TOLERANCE;

        return new LngLat(this.lng+deltaLng, this.lat+deltaLat);

    }

}
