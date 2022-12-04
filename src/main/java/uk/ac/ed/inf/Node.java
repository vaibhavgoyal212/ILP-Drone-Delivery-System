package uk.ac.ed.inf;


public class Node {


    /**
     * this field stores whether a node is a start node.
     * Start nodes don't have a parent.
     * This information is used to determine whether a node is to be added to the JSON file or not
     */
    private boolean isStartNode = false;
    /**
     * this field stores the LngLat associated with the node
     */
    private LngLat position;

    /**
     * this field stores the parent node of the current node
     */
    private Node parent = null;

    /**
     * This heuristic is used for the algorithm to determine the best path
     * this field stores the distance from the current node to a given end node
     */
    private double h_distance;
    /**
     * This field stores the angle that the drone has moved to reach the current node from its parent node
     */
    private Double angle;
    /**
     * Field to store the ticks taken to compute the node since the start of the drone
     */
    private long ticksSinceStartOfCalculation = 0;

    public Node(LngLat position){
        this.setPosition(position);
        }

    /**
     * This method is used to calculate the heuristic value of the node
     * @param goal the end node for which to calculate the heuristic
     * @return the heuristic value of the node
     */
    public double calculateH(LngLat goal){
        return getPosition().distanceTo(goal);
    }

    /**
     * This method is used to set the heuristic value of the node
     * @param h_distance the heuristic value of the node to be set
     */
    public void setH_distance(double h_distance) {
        this.h_distance = h_distance;
    }

    /**
     * This override method is used to compare two nodes
     * @param o
     * @return true if the nodes are equal, false otherwise
     */
    @Override
    public boolean equals(Object o){
        if(o == this){
            return true;
        }
        if(!(o instanceof Node)){
            return false;
        }
        Node node = (Node) o;
        return node.getPosition().equals(this.getPosition());
    }

    /**
     * setter for the angle field
     * sets the angle of the node
     * @param angle the angle to be set
     */
    public void setAngle(Double angle){
        this.angle = angle;
    }

    /**
     * getter for the angle field
     * @return the angle of the node
     */

    public Double getAngle(){
        return angle;
    }


    /**
     * getter for the isStartNode field
     * @return true if the node is a start node, false otherwise
     */
    public boolean isStartNode() {
        return isStartNode;
    }

    /**
     * getter for the parent field
     * @return the parent of the node
     */
    public Node getParent() {
        return parent;
    }

    /**
     * setter for the parent field
     * @param parent the parent node to be set
     */
    public void setParent(Node parent) {
        this.parent = parent;
    }

    /**
     * getter for the position field
     * @return the position of the node as a LngLat object
     */
    public LngLat getPosition() {
        return position;
    }

    /**
     * setter for the position field
     * @param position the position to be set for the node
     */
    public void setPosition(LngLat position) {
        this.position = position;
    }

    /**
     * getter for the ticksSinceStartOfCalculation field
     * @return the ticks taken to compute the node since the start of the drone delivery system
     */
    public long getTicksSinceStartOfCalculation() {
        return ticksSinceStartOfCalculation;
    }
    /**
     * setter for the ticksSinceStartOfCalculation field
     * @param startTime the start time from which to calculate the ticks taken to compute the node
     */
    public void setTicksSinceStartOfCalculation(long startTime) {
        this.ticksSinceStartOfCalculation = System.nanoTime() - startTime;
    }
}
