package uk.ac.ed.inf;

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

    public Order[] getOrders(){
        return orders;
    }

    public LngLat[] getCentralAreaPoints(){
        return centralAreaPoints;
    }


}
