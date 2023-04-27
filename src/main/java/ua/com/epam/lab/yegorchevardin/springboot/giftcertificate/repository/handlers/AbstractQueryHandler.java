package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.handlers;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.exceptions.IncorrectSortingParameterException;

/**
 * This class provides tools for creating a query
 * @author yegorchevardin
 * @version 0.0.1
 */
public abstract class AbstractQueryHandler {
    private static final String ERROR_MESSAGE =
            "Incorrect sorting type: %s";

    /**
     * Creates "like" predicate for the query
     * @param criteriaBuilder a query builder object
     * @param expression entity query expression
     * @param parameter parameter that used in the predicate
     * @return the "like" query of Predicate datatype
     */
    protected Predicate addLikePredicate(CriteriaBuilder criteriaBuilder,
                                         Expression<String> expression,
                                         String parameter) {
        return criteriaBuilder.like(
                criteriaBuilder.lower(expression),
                "%" + parameter.toLowerCase() + "%");
    }

    /**
     * Creates a sort constraint for the query
     * @param criteriaBuilder a query builder object
     * @param expression entity query expression
     * @param sortType type of sort
     * @return a sort constraint object for the query
     */
    protected Order addOrder(CriteriaBuilder criteriaBuilder,
                             Expression<String> expression,
                             String sortType) {
        Order order = null;
        switch (sortType.toLowerCase()) {
            case "asc": {
                order = criteriaBuilder.asc(expression);
                break;
            }
            case "desc": {
                order = criteriaBuilder.desc(expression);
                break;
            }
            default: {
                throw new IncorrectSortingParameterException(
                        String.format(ERROR_MESSAGE, sortType));
            }
        }
        return order;
    }
}
