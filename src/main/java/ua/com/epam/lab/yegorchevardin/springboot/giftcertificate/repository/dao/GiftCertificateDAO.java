package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.dao;

import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.entities.GiftCertificateEntity;

import java.util.Optional;

/**
 * Interface that describes abstract behavior of gift certificate dao
 * @author yegorchevardin
 * @version 0.0.1
 */
public interface GiftCertificateDAO extends CreateReadUpdateDeleteDAO<GiftCertificateEntity> {
    /**
     * Retrieves the gift certificate entity by its name
     * @param name entity name
     * @return the gift certificate entity
     */
    Optional<GiftCertificateEntity> findByName(String name);
}
