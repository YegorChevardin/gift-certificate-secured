package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.hateoas.RepresentationModel;

import java.util.ArrayList;
import java.util.List;

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
    private String password;
    private List<Role> roles = new ArrayList<>();
}
