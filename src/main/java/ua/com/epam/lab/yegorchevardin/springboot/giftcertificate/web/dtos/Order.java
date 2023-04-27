package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.handlers.validators.constraints.ExistingUser;

import java.util.List;

/**
 * DTO for orders
 * @author yegorchevardin
 * @version 0.0.1
 */
@Getter
@Setter
@NoArgsConstructor
public class Order extends RepresentationModel<Order> {
    private Long id;
    private Float cost;
    private String purchasedAt;
    @Valid
    @ExistingUser
    @NotNull(message = "User id must be included in the order!")
    private User user;
    @NotNull(message = "You cannot make order with an empty gift-certificates!")
    private List<GiftCertificate> giftCertificates;
}
