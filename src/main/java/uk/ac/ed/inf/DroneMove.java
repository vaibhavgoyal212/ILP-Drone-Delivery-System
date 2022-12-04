package uk.ac.ed.inf;

public class DroneMove {
    /**
     * field for the order number associated with the move
     */
    private String orderNo;

    /**
     * field for the current location of the drone
     */
    private LngLat position;
    /**
     * field for the location of the parent (starting point of move)
     */
    private LngLat parent;
    /**
     * field for the angle that the drone has moved from the parent to the current position
     */
    private Double angle;

    /**
     * field for the ticks elapsed since the start of the path calculation
     */
    private long ticksSinceStartOfCalculation;

    /**
     * field to check if the move is a starting move(in which case the parent is null)
     */
    private boolean isStartMove;

    public DroneMove(Node node){
        this.position = node.getPosition();
        this.isStartMove = node.isStartNode();
        if(node.getParent() != null){
            this.parent = node.getParent().getPosition();
        }
        this.angle = node.getAngle();
        this.ticksSinceStartOfCalculation = node.getTicksSinceStartOfCalculation();
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public LngLat getPosition() {
        return position;
    }

    public void setPosition(LngLat position) {
        this.position = position;
    }

    public LngLat getParent() {
        return parent;
    }

    public void setParent(LngLat parent) {
        this.parent = parent;
    }

    public Double getAngle() {
        return angle;
    }

    public void setAngle(Double angle) {
        this.angle = angle;
    }

    public long getTicksSinceStartOfCalculation() {
        return ticksSinceStartOfCalculation;
    }

    public void setTicksSinceStartOfCalculation(long ticksSinceStartOfCalculation) {
        this.ticksSinceStartOfCalculation = ticksSinceStartOfCalculation;
    }

    public boolean isStartMove() {
        return isStartMove;
    }

    public void setStartMove() {
        isStartMove = true;
    }
}
