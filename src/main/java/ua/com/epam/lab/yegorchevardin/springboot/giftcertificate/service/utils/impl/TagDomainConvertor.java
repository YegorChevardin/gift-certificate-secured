package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.utils.impl;

import org.springframework.stereotype.Component;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.entities.TagEntity;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.utils.DomainObjectsConvertor;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.dtos.Tag;

/**
 * Class for converting tag domain objects
 * @author yegorchevardin
 * @version 0.0.1
 */
@Component
public class TagDomainConvertor implements DomainObjectsConvertor<TagEntity, Tag> {
    @Override
    public Tag convertEntityToDTO(TagEntity entity) {
        Tag dto = new Tag();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return dto;
    }

    @Override
    public TagEntity convertDtoToEntity(Tag dto) {
        TagEntity entity = new TagEntity();
        entity.setName(dto.getName());
        return entity;
    }
}
