package tn.citypulse.documentdemand.exception;

public class NullProofException extends RuntimeException {
    public NullProofException(String message) {
        super(message);
    }
    public NullProofException() {
        super("The provided Proof is null");
    }
}
