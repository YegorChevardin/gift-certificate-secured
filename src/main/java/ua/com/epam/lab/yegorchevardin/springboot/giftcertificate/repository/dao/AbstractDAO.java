package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.handlers.QueryHandler;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This class is implementation of create read delete dao interface
 * with basic tools for work with database
 * @param <T> type of entity
 * @author yegorchevardin
 * @version 0.0.1
 */
@RequiredArgsConstructor
public abstract class AbstractDAO<T> implements CreateReadDeleteDAO<T> {
    @PersistenceContext
    protected EntityManager entityManager;
    protected final QueryHandler<T> queryHandler;
    protected final Class<T> entityType;

    @Override
    public Optional<T> findById(long id) {
        return Optional.ofNullable(entityManager.find(entityType, id));
    }

    @Override
    public List<T> findAll(Pageable pageable) {
        return entityManager.createQuery("select e from " + entityType.getSimpleName() + " e", entityType)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
    }

    @Override
    @Transactional
    public Optional<T> insert(T entity) {
        entityManager.persist(entity);
        return Optional.of(entity);
    }

    @Override
    @Transactional
    public void removeById(long id) {
        T entity = entityManager.find(entityType, id);
        entityManager.remove(entity);
    }

    @Override
    public List<T> findWithFilter(MultiValueMap<String, String> params, Pageable pageable) {
        CriteriaQuery<T> criteriaQuery = queryHandler.createFilteringGetQuery(
                params,
                entityManager.getCriteriaBuilder()
        );
        return entityManager.createQuery(criteriaQuery)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultStream()
                .distinct()
                .collect(Collectors.toList());
    }
}
