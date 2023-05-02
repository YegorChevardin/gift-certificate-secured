package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.dao;

import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.entities.RoleEntity;

import java.util.Optional;

/**
 * Class that defined methods for finding role entities from the database
 * @author yegorchevardin
 * @version 0.0.1
 */
public interface RoleDAO extends CreateReadDeleteDAO<RoleEntity> {
    /**
     * Method for finding role entity by id
     * @param name name of role to find
     * @return Optional of role
     */
    Optional<RoleEntity> findByName(String name);
}
