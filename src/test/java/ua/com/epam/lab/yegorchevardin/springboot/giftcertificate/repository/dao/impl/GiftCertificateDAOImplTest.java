package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.dao.impl;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.constants.FilterTypes;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.dao.GiftCertificateDAO;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.entities.GiftCertificateEntity;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.entities.TagEntity;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = DaoConfigTest.class)
@Transactional
public class GiftCertificateDAOImplTest {
    private static final long NOT_EXISTED_ID = 999L;
    private static final String NOT_EXISTED_NAME = "not existed name";
    private static final String INCORRECT_FILTER_PARAM = "incorrectParameter";
    private static final String INCORRECT_FILTER_PARAM_VALUE = "incorrectParameterValue";
    private static final String PART_OF_CERTIFICATE_NAME = "giftCertificate";
    private static final String PART_OF_DESCRIPTION = "description";
    private static final String TAG_3_NAME = "tagName3";
    private static final String TAG_4_NAME = "tagName4";
    private static final String ASCENDING = "ASC";
    private final Pageable pageRequest = PageRequest.of(0, 5);

    private final GiftCertificateEntity GIFT_CERTIFICATE_1 = new GiftCertificateEntity(1L, "giftCertificate1",
            "description1", 99.90F, 1,
            Timestamp.valueOf(LocalDateTime.parse("2020-10-20T07:20:15.156")),
            Timestamp.valueOf(LocalDateTime.parse("2020-10-20T07:20:15.156")),
            Collections.singletonList(new TagEntity(2L, "tagName3")));

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
    
    private final GiftCertificateDAO certificateDao;
    
    @Autowired
    public GiftCertificateDAOImplTest(GiftCertificateDAO certificateDao) {
        this.certificateDao = certificateDao;
    }

    @Test
    @Sql({"/db/clear_all.sql", "/db/seed_tags.sql", "/db/seed_gift_certificates.sql"})
    void getById_thenOk() {
        Optional<GiftCertificateEntity> expected = Optional.of(GIFT_CERTIFICATE_1);
        Optional<GiftCertificateEntity> actual = certificateDao.findById(GIFT_CERTIFICATE_1.getId());
        assertEquals(expected, actual);
    }

    @Test
    @Sql({"/db/clear_all.sql", "/db/seed_tags.sql", "/db/seed_gift_certificates.sql"})
    void getByNotExistedId_thenReturnNull() {
        Optional<GiftCertificateEntity> actual = certificateDao.findById(NOT_EXISTED_ID);
        assertFalse(actual.isPresent());
    }

    @Test
    @Sql({"/db/clear_all.sql", "/db/seed_tags.sql", "/db/seed_gift_certificates.sql"})
    void getAll_thenOk() {
        List<GiftCertificateEntity> expected = Arrays.asList(GIFT_CERTIFICATE_1, GIFT_CERTIFICATE_2, GIFT_CERTIFICATE_3);
        List<GiftCertificateEntity> actual = certificateDao.findAll(pageRequest);
        assertEquals(expected, actual);
    }

    @Test
    @Sql({"/db/clear_all.sql", "/db/seed_tags.sql", "/db/seed_gift_certificates.sql"})
    void getWithFilter_thenOk() {
        MultiValueMap<String, String> filterParams = new LinkedMultiValueMap<>();
        filterParams.add(FilterTypes.NAME.getValue(), PART_OF_CERTIFICATE_NAME);
        filterParams.add(FilterTypes.DESCRIPTION.getValue(), PART_OF_DESCRIPTION);
        filterParams.put(FilterTypes.TAG_NAME.getValue(), Arrays.asList(TAG_3_NAME, TAG_4_NAME));
        filterParams.add(FilterTypes.DATE_SORT.getValue(), ASCENDING);

        List<GiftCertificateEntity> expected = List.of(GIFT_CERTIFICATE_3);
        List<GiftCertificateEntity> actual = certificateDao.findWithFilter(filterParams, pageRequest);

        assertEquals(expected, actual);
    }

    @Test
    @Sql({"/db/clear_all.sql", "/db/seed_tags.sql", "/db/seed_gift_certificates.sql"})
    void getWithIncorrectFilter_thenReturnAll() {
        MultiValueMap<String, String> filterParams = new LinkedMultiValueMap<>();
        filterParams.add(INCORRECT_FILTER_PARAM, INCORRECT_FILTER_PARAM_VALUE);
        List<GiftCertificateEntity> expected = Arrays.asList(GIFT_CERTIFICATE_1, GIFT_CERTIFICATE_2, GIFT_CERTIFICATE_3);
        List<GiftCertificateEntity> actual = certificateDao.findWithFilter(filterParams, pageRequest);

        assertEquals(expected, actual);
    }

    @Test
    @Sql({"/db/clear_all.sql", "/db/seed_tags.sql", "/db/seed_gift_certificates.sql"})
    void getByName_thenOk() {
        Optional<GiftCertificateEntity> expected = Optional.of(GIFT_CERTIFICATE_1);
        Optional<GiftCertificateEntity> actual = certificateDao.findByName(GIFT_CERTIFICATE_1.getName());
        assertEquals(expected, actual);
    }

    @Test
    @Sql({"/db/clear_all.sql", "/db/seed_tags.sql", "/db/seed_gift_certificates.sql"})
    void getByNotExistedName_thenReturnNull() {
        Optional<GiftCertificateEntity> actual = certificateDao.findByName(NOT_EXISTED_NAME);
        assertFalse(actual.isPresent());
    }
}
