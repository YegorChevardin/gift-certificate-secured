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
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.dao.impl.UserDAOImpl;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.entities.UserEntity;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.exceptions.DataNotFoundException;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.utils.impl.UserDomainConvertor;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.dtos.User;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    private static final long NOT_EXISTED_ID = 999L;
    private static final int PAGE = 0;
    private static final int SIZE = 5;

    private final UserEntity USER_1 = new UserEntity(1L, "name1");
    private final UserEntity USER_2 = new UserEntity(2L, "name2");
    private final UserEntity USER_3 = new UserEntity(3L, "name3");

    @Mock
    UserDAOImpl userDao;

    @Mock
    UserDomainConvertor userDomainConvertor;

    @InjectMocks
    UserServiceImpl userService;

    @Test
    void getById_thenOk() {
        Mockito.when(userDao.findById(USER_1.getId())).thenReturn(Optional.of(USER_1));
        User actual = userService.findById(USER_1.getId());
        Assertions.assertEquals(userDomainConvertor.convertEntityToDTO(USER_1), actual);
    }

    @Test
    void getNotExistedById_thenThrowEx() {
        Mockito.when(userDao.findById(NOT_EXISTED_ID)).thenReturn(Optional.empty());
        assertThrows(DataNotFoundException.class, () -> userService.findById(NOT_EXISTED_ID));
    }

    @Test
    void getAll_thenOk() {
        List<UserEntity> expected = Arrays.asList(USER_1, USER_2, USER_3);
        Pageable pageRequest = PageRequest.of(PAGE, SIZE);
        Mockito.when(userDao.findAll(pageRequest)).thenReturn(expected);
        List<User> actual = userService.findAll(PAGE, SIZE);
        Assertions.assertEquals(expected.stream().map(
                (element) -> userDomainConvertor.convertEntityToDTO(element)
        ).toList(), actual);
    }
}
