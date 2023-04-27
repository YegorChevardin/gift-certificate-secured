package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.handlers.linkbuilders.impl;

import org.springframework.stereotype.Component;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.controllers.UserController;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.dtos.User;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.handlers.linkbuilders.LinkBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class UserLinkBuilder implements LinkBuilder<User> {
    @Override
    public void buildLinks(User dto) {
        dto.add(linkTo(methodOn(UserController.class).findById(dto.getId())).withSelfRel());
    }
}
