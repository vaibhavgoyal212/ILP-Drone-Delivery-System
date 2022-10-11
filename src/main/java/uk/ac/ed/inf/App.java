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
        LngLat coordinate = new LngLat(-3.186874,55.944494);
        System.out.println(coordinate.inCentralArea());
    }

}
