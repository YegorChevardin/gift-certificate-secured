package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.utils.helpers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.entities.UserEntity;

/**
 * Interface for helping to get current user account from the session
 * @author yegorchevardin
 * @version 0.0.1
 */
public interface UserAccountHelper {
    /**
     * Gets current logged user
     * @return Current user's UserEntity
     */
    UserEntity getLoggedUser();

    /**
     * Checks if guest is authenticated or not
     * @return true if guest is authenticated
     */
    boolean isAuthenticated();

    /**
     * Logs out current user
     */
    void logout(HttpServletRequest request, HttpServletResponse response);
}
