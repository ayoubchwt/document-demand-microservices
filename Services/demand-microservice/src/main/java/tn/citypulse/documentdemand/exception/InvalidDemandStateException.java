package tn.citypulse.documentdemand.exception;

public class InvalidDemandStateException extends RuntimeException {
    public InvalidDemandStateException(String message) {
        super(message);
    }
}
