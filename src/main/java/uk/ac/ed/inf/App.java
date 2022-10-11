package uk.ac.ed.inf;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws MalformedURLException {
        URL url = new URL("https://ilp-rest.azurewebsites.net/");
        Restaurant[] restaurants = Restaurant.getRestaurantsFromRestServer(url);

        System.out.println(restaurants[0]);
    }

}
