package tn.citypulse.documentdemand.exception;

public class NullDemandException extends RuntimeException{
    public NullDemandException(String message){
        super(message);
    }
    public NullDemandException(){
        super("Demand is null or missing");
    }
}
