package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.hateoas.RepresentationModel;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO for gift certificates
 * @author yegorchevardin
 * @version 0.0.1
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GiftCertificate extends RepresentationModel<GiftCertificate> {
    private Long id;
    @NotNull(message = "Gift Certificate name must be not null")
    @Length(min = 2, max = 45, message = "Gift certificate name should range between 2 and 45 characters")
    private String name;
    @Length(min = 5, max = 500, message = "Gift certificate description should range between 5 and 500 characters")
    private String description;
    @NotNull(message = "Gift certificate price must be not null")
    @Positive(message = "Gift certificate price must be positive")
    private Float price;
    @NotNull(message = "Gift certificate duration must be not null")
    @Positive(message = "Gift certificate duration must be positive")
    @Min(value = 1, message = "Gift certificate must be minimum one day")
    private Integer duration;
    private String createDate;
    private String lastUpdateDate;
    private List<@Valid Tag> tags = new ArrayList<>();
}
