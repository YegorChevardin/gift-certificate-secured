package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.dtos.security;

import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.hateoas.RepresentationModel;

/**
 * Class for getting credentials for logging in user
 * @author yegorchevardin
 * @version 0.0.1
 */
@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class JwtRequest extends RepresentationModel<JwtRequest> {
    @Length(min = 2, max = 50, message = "Username should range between 2 and 50 characters")
    @NotNull
    private String username;
    @NotNull
    @Length(min = 8, max = 50, message = "Password should range between 8 and 50 symbols")
    private String password;
}
