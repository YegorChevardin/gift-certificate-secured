package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.services.RoleService;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.dtos.Role;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.handlers.linkbuilders.LinkBuilder;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

/**
 * Controller class for roles
 * @author yegorchevardin
 * @version 0.0.1
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/roles")
public class RoleController {
    private final RoleService roleService;
    private final LinkBuilder<Role> roleLinkBuilder;

    /**
     * Method for getting all roles from the database
     */
    @GetMapping
    public ResponseEntity<CollectionModel<Role>> showAll(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "5", required = false) int size
    ) {
        List<Role> roles = roleService.findAll(page, size).stream().peek(
                roleLinkBuilder::buildLinks
        ).toList();
        Link link = linkTo(methodOn(RoleController.class)).withSelfRel();
        return ResponseEntity.ok(CollectionModel.of(roles, link));
    }

    /**
     * Method for getting role by id
     */
    @GetMapping("/{id}")
    public ResponseEntity<Role> showById(
            @PathVariable(name = "id") Long id
    ) {
        Role role = roleService.findById(id);
        roleLinkBuilder.buildLinks(role);
        return ResponseEntity.ok(role);
    }

    /**
     * Method for creating roles
     */
    @PostMapping
    public ResponseEntity<Role> createRole(
            @RequestBody @Valid Role role
    ) {
        Role dto = roleService.insert(role);
        roleLinkBuilder.buildLinks(dto);
        return ResponseEntity.ok(dto);
    }

    /**
     * Method for deleting role by id
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(
            @PathVariable(name = "id") Long id
    ) {
        roleService.removeById(id);
        return ResponseEntity.ok().build();
    }
}
