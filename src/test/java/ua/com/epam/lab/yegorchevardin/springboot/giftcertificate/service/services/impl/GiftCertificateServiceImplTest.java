package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.services.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.constants.FilterTypes;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.dao.impl.GiftCertificateDAOImpl;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.entities.GiftCertificateEntity;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.entities.TagEntity;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.exceptions.DataExistException;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.exceptions.DataNotFoundException;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.utils.impl.GiftCertificateDomainConvertor;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.dtos.GiftCertificate;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.dtos.Tag;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@ExtendWith(MockitoExtension.class)
public class GiftCertificateServiceImplTest {
    @Mock
    GiftCertificateDAOImpl certificateDao;
    @Mock
    TagServiceImpl tagServiceImpl;
    @Mock
    GiftCertificateDomainConvertor giftCertificateDomainConvertor;
    @InjectMocks
    GiftCertificateServiceImpl certificateService;

    private static final long NOT_EXISTED_ID = 999L;
    private final LocalDateTime UPDATED_DATE = LocalDateTime.parse("2019-10-20T07:20:15.156");
    private static final String NOT_EXISTED_NAME = "not existed name";
    private static final String PART_OF_CERTIFICATE_NAME = "giftCertificate1";
    private static final String TAG_3_NAME = "tagName3";
    private static final String TAG_4_NAME = "tagName4";
    private static final String ASCENDING = "ASC";
    private static final int PAGE = 0;
    private static final int SIZE = 5;

    private final GiftCertificateEntity GIFT_CERTIFICATE_1 =
            new GiftCertificateEntity(1L, "giftCertificate1",
            "description1", 99.90F, 1,
            Timestamp.valueOf(LocalDateTime.parse("2020-10-20T07:20:15.156")),
                    Timestamp.valueOf(LocalDateTime.parse("2020-10-20T07:20:15.156"))
            , List.of(new TagEntity(2L, "tagName3")));

    private final GiftCertificateEntity GIFT_CERTIFICATE_2 =
            new GiftCertificateEntity(2L, "giftCertificate3",
            "description3", 100.99F, 3,
            Timestamp.valueOf(LocalDateTime.parse("2019-10-20T07:20:15.156")),
                    Timestamp.valueOf(LocalDateTime.parse("2019-10-20T07:20:15.156")),
            Arrays.asList(new TagEntity(2L, "tagName3"),
                    new TagEntity(4L, "tagName4")));

    private final GiftCertificateEntity GIFT_CERTIFICATE_3 =
            new GiftCertificateEntity(3L, "giftCertificate2",
            "description2", 999.99F, 2,
            Timestamp.valueOf(LocalDateTime.parse("2018-10-20T07:20:15.156")),
                    Timestamp.valueOf(LocalDateTime.parse("2018-10-20T07:20:15.156")),
            Arrays.asList(new TagEntity(4L, "tagName4"), new TagEntity(2L, "tagName3")));

    private final GiftCertificateEntity NEW_ADDED_CERTIFICATE =
            new GiftCertificateEntity(0L, "giftCertificate3",
            "description3", 100.99F, 3, null, null,
            Arrays.asList(new TagEntity(0L, "tagName3"), new TagEntity(0L, "tagName4")));

    private final GiftCertificateEntity BEFORE_INSERT_CERTIFICATE =
            new GiftCertificateEntity(0L, "giftCertificate3",
            "description3", 100.99F, 3,
            Timestamp.valueOf(LocalDateTime.parse("2019-10-20T07:20:15.156")),
                    Timestamp.valueOf(LocalDateTime.parse("2019-10-20T07:20:15.156")),
            Arrays.asList(new TagEntity(2L, "tagName3"), new TagEntity(4L, "tagName4")));

    private final GiftCertificate NEW_DATA_CERTIFICATE =
            new GiftCertificate(0L, "giftCertificate22",
            "description22", 9999.99F, 22,
            "2018-10-20T07:20:15.156", "2018-10-20T07:20:15.156",
            Collections.singletonList(new Tag(3L, "tagName4")));

    private final GiftCertificateEntity BEFORE_UPDATE_CERTIFICATE =
            new GiftCertificateEntity(3L, "giftCertificate22",
            "description22", 9999.99F, 22,
            Timestamp.valueOf(LocalDateTime.parse("2018-10-20T07:20:15.156")),
                    Timestamp.valueOf(LocalDateTime.parse("2019-10-20T07:20:15.156")),
            Collections.singletonList(new TagEntity(2L, "tagName4")));

    @Test
    void getById_thenOk() {
        Mockito.when(certificateDao.findById(GIFT_CERTIFICATE_1.getId())).thenReturn(Optional.of(GIFT_CERTIFICATE_1));
        GiftCertificate actual = certificateService.findById(GIFT_CERTIFICATE_1.getId());
        Assertions.assertEquals(giftCertificateDomainConvertor.convertEntityToDTO(
                GIFT_CERTIFICATE_1
        ), actual);
    }

    @Test
    void getByNotExistedId_thenThrowEx() {
        Mockito.when(certificateDao.findById(NOT_EXISTED_ID)).thenReturn(Optional.empty());
        assertThrows(DataNotFoundException.class, () -> certificateService.findById(NOT_EXISTED_ID));
    }

    @Test
    void getAll_thenOk() {
        Pageable pageRequest = PageRequest.of(0, 5);
        List<GiftCertificateEntity> expected =
                Arrays.asList(GIFT_CERTIFICATE_1, GIFT_CERTIFICATE_2, GIFT_CERTIFICATE_3);
        Mockito.when(certificateDao.findAll(pageRequest)).thenReturn(expected);
        List<GiftCertificate> actual = certificateService.findAll(PAGE, SIZE);
        Assertions.assertEquals(expected.stream().map(
                (element) -> giftCertificateDomainConvertor.convertEntityToDTO(
                        element
                )
        ).collect(Collectors.toList()), actual);
    }

    @Test
    void doFilter_thenOk() {
        Pageable pageRequest = PageRequest.of(0, 5);

        MultiValueMap<String, String> filterParams = new LinkedMultiValueMap<>();
        filterParams.add(FilterTypes.NAME.getValue(), PART_OF_CERTIFICATE_NAME);
        filterParams.add(FilterTypes.NAME_SORT.getValue(), ASCENDING);

        List<GiftCertificateEntity> expected = Collections.singletonList(GIFT_CERTIFICATE_1);
        Mockito.when(certificateDao.findWithFilter(filterParams, pageRequest)).thenReturn(expected);
        List<GiftCertificate> actual = certificateService.doFilter(filterParams, PAGE, SIZE);
        assertEquals(expected.stream().map(
                (element) -> giftCertificateDomainConvertor.convertEntityToDTO(
                        element
                )
        ).collect(Collectors.toList()), actual);
    }

    @Test
    void updateNotExistedEntity_thenThrowEx() {
        Mockito.when(certificateDao.findById(NEW_DATA_CERTIFICATE.getId())).thenReturn(Optional.empty());
        assertThrows(DataNotFoundException.class, () -> certificateService.update(
                NEW_DATA_CERTIFICATE)
        );
    }

    @Test
    void getByName_thenOk() {
        Mockito.when(certificateDao.findByName(GIFT_CERTIFICATE_1.getName())).thenReturn(Optional.of(GIFT_CERTIFICATE_1));
        GiftCertificate actual = certificateService.findByName(GIFT_CERTIFICATE_1.getName());
        assertEquals(giftCertificateDomainConvertor.convertEntityToDTO(
                GIFT_CERTIFICATE_1
        ), actual);
    }

    @Test
    void getByNotExistedName_thenThrowEx() {
        Mockito.when(certificateDao.findByName(NOT_EXISTED_NAME)).thenReturn(Optional.empty());
        assertThrows(DataNotFoundException.class, () -> certificateService.findByName(NOT_EXISTED_NAME));
    }
}
