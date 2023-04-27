package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.handlers.impl;

import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.entities.TagEntity;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.handlers.AbstractQueryHandler;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.handlers.QueryHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.constants.FilterTypes.*;

/**
 * Query handler for tag entity
 * @author yegorchevardin
 * @version 0.0.1
 */
@Component
public class TagQueryHandler extends AbstractQueryHandler
        implements QueryHandler<TagEntity> {
    private static final String TAG_VALUE_COLUMN = "name";

    @Override
    public CriteriaQuery<TagEntity> createFilteringGetQuery(
            MultiValueMap<String, String> params, CriteriaBuilder criteriaBuilder
    ) {
        CriteriaQuery<TagEntity> criteriaQuery = criteriaBuilder.createQuery(TagEntity.class);
        Root<TagEntity> root = criteriaQuery.from(TagEntity.class);

        List<Predicate> predicates = new ArrayList<>(params.size());
        List<Order> orders = new ArrayList<>(params.size());
        for (Map.Entry<String, List<String>> entry : params.entrySet()) {
            String filterParam = entry.getKey().toLowerCase();
            String paramValue = entry.getValue()
                    .stream()
                    .findFirst()
                    .orElse("");

            if (filterParam.equals(TAG_NAME.getValue())) {
                predicates.add(addLikePredicate(criteriaBuilder, root.get(TAG_VALUE_COLUMN), paramValue));
            } else if (filterParam.equals(SORT_BY_TAG_NAME.getValue())) {
                orders.add(addOrder(criteriaBuilder, root.get(TAG_VALUE_COLUMN), paramValue));
            }
        }

        criteriaQuery.select(root).where(predicates.toArray(new Predicate[]{})).orderBy(orders);
        return criteriaQuery;
    }
}
