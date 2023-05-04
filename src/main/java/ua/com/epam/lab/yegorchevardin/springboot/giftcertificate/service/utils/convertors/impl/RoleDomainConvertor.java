package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.utils.convertors.impl;

import org.springframework.stereotype.Component;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.entities.RoleEntity;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.utils.convertors.DomainObjectsConvertor;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.dtos.Role;

@Component
public class RoleDomainConvertor implements DomainObjectsConvertor<RoleEntity, Role> {
    @Override
    public Role convertEntityToDTO(RoleEntity entity) {
        Role dto = new Role();
        dto.setName(entity.getName());
        dto.setId(entity.getId());
        return dto;
    }

    @Override
    public RoleEntity convertDtoToEntity(Role dto) {
        RoleEntity entity = new RoleEntity();
        entity.setName(dto.getName());
        return entity;
    }
}
