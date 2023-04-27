package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.handlers.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.exceptions.DataNotFoundException;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.services.UserService;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.dtos.User;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.handlers.validators.constraints.ExistingUser;

/**
 * Class for validating user id
 * for checking if it exists in the database
 * @author yegorchevardin
 * @version 0.0.1
 */
@RequiredArgsConstructor
public class UserIdValidator implements ConstraintValidator<ExistingUser, User> {
    private final UserService userService;

    @Override
    public boolean isValid(User value, ConstraintValidatorContext context) {
        if (value == null || value.getUsername() == null) return false;
        try {
            userService.findByUsername(value.getUsername());
        } catch (DataNotFoundException e) {
            return false;
        }
        return true;
    }
}
