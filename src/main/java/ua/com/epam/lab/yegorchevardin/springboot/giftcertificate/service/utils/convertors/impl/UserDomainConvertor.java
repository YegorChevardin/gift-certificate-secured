package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.utils.convertors.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.entities.RoleEntity;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.entities.UserEntity;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.utils.convertors.DomainObjectsConvertor;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.dtos.Role;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.dtos.User;

/**
 * Class for converting user domain objects
 * @author yegorchevardin
 * @version 0.0.1
 */
@Component
@RequiredArgsConstructor
public class UserDomainConvertor implements DomainObjectsConvertor<UserEntity, User> {
    private final DomainObjectsConvertor<RoleEntity, Role>
            roleDomainObjectsConvertor;

    @Override
    public User convertEntityToDTO(UserEntity entity) {
        User dto = new User();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setRoles(entity.getRoles().stream().map(
                roleDomainObjectsConvertor::convertEntityToDTO).toList()
        );
        return dto;
    }

    @Override
    public UserEntity convertDtoToEntity(User dto) {
        UserEntity entity = new UserEntity();
        entity.setUsername(dto.getUsername());
        return entity;
    }
}
