package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.hateoas.RepresentationModel;

/**
 * DTO for users
 * @author yegorchevardin
 * @version 0.0.1
 */
@Getter
@Setter
@NoArgsConstructor
public class User extends RepresentationModel<User> {
    private Long id;
    @Length(min = 2, max = 50, message = "Username should range between 2 and 50 characters")
    @NotNull
    private String username;
}
