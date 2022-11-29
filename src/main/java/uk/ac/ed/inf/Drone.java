package uk.ac.ed.inf;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Stream;

public class Drone {
    private static final int MAX_MOVES=2000;
    private static final LngLat appletonTower = new LngLat(-3.186874, 55.944494);
    private static WorldDataStorage dataStorage = null;

    private static RoutePlanner routePlanner = null;

    private static List<Node> totalFlightPath = new ArrayList<>();


    public Drone(WorldDataStorage storage){
        dataStorage = storage;
        routePlanner = new RoutePlanner(storage);
    }

    public void deliverOrders(String date){
        int validOrders = 0;
        int undeliverableOrders = 0;
        int movesMade = 0;
        List<Order> orders = dataStorage.getValidOrders(date);
        Restaurant[] restaurants = dataStorage.getRestaurants();
        for(Restaurant restaurant : restaurants){
            int moves = routePlanner.search(appletonTower, restaurant.getLngLat()).size();
            restaurant.setMovesToDeliver(moves);
        }
        PriorityQueue<Order> orderQueue = new PriorityQueue<>(Comparator.comparing(o -> o.restaurant.movesToDeliver));
        orderQueue.addAll(orders);
        for(Order order : orderQueue){
            List<Node> appletonToRestaurant = routePlanner.search(appletonTower, order.restaurant.getLngLat());
            List<Node> restaurantToAppleton = routePlanner.search(appletonToRestaurant.get(appletonToRestaurant.size() - 1).getPosition(), appletonTower);
            List<Node> totalPath = Stream.concat(appletonToRestaurant.stream(), restaurantToAppleton.stream()).toList();
            totalPath.forEach(node -> node.setOrderNo(order.orderNo));


            if((appletonToRestaurant.size()+restaurantToAppleton.size() + movesMade) < MAX_MOVES){
                movesMade += appletonToRestaurant.size()+restaurantToAppleton.size();
                totalFlightPath.addAll(totalPath);

                order.setOutcome(OrderOutcome.DELIVERED);
                validOrders++;

            }
            else{
                order.setOutcome(OrderOutcome.VALIDBUTNOTDELIVERED);
                undeliverableOrders++;
            }


        }
        System.out.println("Number of orders: " + orderQueue.size());
        System.out.println("Number of valid orders: " + validOrders);
        System.out.println("Number of undeliverable orders: " + undeliverableOrders);
        System.out.println("Number of moves made: " + movesMade);

    }

    public void createFile(String date){
        writeToJSON.deliveriesFiles(dataStorage.getOrdersByDate(date), date);
        writeToJSON.flightPathFiles(totalFlightPath, date);
    }

    public List<Node> getTotalFlightPath(){
        return totalFlightPath;
    }



}
