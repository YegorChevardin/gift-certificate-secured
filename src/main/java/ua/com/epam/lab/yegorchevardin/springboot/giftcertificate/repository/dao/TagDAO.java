package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.dao;

import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.entities.TagEntity;

import java.util.Optional;

/**
 * Interface that describes abstract behavior of tag dao
 * @author yegorchevardin
 * @version 0.0.1
 */
public interface TagDAO extends CreateReadDeleteDAO<TagEntity> {
    /**
     * Retrieves a Tag entity by its name
     * @param name entity name
     * @return an Tag entity
     */
    Optional<TagEntity> findByName(String name);

    /**
     * Retrieves the most popular tag entity with the highest cost of all Order entities.
     * @return the most popular tag entity
     */
    Optional<TagEntity> findMostPopularTagWithOrdersWithHighestCost();
}
