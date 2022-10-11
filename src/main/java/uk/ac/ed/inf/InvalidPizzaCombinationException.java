package uk.ac.ed.inf;

public class InvalidPizzaCombinationException extends Exception{
    public InvalidPizzaCombinationException(String errorMsg){
        super(errorMsg);
    }
}
