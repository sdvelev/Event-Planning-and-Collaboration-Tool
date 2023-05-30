package bg.sofia.uni.fmi.web.project.validation;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler({ ApiBadRequest.class }) //400 // 404
    public ResponseEntity<?> handleBadRequest(ApiBadRequest exception) {
        // ApiErrorDto -> message, code
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler({ ResourceNotFoundException.class })
    public ResponseEntity<?> handleResourceNotFound(ResourceNotFoundException exception) {
        // ApiErrorDto -> message, code
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler({ MethodArgumentNotValidException.class })
    public ResponseEntity<?> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        // ApiErrorDto -> message, code
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler({ ConstraintViolationException.class })
    public ResponseEntity<?> handleConstraintViolation(ConstraintViolationException exception) {
        // ApiErrorDto -> message, code
        return ResponseEntity.badRequest().body(exception.getMessage());
    }
}