package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.exceptions;

/**
 * Exception for case if data already exist in the database
 * @author yegorchevardin
 * @version 0.0.1
 */
public class DataExistException extends RuntimeException {
    public DataExistException(String message) {
        super(message);
    }
}
