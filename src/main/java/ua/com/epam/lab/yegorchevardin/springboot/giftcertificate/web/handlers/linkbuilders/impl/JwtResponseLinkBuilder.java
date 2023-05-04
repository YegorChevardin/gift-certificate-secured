package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.handlers.linkbuilders.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.controllers.AuthController;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.dtos.User;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.dtos.security.JwtResponse;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.handlers.linkbuilders.LinkBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
@RequiredArgsConstructor
public class JwtResponseLinkBuilder implements LinkBuilder<JwtResponse> {
    private final LinkBuilder<User> userLinkBuilder;

    @Override
    public void buildLinks(JwtResponse dto) {
        userLinkBuilder.buildLinks(dto.getUser());
        dto.add(linkTo(methodOn(AuthController.class)).withSelfRel());
    }
}
