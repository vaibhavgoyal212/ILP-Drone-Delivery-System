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

    /**
     * Initialises the WorldDataStorage object
     * it fetches the data from the server using the FetchResponse class and stores it in the respective fields.
     */
    private static void initialise(){
        restaurants = FetchResponse.getRestaurants();
        noFlyZones = FetchResponse.getNoFlyZones();
        orders = FetchResponse.getOrders();
        centralAreaPoints = FetchResponse.getCentralArea();
    }

    /**
     * getter for the restaurants
     * @return all the restaurants as an array of Restaurant objects
     */
    public Restaurant[] getRestaurants(){
        return restaurants;
    }

    /**
     * getter for the no-fly zones
     * @return all the no-fly zones as an array of NoFlyZone objects
     */
    public NoFlyZone[] getNoFlyZones(){
        return noFlyZones;
    }

    /**
     * this method returns the orders that are valid and are to be delivered on the given date as an array of Order objects
     * @param date the date for which the orders are to be delivered
     * @return an array of Order objects
     */
    public List<Order> getValidOrders(String date){
        List<Order> validOrders = new ArrayList<>();
        for(Order order : orders){
            if(order.isOrderValid(restaurants) && order.orderDate.equals(date)){
                validOrders.add(order);
            }
        }
        return validOrders;
    }

    /**
     * getter for the central area points as an array of LngLat objects
     * @return an array of LngLat objects depicting the corners of the central area
     */
    public LngLat[] getCentralAreaPoints(){
        return centralAreaPoints;
    }

    /**
     * this method returns all the orders as fetched from the server
     * @return a list of Order objects
     */
    public Order[] getOrders(){
        return orders;
    }

    /**
     * this method returns all the orders(both valid and invalid) on particular date
     * @param date the date for which the orders are to be filtered
     * @return a list of Order objects
     */
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
