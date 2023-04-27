package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.handlers.linkbuilders.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.controllers.OrderController;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.dtos.GiftCertificate;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.dtos.Order;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.dtos.User;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.handlers.linkbuilders.LinkBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
@RequiredArgsConstructor
public class OrderLinkBuilder implements LinkBuilder<Order> {
    private final LinkBuilder<GiftCertificate> giftCertificateLinkBuilder;
    private final LinkBuilder<User> userLinkBuilder;

    @Override
    public void buildLinks(Order dto) {
        userLinkBuilder.buildLinks(dto.getUser());
        dto.getGiftCertificates().forEach(
                giftCertificateLinkBuilder::buildLinks
        );
        dto.add(linkTo(methodOn(OrderController.class).findById(dto.getId())).withSelfRel());
    }
}
