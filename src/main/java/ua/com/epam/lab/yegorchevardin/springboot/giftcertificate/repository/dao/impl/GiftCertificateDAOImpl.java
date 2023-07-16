package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.dao.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.dao.AbstractDAO;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.dao.GiftCertificateDAO;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.entities.GiftCertificateEntity;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.handlers.QueryHandler;
import java.util.Optional;

@Repository
public class GiftCertificateDAOImpl
        extends AbstractDAO<GiftCertificateEntity> implements GiftCertificateDAO {
    private static final String SELECT_BY_NAME =
            "select c from GiftCertificateEntity c where c.name = :name";

    private static final String COUNT_ENTITIES =
            "select count(c) from GiftCertificateEntity c";

    @Autowired
    public GiftCertificateDAOImpl(
            QueryHandler<GiftCertificateEntity> queryCreator
    ) {
        super(queryCreator, GiftCertificateEntity.class);
    }

    @Override
    @Transactional
    public GiftCertificateEntity update(GiftCertificateEntity entity) {
        return entityManager.merge(entity);
    }

    @Override
    public Optional<GiftCertificateEntity> findByName(String name) {
        return entityManager.createQuery(SELECT_BY_NAME, GiftCertificateEntity.class)
                .setParameter("name", name)
                .getResultStream()
                .findFirst();
    }

    @Override
    public Integer countEntities() {
        return (int) Math.floor(entityManager.createQuery(COUNT_ENTITIES, Long.class).getResultStream().findFirst().orElse(0L));
    }
}
