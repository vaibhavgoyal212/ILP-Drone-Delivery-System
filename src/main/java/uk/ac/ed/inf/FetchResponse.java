package uk.ac.ed.inf;
import com. fasterxml . jackson . databind . ObjectMapper ;
import java . io . IOException ;
import java . net .URL;

public class FetchResponse {
    private static final FetchResponse instance = new FetchResponse();

    private static final String baseURL = "https://ilp-rest.azurewebsites.net/";

    private FetchResponse(){}

    public static FetchResponse getInstance(){
        return instance;
    }

    public LngLat[] getCentralArea(String endpoint){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            URL url = new URL(baseURL+endpoint);

            return objectMapper.readValue(url, LngLat[].class);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Order[] getOrders(String endpoint){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            URL url = new URL(baseURL+endpoint);

            return objectMapper.readValue(url, Order[].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }





}
