package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.dao.impl;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.dao.AbstractDAO;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.dao.TagDAO;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.entities.TagEntity;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.handlers.QueryHandler;

import java.util.Optional;

import static ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.constants.FilterTypes.TAG_NAME;

@Repository
public class TagDAOImpl extends AbstractDAO<TagEntity> implements TagDAO {
    private static final String MOST_POPULAR_TAG_QUERY = "select t from OrderEntity o " +
            "join o.giftCertificates c " +
            "join c.tags t " +
            "group by t.id order by count(t.id) desc, sum(o.cost) desc limit 1";

    @Autowired
    public TagDAOImpl(QueryHandler<TagEntity> queryCreator) {
        super(queryCreator, TagEntity.class);
    }

    @Override
    public Optional<TagEntity> findByName(String name) {
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add(TAG_NAME.getValue(), name);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<TagEntity> criteriaQuery = queryHandler.createFilteringGetQuery(paramMap, criteriaBuilder);
        return entityManager.createQuery(criteriaQuery)
                .getResultStream()
                .findFirst();
    }

    @Override
    public Optional<TagEntity> findMostPopularTagWithOrdersWithHighestCost() {
        return entityManager.createQuery(MOST_POPULAR_TAG_QUERY, entityType)
                .getResultStream()
                .findFirst();
    }
}
