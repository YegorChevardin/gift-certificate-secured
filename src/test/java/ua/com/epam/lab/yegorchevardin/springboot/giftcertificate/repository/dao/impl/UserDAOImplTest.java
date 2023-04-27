package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.dao.impl;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.dao.UserDAO;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.entities.UserEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = DaoConfigTest.class)
@Transactional
public class UserDAOImplTest {
    private static final long NOT_EXISTED_ID = 999L;
    private final UserEntity USER_1 = new UserEntity(1L, "name1");
    private final UserEntity USER_2 = new UserEntity(2L, "name2");
    private final Pageable pageRequest = PageRequest.of(0, 5);

    private final UserDAO userDao;

    @Autowired
    public UserDAOImplTest(UserDAO userDAO) {
        this.userDao = userDAO;
    }

    @Test
    @Sql({"/db/clear_all.sql", "/db/seed_users.sql"})
    void getById_thenOk() {
        Optional<UserEntity> expected = Optional.of(USER_1);
        Optional<UserEntity> actual = userDao.findById(USER_1.getId());
        assertEquals(expected, actual);
    }

    @Test
    @Sql({"/db/clear_all.sql", "/db/seed_users.sql"})
    void getByNotExistedId_thenReturnNull() {
        Optional<UserEntity> actual = userDao.findById(NOT_EXISTED_ID);
        assertFalse(actual.isPresent());
    }

    @Test
    @Sql({"/db/clear_all.sql", "/db/seed_users.sql"})
    void getAll_thenOk() {
        List<UserEntity> expected = Arrays.asList(USER_1, USER_2);
        List<UserEntity> actual = userDao.findAll(pageRequest);
        assertEquals(expected, actual);
    }
}
