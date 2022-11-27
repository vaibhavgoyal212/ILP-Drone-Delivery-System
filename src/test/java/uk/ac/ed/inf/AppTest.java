package uk.ac.ed.inf;

import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */
public class AppTest  {
    String base ="https://ilp-rest.azurewebsites.net";

    FetchResponse response = FetchResponse.getInstance();


    public AppTest() {
        response.setUrl(base);
    }

    @Test
    public void inCentralAreaTest(){

        LngLat coordinate1 = new LngLat(-3.186874,55.944494); //true
        LngLat coordinate2 = new LngLat(-3.89933,56.4545324); //false
        LngLat coordinate3 = new LngLat(-3.186974,55.944494); //true

        assertTrue(coordinate1.inCentralArea());
        assertFalse(coordinate2.inCentralArea());
        assertTrue(coordinate3.inCentralArea());

    }

    @Test
    public void closeToTest(){
        LngLat coordinate1 = new LngLat(-3.186874,55.944494);
        LngLat coordinate2 = new LngLat(-3.89933,56.4545324);
        LngLat coordinate3 = new LngLat(-3.186974,55.944494);

        assertTrue(coordinate1.closeTo(coordinate3));
        assertFalse(coordinate2.closeTo(coordinate3));

    }

    @Test
    public void nextPositionTest(){
        LngLat coordinate1 = new LngLat(-3.186874,55.944494);
        LngLat coordinate2 = new LngLat(-30,50);
        LngLat value1=new LngLat(-3.186874,55.944644);
        LngLat value2= new LngLat(-30,49.99985);
        assertEquals(coordinate1.nextPosition(Compass.NORTH), value1);
        assertEquals(coordinate2.nextPosition(Compass.SOUTH), value2);

        assertEquals(coordinate1.nextPosition(Compass.NORTH_EAST).nextPosition(Compass.NORTH_WEST).nextPosition(Compass.SOUTH_WEST).nextPosition(Compass.SOUTH_EAST),coordinate1);


    }

    @Test
    public void getMenuTest() throws MalformedURLException {
        Restaurant[] restaurants = response.getRestaurants();
        for(Restaurant r: restaurants){
            assertNotNull("Menu response not fetched for Restaurant"+r , r.getMenu());
        }
    }

    @Test(expected = InvalidPizzaCombinationException.class)
    public void getDeliveryCostForWrongCombinationTest() throws InvalidPizzaCombinationException {
        Restaurant[] restaurants = response.getRestaurants();
        Order order = new Order();
        order.getDeliveryCost(restaurants,new String[]{"Margarita", "All Shrooms"});

    }

    @Test
    public void getDeliveryCostForDuplicatePizzas() throws InvalidPizzaCombinationException {
        Restaurant[] restaurants = response.getRestaurants();
        Order order = new Order();
        int cost = order.getDeliveryCost(restaurants,new String[]{"Margarita", "Margarita"});
        assertEquals(cost,2100);
    }

    @Test
    public void getDeliveryCostGenericTest() throws InvalidPizzaCombinationException {
        Restaurant[] restaurants = response.getRestaurants();
        Order order = new Order();
        int cost = order.getDeliveryCost(restaurants,new String[]{"Super Cheese", "All Shrooms", "Super Cheese"});
        assertEquals(cost,3800);

    }

}
