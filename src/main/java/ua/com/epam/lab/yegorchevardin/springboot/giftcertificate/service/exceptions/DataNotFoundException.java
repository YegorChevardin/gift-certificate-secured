package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.exceptions;

/**
 * Exception for case if data in database was not found
 * @author yegorchevardin
 * @version 0.0.1
 */
public class DataNotFoundException extends RuntimeException {
    public DataNotFoundException(String message) {
        super(message);
    }
}
