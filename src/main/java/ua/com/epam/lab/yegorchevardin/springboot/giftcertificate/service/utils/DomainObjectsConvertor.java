package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.utils;

/**
 * Interface for defining basic methods for converting domain models
 * @param <E> Entity object
 * @param <D> DTO object
 * @author yegorchevardin
 * @version 0.0.1
 */
public interface DomainObjectsConvertor<E, D> {
    /**
     * Method for converting entity object to dto
     * @param entity entity object to convert
     * @return dto object
     */
    D convertEntityToDTO(E entity);

    /**
     * Method for converting dto object to entity
     * @param dto dto object to convert
     * @return entity object
     */
    E convertDtoToEntity(D dto);
}