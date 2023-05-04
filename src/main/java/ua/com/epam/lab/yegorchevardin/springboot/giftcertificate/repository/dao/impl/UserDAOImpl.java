package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.dao.impl;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.constants.FilterTypes;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.dao.AbstractDAO;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.dao.UserDAO;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.entities.TagEntity;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.entities.UserEntity;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.handlers.QueryHandler;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.constants.FilterTypes.TAG_NAME;

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
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add(FilterTypes.USERNAME.getValue(), username);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserEntity> criteriaQuery = queryHandler.createFilteringGetQuery(paramMap, criteriaBuilder);
        return entityManager.createQuery(criteriaQuery)
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
