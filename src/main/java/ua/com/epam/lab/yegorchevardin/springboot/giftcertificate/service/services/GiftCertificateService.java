package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.services;

import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.entities.GiftCertificateEntity;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.dtos.GiftCertificate;

import java.util.List;

/**
 * Interface that defines service methods
 * for handling gift certificate object operations
 * @author yegorchevardin
 * @version 0.0.1
 */
public interface GiftCertificateService
        extends CreateReadUpdateDeleteService<GiftCertificate>,
        FilterService<GiftCertificate> {
    /**
     * Method for getting a gift certificate entity by name.
     * @param name name of entity
     * @return a gift certificate entity.
     */
    GiftCertificate findByName(String name);

    /**
     * Method for preparing gift certificates for updating or inserting order
     * @param certificates certificates to prepare
     */
    List<GiftCertificateEntity> handleGiftCertificatesFromOrder
            (List<GiftCertificateEntity> certificates);

    /**
     * Method for getting proce of all gifet-certificates in a list
     * @param entities entities to handle
     * @return cost of all entities
     */
    Float countPriceFromAllEntities(List<GiftCertificateEntity> entities);
}
