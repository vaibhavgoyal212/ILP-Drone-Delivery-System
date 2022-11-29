package uk.ac.ed.inf;

import java.util.ArrayList;
import java.util.List;

public class WorldDataStorage {
    private static Restaurant[] restaurants;
    private static NoFlyZone[] noFlyZones;
    private static Order[] orders;
    private static LngLat[] centralAreaPoints;


    public WorldDataStorage(){
        initialise();
    }

    private static void initialise(){
        FetchResponse response = FetchResponse.getInstance();
        restaurants = response.getRestaurants();
        noFlyZones = response.getNoFlyZones();
        orders = response.getOrders();
        centralAreaPoints = response.getCentralArea();
    }

    public Restaurant[] getRestaurants(){
        return restaurants;
    }

    public NoFlyZone[] getNoFlyZones(){
        return noFlyZones;
    }

    public List<Order> getValidOrders(String date){
        List<Order> validOrders = new ArrayList<>();
        for(Order order : orders){
            if(order.isOrderValid(restaurants, order.orderItems) && order.orderDate.equals(date)){
                validOrders.add(order);
            }
        }
        return validOrders;
    }

    public LngLat[] getCentralAreaPoints(){
        return centralAreaPoints;
    }

    public Order[] getOrders(){
        return orders;
    }


    public Order[] getOrdersByDate(String date) {
        List<Order> ordersByDate = new ArrayList<>();
        for(Order order : orders){
            if(order.orderDate.equals(date)){
                ordersByDate.add(order);
            }
        }
        return ordersByDate.toArray(new Order[0]);
    }
}
