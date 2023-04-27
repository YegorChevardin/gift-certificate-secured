package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.hateoas.RepresentationModel;

/**
 * DTO for tags
 * @author yegorchevardin
 * @version 0.0.1
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tag extends RepresentationModel<Tag> {
    private Long id;
    @Length(min = 2, max = 45, message = "Tag should range between 2 and 45 characters")
    @NotNull(message = "Tag value must be not null")
    private String name;
}
