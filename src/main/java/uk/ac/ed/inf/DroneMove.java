package uk.ac.ed.inf;

public class DroneMove {
    private String orderNo;
    private LngLat position;
    private LngLat parent;
    private Double angle;
    private long ticksSinceStartOfCalculation;
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
