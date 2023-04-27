package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.dao;

import org.springframework.data.domain.Pageable;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.entities.OrderEntity;

import java.util.List;

/**
 * Interface that describes abstract behavior of order dao
 * @author yegorchevardin
 * @version 0.0.1
 */
public interface OrderDAO extends CreateReadUpdateDeleteDAO<OrderEntity> {
    /**
     * Retrieves a List of orders entities
     * which belongs to user with specified id
     * @param userId user id
     * @param pageable object with pagination information
     * @return List of order entities.
     */
    List<OrderEntity> findByUserId(long userId, Pageable pageable);

}
