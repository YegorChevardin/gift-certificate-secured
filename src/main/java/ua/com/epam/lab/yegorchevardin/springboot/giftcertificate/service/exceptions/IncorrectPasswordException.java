package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.exceptions;

/**
 * Exception for case if inserted password was incorrect
 * @author yegorchevardin
 * @version 0.0.1
 */
public class IncorrectPasswordException extends RuntimeException {
    private static final String MESSAGE = "Incorrect password or username!";
    public IncorrectPasswordException() {
        super(MESSAGE);
    }
}
