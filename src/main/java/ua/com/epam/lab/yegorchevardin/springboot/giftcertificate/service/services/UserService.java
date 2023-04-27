package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.services;

import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.dtos.User;

/**
 * Interface that defines service methods
 * for handling user object operations
 * @author yegorchevardin
 * @version 0.0.1
 */
public interface UserService extends CreateReadUpdateDeleteService<User> {
    /**
     * Method for finding user by it's username
     */
    User findByUsername(String username);
}
