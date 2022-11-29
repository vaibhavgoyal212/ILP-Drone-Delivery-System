package uk.ac.ed.inf;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * Hello world!
 *
 */

public class App 
{
//    public static  List<Node> printPath(WorldDataStorage storage){
//        LngLat start = new LngLat(-3.186874, 55.944494);
//        LngLat goal = new LngLat( -3.202541470527649,  55.943284737579376);
//        RoutePlanner search = new RoutePlanner(storage);
//        List<Node> result1 = search.search(start, goal);
//        LngLat start2 = result1.get(result1.size() - 1).getPosition();
//        List<Node> result2 = search.search(start2, start);
//
//        return Stream.concat(result1.stream(), result2.stream()).toList();
//
//    }

    public static void main( String[] args ) {
        System.out.println("Please enter the base URL of the server: ");
        Scanner in = new Scanner(System.in);
        String baseURL = in.nextLine();
        if(!baseURL.equals("https://ilp-rest.azurewebsites.net")){
            System.out.println("Invalid URL");
            throw new IllegalArgumentException();
            }
        FetchResponse response = FetchResponse.getInstance();
        response.setUrl(baseURL);
        System.out.println("Please enter the date of the orders you want to deliver: ");
        String date = in.nextLine();
        WorldDataStorage storage = new WorldDataStorage();
        System.out.println("storage set");
        System.out.println("path set");
        Drone drone = new Drone(storage);
        drone.deliverOrders(date);
//        for(Node node : drone.getTotalFlightPath()){
//            System.out.println("["+node.getPosition().lng() +", "+ node.getPosition().lat() + "], ");
//        }
        drone.createFile(date);

    }
//https://ilp-rest.azurewebsites.net
}
