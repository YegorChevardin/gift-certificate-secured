package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.dao;

/**
 * Interface for Create Read Update Delete operations for database
 * @author yegorchevardin
 * @version 0.0.1
 */
public interface CreateReadUpdateDeleteDAO<T> extends CreateReadDeleteDAO<T> {
    /**
     * Updates an entity of T datatype in database
     * @param entity an updated entity
     * @return an updated entity
     */
    T update(T entity);
}
