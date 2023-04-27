package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Optional;

/**
 * Interface that describes create read and delete operations
 * for database
 * @author yegorchevardin
 * @version 0.0.1
 */
public interface CreateReadDeleteDAO<T> {
    /**
     * Retrieves an object of T datatype by its id
     * @param id An id of the object
     * @return A object of T datatype
     */
    Optional<T> findById(long id);

    /**
     * Retrieves a List of objects of T datatype
     * @return A List of objects of T datatype
     */
    List<T> findAll(Pageable pageable);

    /**
     * Method for saving an object of T datatype
     * @param entity an object to save.
     * @return saved entity with generated id
     */
    Optional<T> insert(T entity);

    /**
     * Removes an object of T datatype from data source by its id
     * @param id an id of object
     */
    void removeById(long id);

    /**
     * Method for getting a list of objects of T datatype by specific parameters
     * @param params request parameters from URL
     * @return List of objects of T datatype
     */
    List<T> findWithFilter(MultiValueMap<String, String> params, Pageable pageable);
}
