package tn.citypulse.documentdemand.exception;

public class NullAttachmentException extends RuntimeException {
    public NullAttachmentException(String message) {
        super(message);
    }
    public NullAttachmentException() {
        super("Attachment is null or missing");
    }

}
