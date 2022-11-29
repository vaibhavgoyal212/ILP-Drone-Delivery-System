package uk.ac.ed.inf;

import java.awt.geom.Line2D;
import java.util.*;

public class RoutePlanner {

    private static WorldDataStorage worldDataStorage = null;


    public RoutePlanner(WorldDataStorage dataStorage){
        worldDataStorage = dataStorage;
    }

    private static List<Node> getNeighbors(Node node){
        List<Node> neighbors = new ArrayList<>();
        for (Compass compass : Compass.values()){
            LngLat nextPosition = node.getPosition().nextPosition(compass);
            Node nextNode = new Node(nextPosition);
            nextNode.setAngle(compass.getDegreeVal());
            neighbors.add(nextNode);
        }
        return neighbors;
    }

    public List<Node> search(LngLat start, LngLat goal){

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
                return printPath(currentNode);
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

    public List<Node> printPath(Node goal){
        List<Node> path = new ArrayList<>();
        Node current = goal;
        while(current != null){
            path.add(current);
            current = current.getParent();
        }
        Collections.reverse(path);
        path.get(0).setNodeAsStartNode();
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
