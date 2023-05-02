package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.handlers.linkbuilders.impl;

import org.springframework.stereotype.Component;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.controllers.RoleController;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.dtos.Role;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.handlers.linkbuilders.LinkBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class RoleLinkBuilder implements LinkBuilder<Role> {
    @Override
    public void buildLinks(Role dto) {
        dto.add(linkTo(methodOn(RoleController.class)
                .showById(dto.getId())).withSelfRel());
    }
}
