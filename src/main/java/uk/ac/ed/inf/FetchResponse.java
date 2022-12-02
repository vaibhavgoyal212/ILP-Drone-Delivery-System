package uk.ac.ed.inf;
import com. fasterxml . jackson . databind . ObjectMapper ;
import java . io . IOException ;
import java . net .URL;

public class FetchResponse {
    /**
     * This field stores the base URL of the API
     * the base url is combined with the REST endpoint in order to make a request to the API and retrieve the data
     */
    private static String baseURL;

    private FetchResponse(){}

    public static void setUrl(String url){
        baseURL = url;
    }

    /**
     * @return All points in the central area response as array of LngLat
     */
    public static LngLat[] getCentralArea(){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            URL url = new URL(baseURL+ "/centralArea" );

            return objectMapper.readValue(url, LngLat[].class);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return all orders in the orders endpoint as an array of Order class objects
     */
    public static Order[] getOrders(){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            URL url = new URL(baseURL+"/orders");

            return objectMapper.readValue(url, Order[].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     *
     * @return all restaurants in the restaurants endpoint as an array of Restaurant class objects
     */
    public static Restaurant[] getRestaurants() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            URL url = new URL(baseURL + "/restaurants");

            return objectMapper.readValue(url, Restaurant[].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @return all noFlyZones in the noFlyZones endpoint as an array of NoFlyZone class objects
     */
    public static NoFlyZone[] getNoFlyZones() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            URL url = new URL(baseURL + "/noFlyZones");

            return objectMapper.readValue(url, NoFlyZone[].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }





}
