package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.hateoas.RepresentationModel;

/**
 * DTO for roles
 * @author yegorchevardin
 * @version 0.0.1
 */
@Getter
@Setter
@NoArgsConstructor
public class Role extends RepresentationModel<Role> {
    @NotNull
    @Length(min = 2, max = 50, message = "Role value should range between 2 and 50 symbols")
    private String name;
}
