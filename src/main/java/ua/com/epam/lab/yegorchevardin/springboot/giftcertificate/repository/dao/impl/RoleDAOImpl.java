package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.dao.impl;

import org.springframework.stereotype.Repository;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.dao.AbstractDAO;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.dao.RoleDAO;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.entities.RoleEntity;

import java.util.Optional;

@Repository
public class RoleDAOImpl extends AbstractDAO<RoleEntity> implements RoleDAO {
    private static final String FIND_ROLES_BY_NAME =
            "select rl from RoleEntity rl where rl.name = :roleName";
    public RoleDAOImpl() {
        super(null, RoleEntity.class);
    }

    @Override
    public Optional<RoleEntity> findByName(String name) {
        return entityManager
                .createQuery(FIND_ROLES_BY_NAME, RoleEntity.class)
                .setParameter("roleName", name)
                .getResultStream()
                .findFirst();
    }
}
