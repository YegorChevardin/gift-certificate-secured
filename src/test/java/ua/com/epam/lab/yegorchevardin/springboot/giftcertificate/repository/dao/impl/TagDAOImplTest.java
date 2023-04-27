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
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.dao.TagDAO;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.entities.TagEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = DaoConfigTest.class)
@Transactional
public class TagDAOImplTest {
    private static final long NOT_EXISTED_ID = 999L;
    private final TagEntity TAG_1 = new TagEntity(1L, "tagName1");
    private final TagEntity TAG_2 = new TagEntity(2L, "tagName3");
    private final TagEntity TAG_3 = new TagEntity(3L, "tagName5");
    private final TagEntity TAG_4 = new TagEntity(4L, "tagName4");
    private final TagEntity TAG_5 = new TagEntity(5L, "tagName2");
    private static final String PART_OF_TAG_NAME = "tagName";
    private static final String NOT_EXISTED_NAME = "not existed name";
    private static final String INCORRECT_FILTER_PARAM = "incorrectParameter";
    private static final String INCORRECT_FILTER_PARAM_VALUE = "incorrectParameterValue";
    private static final String ASCENDING = "ASC";
    private final Pageable pageRequest = PageRequest.of(0, 5);
    
    private final TagDAO TagDAO;

    @Autowired
    public TagDAOImplTest(TagDAO TagDAO) {
        this.TagDAO = TagDAO;
    }

    @Test
    @Sql({"/db/clear_all.sql", "/db/seed_tags.sql"})
    void getById_thenOk() {
        Optional<TagEntity> expected = Optional.of(TAG_1);
        Optional<TagEntity> actual = TagDAO.findById(TAG_1.getId());
        assertEquals(expected, actual);
    }

    @Test
    @Sql({"/db/clear_all.sql", "/db/seed_tags.sql"})
    void getByNotExistedId_thenReturnNull() {
        Optional<TagEntity> actual = TagDAO.findById(NOT_EXISTED_ID);
        assertFalse(actual.isPresent());
    }

    @Test
    @Sql({"/db/clear_all.sql", "/db/seed_tags.sql"})
    void getAll_thenOk() {
        List<TagEntity> actual = TagDAO.findAll(pageRequest);
        List<TagEntity> expected = Arrays.asList(TAG_1, TAG_2, TAG_3, TAG_4, TAG_5);
        assertEquals(expected, actual);
    }

    @Test
    @Sql({"/db/clear_all.sql", "/db/seed_tags.sql"})
    void getWithFilter_thenOk() {
        MultiValueMap<String, String> filterParams = new LinkedMultiValueMap<>();
        filterParams.add(FilterTypes.TAG_NAME.getValue(), PART_OF_TAG_NAME);
        filterParams.add(FilterTypes.SORT_BY_TAG_NAME.getValue(), ASCENDING);

        List<TagEntity> actual = TagDAO.findWithFilter(filterParams, pageRequest);
        List<TagEntity> expected = Arrays.asList(TAG_1, TAG_5, TAG_2, TAG_4, TAG_3);
        assertEquals(expected, actual);
    }

    @Test
    @Sql({"/db/clear_all.sql", "/db/seed_tags.sql"})
    void getWithIncorrectFilter_thenFetchAll() {
        MultiValueMap<String, String> filterParams = new LinkedMultiValueMap<>();
        filterParams.add(INCORRECT_FILTER_PARAM, INCORRECT_FILTER_PARAM_VALUE);

        List<TagEntity> actual = TagDAO.findWithFilter(filterParams, pageRequest);
        List<TagEntity> expected = Arrays.asList(TAG_1, TAG_2, TAG_3, TAG_4, TAG_5);
        assertEquals(expected, actual);
    }

    @Test
    @Sql({"/db/clear_all.sql", "/db/seed_tags.sql"})
    void getByName_thenOk() {
        Optional<TagEntity> expected = Optional.of(TAG_3);
        Optional<TagEntity> actual = TagDAO.findByName(TAG_3.getName());
        assertEquals(expected, actual);
    }

    @Test
    @Sql({"/db/clear_all.sql", "/db/seed_tags.sql"})
    void getByNotExistedName_thenReturnNull() {
        Optional<TagEntity> actual = TagDAO.findByName(NOT_EXISTED_NAME);
        assertFalse(actual.isPresent());
    }

    @Test
    @Sql({"/db/clear_all.sql", "/db/seed_tags.sql", "/db/seed_gift_certificates.sql", "/db/seed_users.sql", "/db/seed_orders.sql"})
    void getMostPopularTagEntityWithHighestCostOfAllOrders_thenOk() {
        Optional<TagEntity> expected = Optional.of(TAG_4);
        Optional<TagEntity> actual = TagDAO.findMostPopularTagWithOrdersWithHighestCost();
        assertEquals(expected, actual);
    }
}
