package uk.ac.ed.inf;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Stream;

public class Drone {

    /**
     * Field for max moves the drone can make in a day
     */
    private static final int MAX_MOVES=2000;

    /**
     * Field for the location of appleton tower
     */
    private static final LngLat appletonTower = new LngLat(-3.186874, 55.944494);
    private static WorldDataStorage dataStorage = null;

    private static RoutePlanner routePlanner = null;


    /**
     * Field to hold the entire flightpath for a day of the drone
     */
    private static List<Node> totalFlightPath = new ArrayList<>();


    public Drone(WorldDataStorage storage){
        dataStorage = storage;
        routePlanner = new RoutePlanner(storage);
    }

    /**
     * Method to deliver maximum possible orders in a day
     * @param date date for which to deliver orders
     */

    public void deliverOrders(String date){
        int deliveredOrders = 0;
        int undeliverableOrders = 0;
        int movesMade = 0;
        List<Order> orders = dataStorage.getValidOrders(date);
        Restaurant[] restaurants = dataStorage.getRestaurants();

        //we initially find the moves to get to each restaurant and use this to sort the orders in the priority queue
        for(Restaurant restaurant : restaurants){
            int moves = routePlanner.search(appletonTower, restaurant.getLngLat()).size();
            restaurant.setMovesToDeliver(moves);
        }
        PriorityQueue<Order> orderQueue = new PriorityQueue<>(Comparator.comparing(o -> o.restaurant.getMovesToDeliver()));
        orderQueue.addAll(orders);
        int totalOrders = orderQueue.size();
        while(!orderQueue.isEmpty()){
            Order order = orderQueue.poll();
            List<Node> appletonToRestaurant = routePlanner.search(appletonTower, order.restaurant.getLngLat());
            List<Node> restaurantToAppleton = routePlanner.search(appletonToRestaurant.get(appletonToRestaurant.size() - 1).getPosition(), appletonTower);
            List<Node> totalPath = Stream.concat(appletonToRestaurant.stream(), restaurantToAppleton.stream()).toList();
            totalPath.forEach(node -> node.setOrderNo(order.orderNo));


            if((appletonToRestaurant.size()+restaurantToAppleton.size() + movesMade) < MAX_MOVES){
                movesMade += appletonToRestaurant.size()+restaurantToAppleton.size();
                totalFlightPath.addAll(totalPath);

                order.setOutcome(OrderOutcome.DELIVERED);
                deliveredOrders++;

            }
            else{
                order.setOutcome(OrderOutcome.VALIDBUTNOTDELIVERED);
                undeliverableOrders++;
            }


        }
        System.out.println("Number of orders: " + totalOrders);
        System.out.println("Number of delivered orders: " + deliveredOrders);
        System.out.println("Number of undeliverable orders: " + undeliverableOrders);
        System.out.println("Number of moves made: " + movesMade);

    }

    /**
     * Method to create the required JSON and GeoJSON files for the day
     * @param date date for which to create the files
     */
    public void createFile(String date){
        writeToJSON.deliveriesFiles(dataStorage.getOrdersByDate(date), date);
        //System.out.println("deliveries file created");
        writeToJSON.flightPathFiles(totalFlightPath, date);
        //System.out.println("flight path file created");
        writeToJSON.droneGeoJSONFile(totalFlightPath, date);
        //System.out.println("drone geoJSON file created");
    }


}
