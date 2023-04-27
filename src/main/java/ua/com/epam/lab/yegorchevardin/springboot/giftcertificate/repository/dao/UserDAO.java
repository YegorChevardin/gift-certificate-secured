package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.dao;

import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.entities.UserEntity;
import java.util.Optional;

/**
 * Interface that describes abstract behavior of user dao
 * @author yegorchevardin
 * @version 0.0.1
 */
public interface UserDAO extends CreateReadUpdateDeleteDAO<UserEntity> {
    /**
     * Retrieves a User entity by its name
     * @param username entity name
     * @return User entity
     */
    Optional<UserEntity> findByUsername(String username);
}
