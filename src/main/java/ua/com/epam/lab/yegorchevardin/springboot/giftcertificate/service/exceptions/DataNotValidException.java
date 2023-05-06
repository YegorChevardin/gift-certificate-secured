package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.exceptions;

/**
 * Exception for case if custom validation failed
 * @author yegorchevardin
 * @version 0.0.1
 */
public class DataNotValidException extends RuntimeException {
    public DataNotValidException(String message) {
        super(message);
    }
}
