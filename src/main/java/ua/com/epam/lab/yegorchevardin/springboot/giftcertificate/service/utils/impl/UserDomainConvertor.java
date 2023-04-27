package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.utils.impl;

import org.springframework.stereotype.Component;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.entities.UserEntity;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.utils.DomainObjectsConvertor;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.dtos.User;

/**
 * Class for converting user domain objects
 * @author yegorchevardin
 * @version 0.0.1
 */
@Component
public class UserDomainConvertor implements DomainObjectsConvertor<UserEntity, User> {
    @Override
    public User convertEntityToDTO(UserEntity entity) {
        User dto = new User();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        return dto;
    }

    @Override
    public UserEntity convertDtoToEntity(User dto) {
        UserEntity entity = new UserEntity();
        entity.setUsername(dto.getUsername());
        return entity;
    }
}
