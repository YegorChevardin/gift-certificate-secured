package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.handlers.validators.constraints;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.handlers.validators.UserIdValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation for validating passed user id
 * @author yegorchevardin
 * @version 0.0.1
 */
@Documented
@Constraint(validatedBy = UserIdValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistingUser {
    String message() default "User with this id should be existing!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
