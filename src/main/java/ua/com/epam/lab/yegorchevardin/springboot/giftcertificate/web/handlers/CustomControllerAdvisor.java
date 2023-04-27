package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.handlers;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.exceptions.IncorrectSortingParameterException;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.exceptions.DataExistException;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.exceptions.DataNotFoundException;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller Advisor for handling exceptions
 * @author yegorchevardin
 * @version 0.0.1
 */
@RestControllerAdvice
public class CustomControllerAdvisor extends ResponseEntityExceptionHandler {
    /**
     * Constraint violation exception handler
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object>
    handleConstraintViolation(ConstraintViolationException ex) {
        return wrapExceptionToMap(ex, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    /**
     * Validating invalid output
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) ->{

            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Method for handling page not found exception, which will throw 404 error
     */
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return wrapExceptionToMap(ex, (HttpStatus) status);
    }

    /**
     * Method for handling DataNotFoundException, which will return 404 error
     */
    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<Object> handleDataNotFoundException(Exception exception) {
        return wrapExceptionToMap(exception, HttpStatus.NOT_FOUND);
    }

    /**
     * Method for handling exceptions with incorrect inserted data
     * which will return bad request error
     */
    @ExceptionHandler(
            {
                    DataExistException.class,
                    IncorrectSortingParameterException.class
            }
    )
    public ResponseEntity<Object> handleDataExistException(Exception exception) {
        return wrapExceptionToMap(exception, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Object> wrapExceptionToMap(
            Exception exception,
            HttpStatus status
    ) {
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("message:", exception.getMessage());
        responseMap.put("errorCode:", String.valueOf(status.value()));
        return new ResponseEntity<>(
                responseMap,
                status);
    }
}
