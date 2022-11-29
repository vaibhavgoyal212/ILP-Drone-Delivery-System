package uk.ac.ed.inf;
import com. fasterxml . jackson . databind . ObjectMapper ;
import java . io . IOException ;
import java . net .URL;

public class FetchResponse {
    private static FetchResponse instance;

    private static String baseURL;

    private FetchResponse(){}

    public void setUrl(String url){
        baseURL = url;
    }

    public static FetchResponse getInstance(){
        if(instance == null){
            instance = new FetchResponse();
        }
        return instance;
    }

    /**
     * @return All points in the central area response as array of LngLat
     */
    public LngLat[] getCentralArea(){
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
    public Order[] getOrders(){
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
    public Restaurant[] getRestaurants() {
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
    public NoFlyZone[] getNoFlyZones() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            URL url = new URL(baseURL + "/noFlyZones");

            return objectMapper.readValue(url, NoFlyZone[].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }





}
