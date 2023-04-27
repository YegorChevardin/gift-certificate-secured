package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.dao.GiftCertificateDAO;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.entities.GiftCertificateEntity;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.exceptions.DataExistException;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.exceptions.DataNotFoundException;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.services.GiftCertificateService;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.services.TagService;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.services.constants.ExceptionMessages;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.utils.DomainObjectsConvertor;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.dtos.GiftCertificate;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateDAO giftCertificateDAO;
    private final TagService tagService;
    private final DomainObjectsConvertor<GiftCertificateEntity, GiftCertificate>
            giftCertificateDomainObjectsConvertor;

    @Override
    public GiftCertificate update(GiftCertificate dto) {
        if (dto.getId() == null) {
            throw new DataNotFoundException(
                    "Cannot update object with en empty id."
            );
        } else if (giftCertificateDAO.findById(dto.getId()).isEmpty()) {
            throw new DataNotFoundException(
                    String.format(
                            ExceptionMessages.GIFT_CERTIFICATE_BY_ID_NOT_FOUND.getValue(),
                            dto.getId()
                    )
            );
        }
        if (giftCertificateDAO.findByName(dto.getName()).isPresent()
                && !Objects.equals(giftCertificateDAO
                .findByName(dto.getName()).get().getId(), dto.getId())) {
            throw new DataExistException(
                    "Cannot update object to this name, because another gift certificate " +
                            "with this name is already exist: " + dto.getName()
            );
        }
        GiftCertificateEntity existedEntity = giftCertificateDAO.findById(dto.getId()).get();
        GiftCertificateEntity entity = giftCertificateDomainObjectsConvertor
                .convertDtoToEntity(dto);
        entity.setId(dto.getId());
        entity.setTags(tagService.insertTagsFromCertificate(entity.getTags()));
        entity.setCreateDate(existedEntity.getCreateDate());
        return giftCertificateDomainObjectsConvertor
                .convertEntityToDTO(giftCertificateDAO.update(entity));
    }

    @Override
    public GiftCertificate findById(long id) {
        return giftCertificateDomainObjectsConvertor.convertEntityToDTO(
                findGiftCertificateByIdIfExists(id)
        );
    }

    @Override
    public List<GiftCertificate> findAll(int page, int size) {
        return giftCertificateDAO.findAll(PageRequest.of(page, size))
                .stream().map(
                        giftCertificateDomainObjectsConvertor::convertEntityToDTO
                ).collect(Collectors.toList());
    }

    @Override
    public GiftCertificate insert(GiftCertificate dto) {
        if (giftCertificateDAO.findByName(dto.getName()).isPresent()) {
            throw new DataExistException(String.format(
                    ExceptionMessages.GIFT_CERTIFICATE_BY_NAME_EXIST.getValue(),
                    dto.getName()
            ));
        }
        GiftCertificateEntity entity = giftCertificateDomainObjectsConvertor
                .convertDtoToEntity(dto);
        entity.setCreateDate(Timestamp.valueOf(LocalDateTime.now()));

        entity.setTags(tagService.insertTagsFromCertificate(entity.getTags()));

        return giftCertificateDomainObjectsConvertor
                .convertEntityToDTO(
                        giftCertificateDAO.insert(entity).orElseThrow(
                                () -> new DataNotFoundException(
                                        String.format(ExceptionMessages.GIFT_CERTIFICATE_BY_ID_NOT_FOUND.getValue(),
                                                entity.getId())
                                )
                        )
                );
    }

    @Override
    public void removeById(long id) {
        findGiftCertificateByIdIfExists(id);
        giftCertificateDAO.removeById(id);
    }

    @Override
    public List<GiftCertificate> doFilter(
            MultiValueMap<String, String> params, int page, int size
    ) {
        return giftCertificateDAO.findWithFilter(params, PageRequest.of(page, size))
                .stream().map(
                        giftCertificateDomainObjectsConvertor::convertEntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public GiftCertificate findByName(String name) {
        return giftCertificateDomainObjectsConvertor.convertEntityToDTO(
                findGiftCertificateByNameIfExist(name)
        );
    }

    @Override
    public List<GiftCertificateEntity> handleGiftCertificatesFromOrder(List<GiftCertificateEntity> certificates) {
        List<GiftCertificateEntity> resultEntities = new ArrayList<>();

        for (GiftCertificateEntity entity : certificates) {
            resultEntities.add(
                    findGiftCertificateByNameIfExist(entity.getName())
            );
        }
        return resultEntities;
    }

    @Override
    public Float countPriceFromAllEntities(List<GiftCertificateEntity> entities) {
        Float cost = 0.0F;
        for (GiftCertificateEntity entity : entities) {
            cost += entity.getPrice();
        }
        return cost;
    }

    private GiftCertificateEntity findGiftCertificateByNameIfExist(String name) {
        return giftCertificateDAO.findByName(name).orElseThrow(
                () -> new DataNotFoundException(
                        ExceptionMessages.GIFT_CERTIFICATE_BY_NAME_DOES_NOT_FOUND.getValue()
                ));
    }

    private GiftCertificateEntity findGiftCertificateByIdIfExists(Long id) {
        return giftCertificateDAO.findById(id).orElseThrow(
                () -> new DataNotFoundException(
                        String.format(
                                ExceptionMessages.GIFT_CERTIFICATE_BY_ID_NOT_FOUND.getValue(), id
                        ))
        );
    }
}
