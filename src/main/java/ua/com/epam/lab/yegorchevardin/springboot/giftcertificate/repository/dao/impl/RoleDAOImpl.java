package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.dao.impl;

import org.springframework.stereotype.Repository;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.dao.AbstractDAO;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.dao.RoleDAO;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.entities.RoleEntity;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.handlers.QueryHandler;

import java.util.Optional;

@Repository
public class RoleDAOImpl extends AbstractDAO<RoleEntity> implements RoleDAO {
    public RoleDAOImpl(QueryHandler<RoleEntity> queryHandler, Class<RoleEntity> entityType) {
        super(queryHandler, entityType);
    }

    @Override
    public Optional<RoleEntity> findByName(String name) {
        return Optional.empty();
    }
}
