package uk.ac.ed.inf;

public enum Compass {
    NORTH (90),
    SOUTH (270),
    EAST (0),
    WEST(180),
    NORTH_EAST(45),
    NORTH_WEST(135),
    SOUTH_EAST(315),
    SOUTH_WEST(225),
    NORTH_NORTH_EAST(67.5),
    EAST_NORTH_EAST(22.5),
    EAST_SOUTH_EAST(337.5),
    SOUTH_SOUTH_EAST(292.5),
    SOUTH_SOUTH_WEST(247.5),
    WEST_SOUTH_WEST(202.5),
    WEST_NORTH_WEST(157.5),
    NORTH_NORTH_WEST(112.5);

    private final double degreeVal;

    Compass(double degreeVal){
        this.degreeVal=degreeVal;
    }

    public double getDegreeVal(){
        return degreeVal;
    }



}
