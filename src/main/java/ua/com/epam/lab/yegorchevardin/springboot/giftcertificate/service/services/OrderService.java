package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.services;

import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.dtos.Order;

import java.util.List;

/**
 * Interface that defines service methods
 * for handling user object operations
 * @author yegorchevardin
 * @version 0.0.1
 */
public interface OrderService extends CreateReadDeleteService<Order> {
    /**
     * Method for getting a entities by specified user id
     * @param userId user id
     * @param page page number for pagination
     * @param size page size for pagination
     * @return a list of order entities
     */
    List<Order> findByUserId (long userId, int page, int size);

    /**
     * Updates entity of T datatype
     * @param dto updated entity
     * @param isPurchased purchased dto or not
     */
    Order update(Order dto, boolean isPurchased);
}
