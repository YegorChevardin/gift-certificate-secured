package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.configurations;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.SmartValidator;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.constants.DefaultRoles;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.dao.RoleDAO;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.dao.UserDAO;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.entities.RoleEntity;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.entities.UserEntity;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.constants.ExceptionMessages;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.exceptions.DataNotFoundException;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.exceptions.DataNotValidException;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.utils.convertors.DomainObjectsConvertor;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.dtos.User;
import java.util.List;

/**
 * Class for loading data into database before application starts
 * @author yegorchevardin
 * @version 0.0.1
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    @Value("${accounts.admin.username}")
    private String adminUsername;
    @Value("${accounts.admin.password}")
    private String adminPassword;
    private boolean alreadySetup = false;
    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;
    private final RoleDAO roleDAO;
    private final SmartValidator smartValidator;
    private final DomainObjectsConvertor<UserEntity, User>
            userDomainObjectsConvertor;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup) return;
        UserEntity entity = createAdmin();
        if(userDAO.findByUsername(adminUsername).isEmpty()) {
            if (adminPassword == null || adminPassword.length() < 2
                    || adminPassword.length() > 50) {
                throw new DataNotValidException(
                        "Password cannot be null and must be greater " +
                                "than 2 and less than 50 characters"
                );
            }
            User userToValidate = userDomainObjectsConvertor.convertEntityToDTO(entity);
            userToValidate.setPassword(adminPassword);
            validateUserModel(userToValidate);
            userDAO.insert(entity);
            alreadySetup = true;
            log.info("Admin user created with username: "
                    + adminUsername + " and password: " + adminPassword);
        } else {
            log.info("Admin for this application with such username and password was created earlier: "
                    + adminUsername + "=>" + adminPassword);
        }
    }

    @Transactional
    public UserEntity createAdmin() {
        UserEntity entity = new UserEntity();
        entity.setUsername(adminUsername);
        entity.setPassword(passwordEncoder.encode(adminPassword));
        entity.setRoles(getDefaultRoles());
        return entity;
    }

    private void validateUserModel(@Valid User model) {
        DataBinder dataBinder = new DataBinder(model);
        BindingResult bindingResult = new BeanPropertyBindingResult(
                dataBinder.getTarget(),
                dataBinder.getObjectName());
        smartValidator.validate(model, bindingResult);
        if (bindingResult.hasErrors()) {
            StringBuilder messageBuilder = new StringBuilder(
                    "Passed data for admin is not valid, try again with correct data: "
            ).append(System.lineSeparator());
            bindingResult.getFieldErrors().forEach(
                    element -> messageBuilder
                            .append(element.getDefaultMessage())
                            .append(": ").append(element.getRejectedValue())
                            .append(System.lineSeparator())
            );
            throw new DataNotValidException(messageBuilder.toString());
        }
    }

    private List<RoleEntity> getDefaultRoles() {
        return DefaultRoles.getDefaultRolesInStrings().stream().map(
                element -> roleDAO
                        .findByName(element)
                        .orElseThrow(
                                () -> new DataNotFoundException(
                                        String.format(
                                                ExceptionMessages
                                                        .ROLE_BY_NAME_NOT_FOUND.getValue(),
                                                element
                                        )
                                )
                        )
        ).toList();
    }

}
