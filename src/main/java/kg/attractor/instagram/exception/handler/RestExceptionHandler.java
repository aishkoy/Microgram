package kg.attractor.instagram.exception.handler;

import jakarta.validation.ConstraintViolationException;
import kg.attractor.instagram.exception.model.ErrorResponseBody;
import kg.attractor.instagram.service.ErrorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)

public class RestExceptionHandler {
    private final ErrorService errorService;

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<ErrorResponseBody> handleMultipartException(MultipartException e) {
        log.error("Multipart request processing error: {}", e.getMessage());
        return new ResponseEntity<>(errorService.makeResponse(e), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponseBody> handleISE(IllegalStateException e) {
        log.error("IllegalStateException processing error: {}", e.getMessage());
        return new ResponseEntity<>(errorService.makeResponse(e), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponseBody> handleRuntime(RuntimeException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(errorService.makeResponse(e), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseBody> handleIAE(IllegalArgumentException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(errorService.makeResponse(e), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseBody> handleConstraintViolation(ConstraintViolationException ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(errorService.makeResponse(ex.getConstraintViolations()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponseBody> handleNSEE(NoSuchElementException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(errorService.makeResponse(e), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseBody> handleDataIntegrityViolation(DataIntegrityViolationException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(errorService.makeResponse(e), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponseBody> handleAccessDenied(AccessDeniedException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(errorService.makeResponse(e), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseBody> handleValidation(MethodArgumentNotValidException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(errorService.makeResponse(e.getBindingResult()), HttpStatus.BAD_REQUEST);
    }
}
