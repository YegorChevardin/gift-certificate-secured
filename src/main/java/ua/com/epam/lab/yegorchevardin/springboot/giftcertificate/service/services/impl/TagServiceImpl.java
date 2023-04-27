package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.dao.TagDAO;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.entities.TagEntity;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.exceptions.DataExistException;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.exceptions.DataNotFoundException;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.services.TagService;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.services.constants.ExceptionMessages;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.utils.DomainObjectsConvertor;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.dtos.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagDAO tagDAO;
    private final DomainObjectsConvertor<TagEntity, Tag> tagDomainObjectsConvertor;

    @Override
    public Tag findById(long id) {
        return tagDomainObjectsConvertor.convertEntityToDTO(
                findTagIfExist(id)
        );
    }

    @Override
    public List<Tag> findAll(int page, int size) {
        return tagDAO.findAll(PageRequest.of(page, size)).stream().map(
                tagDomainObjectsConvertor::convertEntityToDTO
        ).collect(Collectors.toList());
    }

    @Override
    public Tag insert(Tag dto) {
        if (tagDAO.findByName(dto.getName()).isPresent()) {
            throw new DataExistException(String.format(
                    ExceptionMessages.TAG_BY_NAME_EXIST.getValue(), dto.getName())
            );
        }
        TagEntity entity = tagDomainObjectsConvertor.convertDtoToEntity(dto);
        return tagDomainObjectsConvertor.convertEntityToDTO(
                tagDAO.insert(entity).orElseThrow(
                        () -> new DataNotFoundException(
                                ExceptionMessages.TAG_BY_ID_NOT_FOUND.getValue()
                        )
                )
        );
    }

    @Override
    public void removeById(long id) {
        findTagIfExist(id);
        tagDAO.removeById(id);
    }

    @Override
    public Tag findMostPopularTagWithOrdersWithHighestCost() {
        return tagDomainObjectsConvertor.convertEntityToDTO(
                tagDAO.findMostPopularTagWithOrdersWithHighestCost().orElseThrow(
                        () -> new DataNotFoundException(
                                ExceptionMessages.TAG_NOT_FOUND.getValue()
                        )
                )
        );
    }

    @Override
    public List<TagEntity> insertTagsFromCertificate(List<TagEntity> tags) {
        List<TagEntity> tagsToUpdate = new ArrayList<>();
        for (TagEntity currentTag : tags) {
            if (tagDAO.findByName(currentTag.getName()).isPresent()) {
                tagsToUpdate.add(
                        tagDAO.findByName(currentTag.getName()).get()
                );
            } else {
                tagsToUpdate.add(currentTag);
            }
        }
        return tagsToUpdate;
    }

    @Override
    public List<Tag> doFilter(MultiValueMap<String, String> params, int page, int size) {
        return tagDAO.findWithFilter(params, PageRequest.of(page, size)).stream().map(
                tagDomainObjectsConvertor::convertEntityToDTO
        ).collect(Collectors.toList());
    }

    private TagEntity findTagIfExist(Long id) {
        return tagDAO.findById(id).orElseThrow(
                () -> new DataNotFoundException(
                        String.format(ExceptionMessages.TAG_BY_ID_NOT_FOUND.getValue(), id)
                )
        );
    }

}
