package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.handlers.linkbuilders.impl;

import org.springframework.stereotype.Component;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.controllers.TagController;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.dtos.Tag;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.handlers.linkbuilders.LinkBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TagLinkBuilder implements LinkBuilder<Tag> {
    @Override
    public void buildLinks(Tag dto) {
        dto.add(linkTo(methodOn(TagController.class).findById(dto.getId())).withSelfRel());
    }
}
