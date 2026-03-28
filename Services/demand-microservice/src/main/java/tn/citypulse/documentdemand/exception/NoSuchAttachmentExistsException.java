package tn.citypulse.documentdemand.exception;

public class NoSuchAttachmentExistsException extends RuntimeException {
    public NoSuchAttachmentExistsException(String message) {
        super(message);
    }
    public NoSuchAttachmentExistsException() {
        super("The requested Attachment does not exist");
    }
}
