package uk.ac.ed.inf;


public class Node {
    private String orderNo;

    private boolean isStartNode = false;

    private LngLat position;
    private Node parent = null;
    private double h_distance;

    private Double angle;

    private long ticksSinceStartOfCalculation = 0;

    public Node(LngLat position){
        this.setPosition(position);
        }

    public double calculateH(LngLat goal){
        return getPosition().distanceTo(goal);
    }

    public void setH_distance(double h_distance) {
        this.h_distance = h_distance;
    }


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

    public void setAngle(Double angle){
        this.angle = angle;
    }

    public Double getAngle(){
        return angle;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public void setNodeAsStartNode() {
        isStartNode = true;
    }

    public boolean isStartNode() {
        return isStartNode;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public double getH_distance() {
        return h_distance;
    }

    public LngLat getPosition() {
        return position;
    }

    public void setPosition(LngLat position) {
        this.position = position;
    }

    public long getTicksSinceStartOfCalculation() {
        return ticksSinceStartOfCalculation;
    }

    public void setTicksSinceStartOfCalculation(long startTime) {
        this.ticksSinceStartOfCalculation = System.nanoTime() - startTime;
    }
}
