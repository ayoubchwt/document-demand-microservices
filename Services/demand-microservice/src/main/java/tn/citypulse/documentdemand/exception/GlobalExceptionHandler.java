package tn.citypulse.documentdemand.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 404 not found
    @ExceptionHandler({
            NoSuchProofExistsException.class,
            NoSuchAttachmentExistsException.class,
            NoSuchDemandExistsException.class
    })
    public ResponseEntity<ProblemDetail> handleNotFound(RuntimeException exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("Resource Not Found");
        problemDetail.setDetail(exception.getMessage());
        problemDetail.setProperty("timestamp", LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(problemDetail);
    }

    // 400 bad request
    @ExceptionHandler({
            NullProofException.class,
            NullAttachmentException.class,
            NullDemandException.class,
            IllegalArgumentException.class
    })
    public ResponseEntity<ProblemDetail> handleBadRequest(RuntimeException exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Bad Request");
        problemDetail.setDetail(exception.getMessage());
        problemDetail.setProperty("timestamp", LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }
    // 409 conflict
    @ExceptionHandler(InvalidPaymentStateException.class)
    public ResponseEntity<ProblemDetail> handlePaymentError(InvalidPaymentStateException exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problemDetail.setTitle("Payment Not Allowed");
        problemDetail.setDetail(exception.getMessage());
        problemDetail.setProperty("timestamp", LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(problemDetail);
    }
    // 409 conflict
    @ExceptionHandler(InvalidDemandStateException.class)
    public ResponseEntity<ProblemDetail> handleDemandStateError(InvalidDemandStateException exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problemDetail.setTitle("Invalid Demand State");
        problemDetail.setDetail(exception.getMessage());
        problemDetail.setProperty("timestamp", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(problemDetail);
    }
    // fall back (generic)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleGeneric(Exception exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problemDetail.setTitle("Internal server Error");
        problemDetail.setDetail(exception.getMessage());
        problemDetail.setProperty("timestamp",LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problemDetail);
    }
}
