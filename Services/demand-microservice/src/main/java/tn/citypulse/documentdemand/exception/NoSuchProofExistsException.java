package tn.citypulse.documentdemand.exception;

public class NoSuchProofExistsException extends RuntimeException {
    public NoSuchProofExistsException(String message) {
        super(message);
    }
    public NoSuchProofExistsException() {
        super("The requested Proof does not exist");
    }
}
