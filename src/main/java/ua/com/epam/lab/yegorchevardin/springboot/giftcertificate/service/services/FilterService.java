package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.services;

import org.springframework.util.MultiValueMap;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.dtos.GiftCertificate;

import java.util.List;

/**
 * Interface for defining filter operation for object in database
 * @author yegorchevardin
 * @version 0.0.1
 */
public interface FilterService<T> {
    /**
     * Method for getting a list of T objects by specific parameters
     * @param params request parameters from URL
     * @return List of T objects
     */
    List<T> doFilter(MultiValueMap<String, String> params, int page, int size);
}
