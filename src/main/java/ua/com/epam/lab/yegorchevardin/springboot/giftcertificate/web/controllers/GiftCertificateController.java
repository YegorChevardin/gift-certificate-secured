package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.services.GiftCertificateService;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.dtos.GiftCertificate;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.handlers.linkbuilders.LinkBuilder;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

/**
 * Controller for handling requests for gift-certificates
 * @author yegorchevardin
 * @version 0.0.1
 */
@RestController
@RequestMapping("/api/v1/gift-certificates")
@RequiredArgsConstructor
public class GiftCertificateController {
    private final GiftCertificateService giftCertificateService;
    private final LinkBuilder<GiftCertificate> giftCertificateLinkBuilder;

    /**
     * Method for getting all gift-certificates
     * @return ResponseEntity with all gift-certificates
     */
    @GetMapping
    public ResponseEntity<CollectionModel<GiftCertificate>> showAll(
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "5", required = false) int size
    ) {
        List<GiftCertificate> giftCertificates = giftCertificateService
                .findAll(page, size).stream().peek(
                        giftCertificateLinkBuilder::buildLinks
                ).toList();
        Link link = linkTo(methodOn(GiftCertificateController.class).showAll(page, size)).withSelfRel();
        return ResponseEntity.ok(CollectionModel.of(giftCertificates, link));
    }

    /**
     * Method for getting all gift-certificates by some filter
     * @return ResponseEntity with all gift-certificates
     */
    @GetMapping("/filter")
    public ResponseEntity<CollectionModel<GiftCertificate>> findAllGiftCertificatesWithFilter(
            @RequestParam MultiValueMap<String, String> params,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "5", required = false) int size
    ) {
        List<GiftCertificate> giftCertificates = giftCertificateService.doFilter(
                params,
                page, size
        ).stream().peek(giftCertificateLinkBuilder::buildLinks).toList();
        Link link = linkTo(methodOn(GiftCertificateController.class)
                        .findAllGiftCertificatesWithFilter(params, page, size)).withSelfRel();
        return ResponseEntity.ok(CollectionModel.of(giftCertificates, link));
    }

    /**
     * Method for getting gift certificate by specific id
     * @return ResponseEntity with all gift-certificates
     */
    @GetMapping("/{id}")
    public ResponseEntity<GiftCertificate> findById(
            @PathVariable(name = "id") Long id) {
        GiftCertificate giftCertificate = giftCertificateService.findById(id);
        giftCertificateLinkBuilder.buildLinks(giftCertificate);
        return ResponseEntity.ok(giftCertificate);
    }

    /**
     * Method for handling requests
     * for creating gift certificate
     * @param giftCertificate Gift certificate to insert
     * @return ResponseEntity response entity with gift certificate
     * */
    @PostMapping
    public ResponseEntity<GiftCertificate> createGiftCertificate(
            @RequestBody @Valid GiftCertificate giftCertificate
    ) {
        GiftCertificate insertedDto = giftCertificateService.insert(giftCertificate);
        giftCertificateLinkBuilder.buildLinks(insertedDto);
        return ResponseEntity.ok(insertedDto);
    }

    /**
     * Method for handling requests
     * for deleting gift certificate
     * @param id id of gift certificate
     * @return ResponseEntity with ok status
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGiftCertificate(
            @PathVariable(name = "id")
            @Min(value = 0, message = "Min value for id is 0") Long id) {
        giftCertificateService.removeById(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Method for handling requests
     * for updating the gift certificate object in the database
     * @param giftCertificate Gift certificate to update
     * @return ResponseEntity with gift certificate
     */
    @PutMapping()
    public ResponseEntity<GiftCertificate> updateGiftCertificate(
            @RequestBody @Valid GiftCertificate giftCertificate
    ) {
        GiftCertificate updatedDto = giftCertificateService.update(giftCertificate);
        giftCertificateLinkBuilder.buildLinks(updatedDto);
        return ResponseEntity.ok(updatedDto);
    }
}
