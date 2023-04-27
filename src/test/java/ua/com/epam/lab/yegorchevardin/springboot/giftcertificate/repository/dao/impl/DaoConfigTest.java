package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.dao.impl;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@EnableAutoConfiguration
@ComponentScan("ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository")
@EntityScan("ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository")
public class DaoConfigTest {
}
