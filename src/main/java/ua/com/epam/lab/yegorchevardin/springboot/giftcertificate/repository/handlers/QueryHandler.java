package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.handlers;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import org.springframework.util.MultiValueMap;

/**
 * This interface is designed for creating queries
 * @param <T> entity return type
 * @author yegorchevardin
 * @version 0.0.1
 */
public interface QueryHandler<T> {
    /**
     * Method for creating query with filters that gets data from the database
     * @param params Query parameters
     * @param criteriaBuilder criteria builder for building the query
     * @return CriteriaQuery<T> object with query
     */
    CriteriaQuery<T> createFilteringGetQuery(
            MultiValueMap<String, String> params,
            CriteriaBuilder criteriaBuilder
    );
}
