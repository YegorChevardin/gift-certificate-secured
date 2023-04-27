package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.services;

/**
 * Interface that defines basic CRUD methods for manipulating data
 * @author yegorchevardin
 * @version 0.0.1
 */
public interface CreateReadUpdateDeleteService<T> extends CreateReadDeleteService<T> {
    /**
     * Updates entity of T datatype
     * @param dto updated entity
     */
    T update(T dto);
}
