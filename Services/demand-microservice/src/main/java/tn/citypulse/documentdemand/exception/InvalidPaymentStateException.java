package tn.citypulse.documentdemand.exception;

public class InvalidPaymentStateException extends RuntimeException {
    public InvalidPaymentStateException(String message) {
        super(message);
    }
    public InvalidPaymentStateException() {
        super("Payment can only be made for APPROVED demands");
    }
}
