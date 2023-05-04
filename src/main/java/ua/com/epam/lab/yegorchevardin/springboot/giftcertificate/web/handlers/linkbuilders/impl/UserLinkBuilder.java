package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.handlers.linkbuilders.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.controllers.UserController;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.dtos.Role;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.dtos.User;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.handlers.linkbuilders.LinkBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
@RequiredArgsConstructor
public class UserLinkBuilder implements LinkBuilder<User> {
    private final LinkBuilder<Role> roleLinkBuilder;

    @Override
    public void buildLinks(User dto) {
        dto.getRoles().forEach(roleLinkBuilder::buildLinks);
        dto.add(linkTo(methodOn(UserController.class).findById(dto.getId())).withSelfRel());
    }
}
