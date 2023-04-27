package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.constants.FilterTypes;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.dao.impl.TagDAOImpl;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.entities.TagEntity;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.exceptions.DataExistException;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.exceptions.DataNotFoundException;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.utils.impl.TagDomainConvertor;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.dtos.Tag;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TagServiceImplTest {
    @Mock
    TagDAOImpl tagDao;

    @Mock
    TagDomainConvertor tagDomainConvertor;

    @InjectMocks
    TagServiceImpl tagService;

    private final TagEntity TAG_1 = new TagEntity(1L, "tagName1");
    private final TagEntity TAG_2 = new TagEntity(2L, "tagName3");
    private final TagEntity TAG_3 = new TagEntity(3L, "tagName2");
    private final TagEntity TAG_4 = new TagEntity(4L, "tagName4");

    private final TagEntity TAG_5 = new TagEntity(5L, "TagName5");
    private final Tag NEW_INSERT_TAG = new Tag(0L, "tagName5");
    private final TagEntity BEFORE_INSERT_TAG = new TagEntity(0L, "TagName5");

    private static final long NOT_EXISTED_ID = 999L;
    private static final String PART_OF_TAG_NAME = "tagName";
    private static final String INCORRECT_FILTER_PARAM = "incorrectParameter";
    private static final String INCORRECT_FILTER_PARAM_VALUE = "incorrectParameterValue";
    private static final String ASCENDING = "ASC";
    private static final int PAGE = 0;
    private static final int SIZE = 5;

    @Test
    void getById_thenOk() {
        Mockito.when(tagDao.findById(TAG_1.getId())).thenReturn(Optional.of(TAG_1));
        Tag actual = tagService.findById(TAG_1.getId());
        assertEquals(tagDomainConvertor.convertEntityToDTO(TAG_1), actual);
    }

    @Test
    void getByNotExistedId_thenThrow() {
        Mockito.when(tagDao.findById(NOT_EXISTED_ID)).thenReturn(Optional.empty());
        assertThrows(DataNotFoundException.class, () -> tagService.findById(NOT_EXISTED_ID));
    }

    @Test
    void getAll_thenOk() {
        PageRequest pageRequest = PageRequest.of(PAGE, SIZE);
        List<TagEntity> expected = Arrays.asList(TAG_1, TAG_2, TAG_5, TAG_4, TAG_5);
        Mockito.when(tagDao.findAll(pageRequest)).thenReturn(expected);
        List<Tag> actual = tagService.findAll(PAGE, SIZE);
        assertEquals(expected.stream().map(
                (element) -> tagDomainConvertor.convertEntityToDTO(element)
        ).collect(Collectors.toList()), actual);
    }

    @Test
    void doFilter_thenOk() {
        PageRequest pageRequest = PageRequest.of(PAGE, SIZE);

        MultiValueMap<String, String> filterParams = new LinkedMultiValueMap<>();
        filterParams.add(FilterTypes.TAG_NAME.getValue(), PART_OF_TAG_NAME);
        filterParams.add(FilterTypes.SORT_BY_TAG_NAME.getValue(), ASCENDING);

        List<TagEntity> expected = Arrays.asList(TAG_1, TAG_3, TAG_2, TAG_4);
        Mockito.when(tagDao.findWithFilter(filterParams, pageRequest)).thenReturn(expected);
        List<Tag> actual = tagService.doFilter(filterParams, PAGE, SIZE);
        assertEquals(expected.stream().map(
                (element) -> tagDomainConvertor.convertEntityToDTO(element)
        ).collect(Collectors.toList()), actual);
    }

    @Test
    void doWithIncorrectParamFilter_thenFetchAll() {
        PageRequest pageRequest = PageRequest.of(PAGE, SIZE);

        MultiValueMap<String, String> filterParams = new LinkedMultiValueMap<>();
        filterParams.add(INCORRECT_FILTER_PARAM, INCORRECT_FILTER_PARAM_VALUE);

        List<TagEntity> expected = Arrays.asList(TAG_1, TAG_2, TAG_3, TAG_4);
        Mockito.when(tagDao.findWithFilter(filterParams, pageRequest)).thenReturn(expected);
        List<Tag> actual = tagService.doFilter(filterParams, PAGE, SIZE);
        assertEquals(expected.stream().map(
                (element) -> tagDomainConvertor.convertEntityToDTO(element)
        ).collect(Collectors.toList()), actual);
    }

    @Test
    void insert_thenOk() {
        Mockito.when(tagDao.findByName(NEW_INSERT_TAG.getName())).thenReturn(Optional.empty());
        Mockito.when(tagDao.insert(BEFORE_INSERT_TAG)).thenReturn(Optional.of(TAG_5));
        Mockito.when(tagDomainConvertor.convertDtoToEntity(NEW_INSERT_TAG)).thenReturn(BEFORE_INSERT_TAG);
        Tag actual = tagService.insert(NEW_INSERT_TAG);
        assertEquals(tagDomainConvertor.convertEntityToDTO(TAG_5), actual);
    }

    @Test
    void insertAlreadyExistedTag_thenThrow() {
        Mockito.when(tagDao.findByName(NEW_INSERT_TAG.getName())).thenReturn(Optional.of(TAG_5));
        assertThrows(DataExistException.class, () -> tagService.insert(NEW_INSERT_TAG));
    }

    @Test
    void getMostPopularTagWithHighestCostOfAllOrders_tagExisted_thenOk() {
        Mockito.when(tagDao.findMostPopularTagWithOrdersWithHighestCost()).thenReturn(Optional.of(TAG_4));
        Tag actual = tagService.findMostPopularTagWithOrdersWithHighestCost();
        assertEquals(tagDomainConvertor.convertEntityToDTO(TAG_4), actual);
    }

    @Test
    void getMostPopularTagWithHighestCostOfAllOrders_tagNotExisted_thenThrow() {
        Mockito.when(tagDao.findMostPopularTagWithOrdersWithHighestCost()).thenReturn(Optional.empty());
        assertThrows(DataNotFoundException.class, () -> tagService.findMostPopularTagWithOrdersWithHighestCost());
    }
}
