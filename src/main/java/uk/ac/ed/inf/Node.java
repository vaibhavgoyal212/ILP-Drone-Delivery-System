package uk.ac.ed.inf;

import java.util.ArrayList;
import java.util.List;

public class Node {

    public LngLat position;
    public Node parent = null;
    //public int g;
    public double h_distance;
    //public double f_total;

    public Node(LngLat position){
        this.position = position;
        }



    public double calculateH(LngLat goal){
        return position.distanceTo(goal);
    }

    public void setH_distance(double h_distance) {
        this.h_distance = h_distance;
    }

    public double getF(){
        return this.h_distance;
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
        return node.position.equals(this.position);
    }

}
