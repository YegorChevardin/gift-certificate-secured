package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.exceptions;

/**
 * Exception for case if Incorrect parameter was typed by the user
 * @author yegorchevardin
 * @version 0.0.1
 */
public class IncorrectSortingParameterException extends RuntimeException {
    public IncorrectSortingParameterException(String message) {
        super(message);
    }
}
