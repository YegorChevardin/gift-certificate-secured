package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.exceptions;

/**
 * Exception in case of update is not allowed or something went wrong
 * @author yegorchevardin
 * @version 0.0.1
 */
public class UpdateException extends RuntimeException {
    public UpdateException(String message) {
        super(message);
    }
}
