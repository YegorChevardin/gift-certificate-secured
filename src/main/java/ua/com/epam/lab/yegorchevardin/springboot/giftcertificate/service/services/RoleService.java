package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.services;

import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.dtos.Role;

/**
 * Service for handling business logic for roles
 * @author yegorchevardin
 * @version 0.0.1
 */
public interface RoleService extends CreateReadDeleteService<Role> {
    /**
     * Methods for getting role dto by its value
     */
    Role findByRoleValue(String role);
}
