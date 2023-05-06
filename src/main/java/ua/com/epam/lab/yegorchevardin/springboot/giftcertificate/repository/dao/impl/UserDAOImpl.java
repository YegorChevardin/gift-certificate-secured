package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.dao.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.dao.AbstractDAO;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.dao.UserDAO;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.entities.UserEntity;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDAOImpl extends AbstractDAO<UserEntity> implements UserDAO {
    private static final String FIND_USER_BY_NAME =
            "select u from UserEntity u where u.username = :username";

    private static final String FIND_USERS_BY_ROLE =
            "select u from UserEntity u join u.roles rs where rs.name = :name";

    @Autowired
    public UserDAOImpl() {
        super(null, UserEntity.class);
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        return entityManager.createQuery(FIND_USER_BY_NAME, UserEntity.class)
                .getResultStream()
                .findFirst();
    }

    @Override
    public List<UserEntity> findByRole(String role) {
        return entityManager.createQuery(FIND_USERS_BY_ROLE, UserEntity.class)
                .setParameter("role", role).getResultList();
    }

    @Override
    @Transactional
    public UserEntity update(UserEntity entity) {
        return entityManager.merge(entity);
    }
}
