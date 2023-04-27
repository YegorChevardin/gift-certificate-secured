package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.services.TagService;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.dtos.Tag;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.handlers.linkbuilders.LinkBuilder;
import java.util.List;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

/**
 * Controller for handling request for tag dtos
 * @author yegorchevardin
 * @version 0.0.1
 */
@RestController
@RequestMapping("/api/v1/tags")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;
    private final LinkBuilder<Tag> tagLinkBuilder;

    /**
     * Method for handling requests for getting all tags
     * @param page specific page of items
     * @param size size of elements on page
     * @return Response Entity with tags dtos
     */
    @GetMapping
    public ResponseEntity<CollectionModel<Tag>> showAllTags(
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "5", required = false) int size
    ) {
        List<Tag> tags = tagService.findAll(page, size).stream().peek(
                tagLinkBuilder::buildLinks
        ).toList();
        Link link = linkTo(methodOn(TagController.class).showAllTags(page, size)).withSelfRel();
        return ResponseEntity.ok(CollectionModel.of(tags, link));
    }

    /**
     * Method for handling requests for finding tag by id
     * @param tagId id of tag to find
     * @return Response Entity with tag dto
     */
    @GetMapping("/{tagId}")
    public ResponseEntity<Tag> findById(@PathVariable(name = "tagId") Long tagId) {
        Tag tag = tagService.findById(tagId);
        tagLinkBuilder.buildLinks(tag);
        return ResponseEntity.ok(tag);
    }

    /**
     * Method for handling requests for creating tag
     * @param tag tag to insert
     * @return Response Entity with tag dto
     */
    @PostMapping
    public ResponseEntity<Tag> createTag(@RequestBody @Valid Tag tag) {
        Tag insertedTag = tagService.insert(tag);
        tagLinkBuilder.buildLinks(insertedTag);
        return ResponseEntity.ok(insertedTag);
    }

    /**
     * Method for handling removing tag from the database
     * @param id tag id
     * @return Response Entity with okay status
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        tagService.removeById(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Method for handling request to find the most profitable tag
     * @return Response Entity with tag dto
     */
    @GetMapping("/most-profitable")
    public ResponseEntity<Tag> findMostProfitableTag() {
        Tag dto = tagService.findMostPopularTagWithOrdersWithHighestCost();
        tagLinkBuilder.buildLinks(dto);
        return ResponseEntity.ok(dto);
    }

    /**
     * Method for handling request to find tags with some filters
     * @param page size of page
     * @param size amount of elements to get
     * @return Response Entity with tags
     */
    @GetMapping("/filter")
    public ResponseEntity<CollectionModel<Tag>> tagByFilter(
            @RequestParam MultiValueMap<String, String> params,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "5", required = false) int size) {
        List<Tag> tags = tagService.doFilter(params, page, size)
                .stream().peek(
                        tagLinkBuilder::buildLinks
                ).toList();
        Link link = linkTo(methodOn(TagController.class)
                .tagByFilter(params, page, size)).withSelfRel();
        return ResponseEntity.ok(CollectionModel.of(tags, link));
    }
}
