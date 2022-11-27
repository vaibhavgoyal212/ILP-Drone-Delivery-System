package uk.ac.ed.inf;

import java.awt.geom.Line2D;
import java.util.*;

public class AStarSearch {
    private static final double DIST_PER_MOVE= 0.00015;
    private static final int MAX_MOVES=2000;
    private static final LngLat appletonTower = new LngLat(-3.186874, 55.944494);
    private static WorldDataStorage worldDataStorage = null;


    public AStarSearch(WorldDataStorage dataStorage){
        worldDataStorage = dataStorage;
    }

    public List<Node> getNeighbors(Node node){
        List<Node> neighbors = new ArrayList<>();
        for (Compass compass : Compass.values()){
            LngLat nextPosition = node.position.nextPosition(compass);
            neighbors.add(new Node(nextPosition));
        }
        return neighbors;
    }

    public List<LngLat> search(LngLat start, LngLat goal){

        PriorityQueue<Node> openList = new PriorityQueue<>(Comparator.comparing(o -> o.calculateH(goal)));
        ArrayList<Node> closedList = new ArrayList<>();
        Node startNode = new Node(start);
        Node goalNode = new Node(goal);
        openList.add(startNode);
        boolean found = false;
        while(!openList.isEmpty()){
            Node currentNode = openList.poll();
            closedList.add(currentNode);
            if(currentNode.position.closeTo(goalNode.position)){
                return printPath(currentNode);
            }
            List<Node> neighbors = getNeighbors(currentNode);
            for(Node neighbour:neighbors){
                if(crossesNoFlyZone(currentNode.position, neighbour.position)){
                    continue;
                }
                if(closedList.contains(neighbour) || neighbour.calculateH(goalNode.position) >= currentNode.calculateH(goalNode.position)){
                    continue;
                }
                neighbour.setH_distance(neighbour.calculateH(goalNode.position));
                neighbour.parent = currentNode;
                if(!closedList.contains(neighbour)){
                    openList.add(neighbour);
                }
                else if(closedList.get(closedList.indexOf(neighbour)).calculateH(goalNode.position) > neighbour.calculateH(goalNode.position)){
                    openList.add(neighbour);
                }
            }
        }
        System.out.println("No path found");
        return null;
    }

    public List<LngLat> printPath(Node goal){
        List<LngLat> path = new ArrayList<>();
        Node current = goal;
        while(current != null){
            path.add(current.position);
            current = current.parent;
        }
        Collections.reverse(path);
        return path;
    }



    public static boolean segmentsIntersect(LngLat start , LngLat end, LngLat point1 , LngLat point2){
        Line2D line1 = new Line2D.Double(start.lng(), start.lat(), end.lng(), end.lat());
        Line2D line2 = new Line2D.Double(point1.lng(), point1.lat(), point2.lng(), point2.lat());
        return line1.intersectsLine(line2);
    }

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





}
