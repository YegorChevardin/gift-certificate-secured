package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.handlers.impl;

import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.entities.GiftCertificateEntity;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.handlers.AbstractQueryHandler;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.handlers.QueryHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.constants.FilterTypes.*;

/**
 * Query handler for gift certificate entity
 * @author yegorchevardin
 * @version 0.0.1
 */
@Component
public class GiftCertificateQueryHandler
        extends AbstractQueryHandler
        implements QueryHandler<GiftCertificateEntity> {
    private static final String TAGS_FIELD = "tags";
    private static final String NAME_FIELD = "name";
    private static final String CREATE_DATE_FIELD = "createDate";

    @Override
    public CriteriaQuery<GiftCertificateEntity> createFilteringGetQuery(
            MultiValueMap<String, String> params,
            CriteriaBuilder criteriaBuilder) {
        CriteriaQuery<GiftCertificateEntity> criteriaQuery =
                criteriaBuilder.createQuery(GiftCertificateEntity.class);
        Root<GiftCertificateEntity> root = criteriaQuery.from(GiftCertificateEntity.class);

        List<Predicate> predicates = new ArrayList<>(params.size());
        List<Order> orders = new ArrayList<>(params.size());
        for(Map.Entry<String, List<String>> entry : params.entrySet()) {
            String filterParam = entry.getKey().toLowerCase();
            String paramValue = entry.getValue()
                    .stream()
                    .findFirst()
                    .orElse("");

            if (filterParam.equals(NAME_FIELD)) {
                predicates.add(addLikePredicate(criteriaBuilder, root.get(NAME_FIELD), paramValue));
            } else if (filterParam.equals(DESCRIPTION.getValue())) {
                predicates.add(addLikePredicate(criteriaBuilder, root.get(DESCRIPTION.getValue()), paramValue));
            } else if (filterParam.equals(TAG_NAME.getValue())) {
                List<String> tagNames = entry.getValue();
                tagNames.forEach(
                        (tagName) -> predicates.add(
                                addLikePredicate(
                                        criteriaBuilder, root.join(TAGS_FIELD).get(NAME.getValue()),
                                        tagName
                                )
                        )
                );
            } else if (filterParam.equals(NAME_SORT.getValue())) {
                orders.add(addOrder(criteriaBuilder, root.get(NAME_FIELD), paramValue));
            } else if (filterParam.equals(DATE_SORT.getValue())) {
                orders.add(addOrder(criteriaBuilder, root.get(CREATE_DATE_FIELD), paramValue));
            }
        }

        criteriaQuery.select(root)
                .where(predicates.toArray(new Predicate[]{}))
                .orderBy(orders);
        return criteriaQuery;
    }
}
