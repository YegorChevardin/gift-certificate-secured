package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.handlers.linkbuilders;

import org.springframework.hateoas.RepresentationModel;

/**
 * Interface for creation relation between objects with http link
 * @param <T> object element for which needs to create link
 * @author yegorchevardin
 * @version 0.0.1
 */
public interface LinkBuilder<T extends RepresentationModel<T>> {
    void buildLinks(T dto);
}
