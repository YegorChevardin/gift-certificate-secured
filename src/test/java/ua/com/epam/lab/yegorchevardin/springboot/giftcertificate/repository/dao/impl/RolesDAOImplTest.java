package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.dao.impl;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.dao.RoleDAO;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.entities.RoleEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = DaoConfigTest.class)
@Transactional
public class RolesDAOImplTest {
    private static final RoleEntity ROLE_USER = new RoleEntity(1L, "user");
    private static final RoleEntity ROLE_ADMIN = new RoleEntity(0L, "admin");
    private final Pageable pageRequest = PageRequest.of(0, 5);

    private final RoleDAO roleDAO;

    @Autowired
    public RolesDAOImplTest(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }

    @Test
    @Sql({"/db/clear_all.sql", "/db/seed_roles.sql"})
    public void getById_thenOk() {
        Optional<RoleEntity> expected = Optional.of(ROLE_USER);
        Optional<RoleEntity> actual = roleDAO.findById(1);
        assertEquals(expected, actual);
    }

    @Test
    @Sql({"/db/clear_all.sql", "/db/seed_roles.sql"})
    public void getById_thenReturnNull() {
        Optional<RoleEntity> actual = roleDAO.findById(100);
        assertTrue(actual.isEmpty());
    }

    @Test
    @Sql({"/db/clear_all.sql", "/db/seed_roles.sql", "/db/seed_users.sql"})
    void getAll_thenOk() {
        List<RoleEntity> expected = Arrays.asList(ROLE_ADMIN, ROLE_USER);
        List<RoleEntity> actual = roleDAO.findAll(pageRequest);
        assertEquals(expected, actual);
    }
}
