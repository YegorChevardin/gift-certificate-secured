package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.services;

import java.util.List;

/**
 * Interface that defines basic CRD methods for manipulating data
 * @author yegorchevardin
 * @version 0.0.1
 */
public interface CreateReadDeleteService<T> {
    /**
     * Retrieves a T object by id
     * @param id an id of the object
     * @return a T object
     */
    T findById(long id);

    /**
     * Retrieves a List of T objects
     * @return A List of T objects
     */
    List<T> findAll(int page, int size);

    /**
     * Method for saving an T entity
     * @param dto an T entity to save
     */
    T insert(T dto);

    /**
     * Removes an T entity from data source by id
     * @param id an id of T entity
     */
    void removeById(long id);
}
