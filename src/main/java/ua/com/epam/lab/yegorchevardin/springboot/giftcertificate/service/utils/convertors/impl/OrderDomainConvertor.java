package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.utils.convertors.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.entities.GiftCertificateEntity;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.entities.OrderEntity;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.entities.UserEntity;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.utils.convertors.DomainObjectsConvertor;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.dtos.GiftCertificate;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.dtos.Order;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.dtos.User;

/**
 * Class for converting order domain objects
 * @author yegorchevardin
 * @version 0.0.1
 */
@Component
@RequiredArgsConstructor
public class OrderDomainConvertor implements DomainObjectsConvertor<OrderEntity, Order> {
    private final DomainObjectsConvertor<UserEntity, User> userDomainObjectsConvertor;
    private final DomainObjectsConvertor<GiftCertificateEntity, GiftCertificate>
            giftCertificateDomainObjectsConvertor;

    @Override
    public Order convertEntityToDTO(OrderEntity entity) {
        Order dto = new Order();
        dto.setId(entity.getId());
        dto.setCost(entity.getCost());
        dto.setUser(userDomainObjectsConvertor.convertEntityToDTO(entity.getUser()));
        if(dto.getPurchasedAt() != null) {
            dto.setPurchasedAt(String.valueOf(entity.getPurchasedAt().toLocalDateTime()));
        }
        if (entity.getGiftCertificates() != null) {
            dto.setGiftCertificates(entity.getGiftCertificates()
                    .stream().map(
                            giftCertificateDomainObjectsConvertor::convertEntityToDTO
                    ).toList());
        }
        return dto;
    }

    @Override
    public OrderEntity convertDtoToEntity(Order dto) {
        OrderEntity entity = new OrderEntity();
        entity.setUser(userDomainObjectsConvertor.convertDtoToEntity(dto.getUser()));
        entity.setGiftCertificates(dto.getGiftCertificates()
                .stream().map(giftCertificateDomainObjectsConvertor::convertDtoToEntity)
                .toList());
        return entity;
    }
}
