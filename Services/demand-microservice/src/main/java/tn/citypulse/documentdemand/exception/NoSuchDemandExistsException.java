package tn.citypulse.documentdemand.exception;

public class NoSuchDemandExistsException extends RuntimeException{
    public NoSuchDemandExistsException(String message){
        super(message);
    }
    public NoSuchDemandExistsException(){
        super("The requested Demand does not exist");
    }
}
