package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.dtos.security;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.dtos.User;

/**
 * Class that represents Jwt response object
 * @author yegorchevardin
 * @version 0.0.1
 */
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class JwtResponse extends RepresentationModel<JwtResponse> {
    private User user;
    private String jwtToken;
}
