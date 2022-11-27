package uk.ac.ed.inf;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Hello world!
 *
 */

public class App 
{
    public static  List<LngLat> printPath(WorldDataStorage storage){
        LngLat start = new LngLat(-3.186874, 55.944494);
        LngLat goal = new LngLat( -3.202541470527649,  55.943284737579376);
        AStarSearch search = new AStarSearch(storage);
        List<LngLat> result = search.search(start, goal);
        return result;

    }

    public static void main( String[] args ) {
        System.out.println("Please enter the base URL of the server: ");
        Scanner in = new Scanner(System.in);
        String baseURL = in.nextLine();
        FetchResponse response = FetchResponse.getInstance();
        response.setUrl(baseURL);
        WorldDataStorage storage = new WorldDataStorage();
        System.out.println("storage set");
        System.out.println("path set");
        for (LngLat lngLat : printPath(storage)) {
            System.out.println("[" + lngLat.lng() + ", " + lngLat.lat() + "] ,");
        }
    }

}
