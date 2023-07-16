package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.dao.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.dao.AbstractDAO;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.dao.OrderDAO;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.entities.OrderEntity;
import java.util.List;

@Repository
public class OrderDAOImpl extends AbstractDAO<OrderEntity> implements OrderDAO {
    private static final String FIND_BY_USER_ID_QUERY =
            "select o from OrderEntity o where o.user.id = :userId";

    private static final String COUNT_ENTITIES =
            "select count(o) from OrderEntity o";

    @Autowired
    public OrderDAOImpl() {
        super(null, OrderEntity.class);
    }

    @Override
    public List<OrderEntity> findByUserId(long userId, Pageable pageable) {
        return entityManager.createQuery(FIND_BY_USER_ID_QUERY, OrderEntity.class)
                .setParameter("userId", userId)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultStream()
                .distinct()
                .toList();
    }

    @Override
    @Transactional
    public OrderEntity update(OrderEntity entity) {
        return entityManager.merge(entity);
    }

    @Override
    public Integer countEntities() {
        return (int) Math.floor(entityManager.createQuery(COUNT_ENTITIES, Long.class).getResultStream().findFirst().orElse(0L));
    }
}
