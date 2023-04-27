package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.dao.OrderDAO;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.entities.GiftCertificateEntity;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.entities.OrderEntity;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.exceptions.DataExistException;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.exceptions.DataNotFoundException;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.services.GiftCertificateService;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.services.OrderService;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.services.UserService;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.services.constants.ExceptionMessages;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.utils.DomainObjectsConvertor;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.dtos.Order;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderDAO orderDAO;
    private final DomainObjectsConvertor<OrderEntity, Order> orderDomainObjectsConvertor;
    private final GiftCertificateService giftCertificateService;
    private final UserService userService;

    @Override
    public Order findById(long id) {
        return orderDomainObjectsConvertor.convertEntityToDTO(
                findByIdIfExists(id)
        );
    }

    @Override
    public List<Order> findAll(int page, int size) {
        return orderDAO.findAll(PageRequest.of(page, size)).stream()
                .map(orderDomainObjectsConvertor::convertEntityToDTO)
                .toList();
    }

    @Override
    public Order insert(Order dto) {
        return orderDomainObjectsConvertor.convertEntityToDTO(
                orderDAO.insert(prepareOrderEntity(dto, false)).orElseThrow(
                        () -> new DataExistException(
                                ExceptionMessages.ORDER_NOT_FOUND_BY_ID.getValue()
                        )
                )
        );
    }

    @Override
    public void removeById(long id) {
        findByIdIfExists(id);
        orderDAO.removeById(id);
    }

    @Override
    public Order update(Order dto, boolean isPurchased) {
        if (dto.getId() == null) {
            throw new DataNotFoundException(
                    "Cannot update order with empty id"
            );
        }
        OrderEntity entity = prepareOrderEntity(dto, isPurchased);
        entity.setId(dto.getId());
        return orderDomainObjectsConvertor.convertEntityToDTO(
                orderDAO.update(entity)
        );
    }

    @Override
    public List<Order> findByUserId(long userId, int page, int size) {
        return orderDAO.findByUserId(userId, PageRequest.of(page, size)).stream()
                .map(orderDomainObjectsConvertor::convertEntityToDTO).toList();
    }

    private OrderEntity prepareOrderEntity(Order order, boolean purchase) {
        order.setUser(userService.findByUsername(order.getUser().getUsername()));
        OrderEntity entity = orderDomainObjectsConvertor.convertDtoToEntity(order);
        entity.getUser().setId(order.getUser().getId());
        entity.setGiftCertificates(
                giftCertificateService.
                        handleGiftCertificatesFromOrder(
                                entity.getGiftCertificates()
                        )
        );
        entity.setCost(giftCertificateService
                .countPriceFromAllEntities(entity.getGiftCertificates()));
        if (purchase) {
            entity.setPurchasedAt(Timestamp.valueOf(LocalDateTime.now()));
        }
        return entity;
    }

    private OrderEntity findByIdIfExists(Long id) {
        return orderDAO.findById(id).orElseThrow(
                () -> new DataNotFoundException(
                        String.format(
                                ExceptionMessages.ORDER_NOT_FOUND_BY_ID.getValue(),
                                id
                        )
                )
        );
    }
}
