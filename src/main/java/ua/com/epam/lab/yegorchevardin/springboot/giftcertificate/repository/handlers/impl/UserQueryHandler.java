package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.handlers.impl;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.entities.UserEntity;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.handlers.AbstractQueryHandler;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.handlers.QueryHandler;

@Component
public class UserQueryHandler extends AbstractQueryHandler
        implements QueryHandler<UserEntity> {
    @Override
    public CriteriaQuery<UserEntity> createFilteringGetQuery(
            MultiValueMap<String, String> params,
            CriteriaBuilder criteriaBuilder
    ) {
        CriteriaQuery<UserEntity> criteriaQuery = criteriaBuilder.createQuery(UserEntity.class);
        Root<UserEntity> root = criteriaQuery.from(UserEntity.class);

        return criteriaQuery;
    }
}
