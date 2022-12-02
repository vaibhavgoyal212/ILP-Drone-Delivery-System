package uk.ac.ed.inf;

import java.awt.geom.Line2D;
import java.util.*;

public class RoutePlanner {

    private static WorldDataStorage worldDataStorage = null;
    /**
     * this field stores the start time of the execution of the program
     */
    private final long startTime;


    public RoutePlanner(WorldDataStorage dataStorage){
        worldDataStorage = dataStorage;
        this.startTime = System.nanoTime();
    }

    /**
     * this method finds all the neighbours of a given node according to all the compass directions
     * it also sets the angle of the neighbour according to the current node and the ticks of the node calculation
     * @param node the node whose neighbours are to be found
     * @return a list of all the neighbours of the given node
     */
    private List<Node> getNeighbors(Node node){
        List<Node> neighbors = new ArrayList<>();
        for (Compass compass : Compass.values()){
            LngLat nextPosition = node.getPosition().nextPosition(compass);
            Node nextNode = new Node(nextPosition);
            nextNode.setTicksSinceStartOfCalculation(this.startTime);
            nextNode.setAngle(compass.getDegreeVal());
            neighbors.add(nextNode);
        }
        return neighbors;
    }

    public List<DroneMove> search(LngLat start, LngLat goal){

        PriorityQueue<Node> openList = new PriorityQueue<>(Comparator.comparing(o -> o.calculateH(goal)));
        ArrayList<Node> closedList = new ArrayList<>();
        Node startNode = new Node(start);
        Node goalNode = new Node(goal);
        openList.add(startNode);
        while(!openList.isEmpty()){
            boolean centralAreaCrossed = false;
            Node currentNode = openList.poll();
            closedList.add(currentNode);
            if(currentNode.getPosition().closeTo(goalNode.getPosition())){
                return getPath(currentNode);
            }
            List<Node> neighbors = getNeighbors(currentNode);
            for(Node neighbour:neighbors){
                boolean centralAreaStatus=crossesCentralArea(currentNode.getPosition(), neighbour.getPosition());
                if(centralAreaStatus && !centralAreaCrossed){
                    centralAreaCrossed = true;
                }
                else if(centralAreaCrossed && centralAreaStatus){
                    continue;
                }
                if(crossesNoFlyZone(currentNode.getPosition(), neighbour.getPosition())){
                    continue;
                }
                neighbour.setH_distance(neighbour.calculateH(goalNode.getPosition()));
                neighbour.setParent(currentNode);
                if(!closedList.contains(neighbour)){
                    openList.add(neighbour);
                }
                else if(closedList.get(closedList.indexOf(neighbour)).calculateH(goalNode.getPosition()) > neighbour.calculateH(goalNode.getPosition())){
                    closedList.remove(neighbour);
                    openList.add(neighbour);
                }
            }
        }
        System.out.println("No path found");
        return null;
    }

    /**
     * this method creates the path from start node to goal node by tracing the parent of each node starting from the goal node
     * it will also add a hover node at the end of each path
     * the hover node is added to ensure that the drone hovers over the delivery location or the pickup location for 1 move.
     * @param goal the goal node
     * @return a list of nodes in the shortest path from start to goal
     */
    public List<DroneMove> getPath(Node goal){
        List<DroneMove> path = new ArrayList<>();
        Node current = goal;
        while(current != null){
            path.add(new DroneMove(current));
            current = current.getParent();
        }
        Collections.reverse(path);
        path.get(0).setStartMove();
        DroneMove end = path.get(path.size()-1);
        Node hoverNode = new Node(end.getPosition());
        DroneMove hover = new DroneMove(hoverNode);
        hover.setTicksSinceStartOfCalculation(this.startTime);
        hover.setParent(end.getPosition());
        hoverNode.setAngle(null);
        path.add(hover);
        return path;
    }


    /**
     * this helper method checks if 2 lines segments intersect each other
     * @param start the start position of the first line segment
     * @param end the end position of the first line segment
     * @param point1 the start position of the second line segment
     * @param point2 the end position of the second line segment
     * @return true if the 2 line segments intersect each other, false otherwise
     */
    public static boolean segmentsIntersect(LngLat start , LngLat end, LngLat point1 , LngLat point2){
        Line2D line1 = new Line2D.Double(start.lng(), start.lat(), end.lng(), end.lat());
        Line2D line2 = new Line2D.Double(point1.lng(), point1.lat(), point2.lng(), point2.lat());
        return line1.intersectsLine(line2);
    }

    /**
     * this method checks if line segments between 2 points cross any of the no-fly zones
     * @param start the start position of the line segment
     * @param end   the end position of the line segment
     * @return true if the line segment crosses any of the no-fly zones, false otherwise
     */
    public static boolean crossesNoFlyZone(LngLat start, LngLat end){
        for(NoFlyZone noFlyZone: worldDataStorage.getNoFlyZones()){
            for(int i = 0; i < noFlyZone.getCoordinates().length - 1; i++){
                if(segmentsIntersect(start, end, noFlyZone.getCoordinates()[i], noFlyZone.getCoordinates()[i+1])){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * this method checks if line segment between 2 points crosses the central area
     * @param start the start position of the line segment
     * @param end  the end position of the line segment
     * @return true if the line segment crosses the central area, false otherwise
     */
    public static boolean crossesCentralArea(LngLat start, LngLat end){
        LngLat[] centralArea = worldDataStorage.getCentralAreaPoints();
        for(int i = 0; i < centralArea.length - 1; i++){
            if(segmentsIntersect(start, end, centralArea[i], centralArea[i+1])){
                return true;
            }
        }
        return false;
    }





}
