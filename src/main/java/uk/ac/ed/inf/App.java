package uk.ac.ed.inf;

public class App {

    public static void main( String[] args ) {
        long startTime = System.nanoTime();
        String date = args[0];
        String baseURL = args[1];
        System.out.println("Starting drone delivery for date: " + date);

        FetchResponse.setUrl(baseURL);
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
