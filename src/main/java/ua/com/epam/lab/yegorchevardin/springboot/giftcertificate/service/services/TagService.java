package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.services;

import org.springframework.util.MultiValueMap;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.entities.TagEntity;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.dtos.Tag;

import java.util.List;

/**
 * Interface that defines service methods
 * for handling tag object operations
 * @author yegorchevardin
 * @version 0.0.1
 */
public interface TagService extends CreateReadDeleteService<Tag>, FilterService<Tag> {
    /**
     * Method for getting the most popular tag entity
     * with the highest cost of all orders
     * @return the most popular Tag entity
     */
    Tag findMostPopularTagWithOrdersWithHighestCost();

    /**
     * Method for inserting only new tags to the database
     * from all tags from gift certificate
     * @param tags tags entities to handle
     * @return List<TagEntity> tag entities prepared for updating gift certificate
     */
    List<TagEntity> insertTagsFromCertificate(List<TagEntity> tags);
}
