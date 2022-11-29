package uk.ac.ed.inf;


/**
 * Hello world!
 *
 */

public class App {

    public static void main( String[] args ) {
        long startTime = System.nanoTime();
        if(args.length != 2){
            throw new IllegalArgumentException("Please provide the correct number of arguments");
        }
        String date = args[0];
        String baseURL = args[1];
        System.out.println("Starting drone delivery for date: " + date);

        FetchResponse response = FetchResponse.getInstance();
        response.setUrl(baseURL);
        WorldDataStorage storage = new WorldDataStorage();
        System.out.println("storage set");
        Drone drone = new Drone(storage);
        drone.deliverOrders(date);
        drone.createFile(date);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime)/1000000000;
        System.out.println("Time taken: " + duration + "seconds");

    }
}
