package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.dao.impl;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.dao.OrderDAO;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.entities.GiftCertificateEntity;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.entities.OrderEntity;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.entities.TagEntity;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.entities.UserEntity;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@SpringBootTest(classes = DaoConfigTest.class)
@Transactional
public class OrderDAOImplTest {
    private static final long NOT_EXISTED_ID = 999L;
    private final Pageable pageRequest = PageRequest.of(0, 5);

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
            Timestamp.valueOf(LocalDateTime.parse("2020-10-20T07:20:15.156")), USER_1, List.of(GIFT_CERTIFICATE_3));
    private final OrderEntity ORDER_2 = new OrderEntity(2L, 30.30F,
            Timestamp.valueOf(LocalDateTime.parse("2019-10-20T07:20:15.156")), USER_1, List.of(GIFT_CERTIFICATE_2));
    
    OrderDAO orderDao;
    
    @Autowired
    public OrderDAOImplTest(OrderDAO orderDAO) {
        this.orderDao = orderDAO;
    }

    @Test
    @Sql({"/db/clear_all.sql", "/db/seed_tags.sql", "/db/seed_gift_certificates.sql", "/db/seed_users.sql", "/db/seed_orders.sql"})
    void findByUserId_thenOk() {
        List<OrderEntity> expected = Arrays.asList(ORDER_1, ORDER_2);
        List<OrderEntity> actual = orderDao.findByUserId(USER_1.getId(), pageRequest);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @Sql({"/db/clear_all.sql", "/db/seed_tags.sql", "/db/seed_gift_certificates.sql", "/db/seed_users.sql", "/db/seed_orders.sql"})
    void findNotExistedUserId_thenReturnNull() {
        List<OrderEntity> actual = orderDao.findByUserId(NOT_EXISTED_ID, pageRequest);
        Assertions.assertTrue(actual.isEmpty());
    }
}
