package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.dao.impl.OrderDAOImpl;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.entities.GiftCertificateEntity;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.entities.OrderEntity;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.entities.TagEntity;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.entities.UserEntity;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.utils.impl.OrderDomainConvertor;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.dtos.Order;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {
    @Mock
    OrderDAOImpl orderDao;

    @Mock
    OrderDomainConvertor orderDomainConvertor;

    @InjectMocks
    OrderServiceImpl orderService;
    private static final int PAGE = 0;
    private static final int SIZE = 5;
    private final UserEntity USER_1 = new UserEntity(1L, "name1");

    private final GiftCertificateEntity GIFT_CERTIFICATE_2 = new GiftCertificateEntity(2L, "giftCertificate3",
            "description3", 100.99F, 3,
            Timestamp.valueOf(LocalDateTime.parse("2019-10-20T07:20:15.156")),
            Timestamp.valueOf(LocalDateTime.parse("2019-10-20T07:20:15.156")),
            Arrays.asList(new TagEntity(2L, "tagName3"), new TagEntity(4L, "tagName4")));

    private final GiftCertificateEntity GIFT_CERTIFICATE_3 = new GiftCertificateEntity(3L, "giftCertificate2",
            "description2", 999.99F, 2,
            Timestamp.valueOf(LocalDateTime.parse("2018-10-20T07:20:15.156")),
            Timestamp.valueOf(LocalDateTime.parse("2018-10-20T07:20:15.156")),
            Arrays.asList(new TagEntity(4L, "tagName4"), new TagEntity(2L, "tagName3")));

    private final OrderEntity ORDER_1 = new OrderEntity(1L, 10.10F,
            Timestamp.valueOf(LocalDateTime.parse("2020-10-20T07:20:15.156")), USER_1,
            List.of(GIFT_CERTIFICATE_3));
    private final OrderEntity ORDER_2 = new OrderEntity(2L, 100.99F,
            Timestamp.valueOf(LocalDateTime.parse("2019-10-20T07:20:15.156")),
            USER_1, List.of(GIFT_CERTIFICATE_2));

    @Test
    void getOrdersByUserId_thenOk() {
        PageRequest pageRequest = PageRequest.of(PAGE, SIZE);
        List<OrderEntity> expected = Arrays.asList(ORDER_1, ORDER_2);
        Mockito.when(orderDao.findByUserId(USER_1.getId(), pageRequest))
                .thenReturn(expected);
        List<Order> actual = orderService.findByUserId(USER_1.getId(), PAGE, SIZE);
        assertEquals(expected.stream().map(
                (element) -> orderDomainConvertor.convertEntityToDTO(element)
        ).collect(Collectors.toList()), actual);
    }
}
