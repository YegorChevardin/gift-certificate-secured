package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.handlers.linkbuilders.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.controllers.GiftCertificateController;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.dtos.GiftCertificate;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.dtos.Tag;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.handlers.linkbuilders.LinkBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
@RequiredArgsConstructor
public class GiftCertificateLinkBuilder implements LinkBuilder<GiftCertificate> {
    private final LinkBuilder<Tag> tagLinkBuilder;

    @Override
    public void buildLinks(GiftCertificate dto) {
        dto.add(linkTo(methodOn(GiftCertificateController.class).findById(dto.getId())).withSelfRel());
        dto.getTags().forEach(tagLinkBuilder::buildLinks);
    }
}
