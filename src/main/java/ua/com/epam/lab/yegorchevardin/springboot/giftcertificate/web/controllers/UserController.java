package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.services.UserService;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.dtos.User;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.handlers.linkbuilders.LinkBuilder;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

/**
 * Controller for handling requests to user dtos
 * @author yegorchevardin
 * @version 0.0.1
 */
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "#{'${frontEnd.url}'}")
public class UserController {
    private final UserService userService;
    private final LinkBuilder<User> userLinkBuilder;

    /**
     * Method for getting all users from the database
     * @param page length of page
     * @param size amount of elements on the page
     * @return ResponseEntity with user
     */
    @GetMapping
    public ResponseEntity<CollectionModel<User>> findAll(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "5", required = false) int size
    ) {
        List<User> users = userService.findAll(page, size);
        users.forEach(userLinkBuilder::buildLinks);
        Link link = linkTo(methodOn(UserController.class).findAll(page, size)).withSelfRel();
        return ResponseEntity.ok(CollectionModel.of(users, link));
    }

    /**
     * Method for getting size of all elements
     */
    @GetMapping("/size")
    public ResponseEntity<Integer> countAll() {
        Integer result = userService.countAll();
        return ResponseEntity.ok(result);
    }

    /**
     * Method for handling request
     * for getting user by specific id
     * @param id users id
     * @return ResponseEntity with user
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> findById(
            @PathVariable(name = "id") Long id
    ) {
        User user = userService.findById(id);
        userLinkBuilder.buildLinks(user);
        return ResponseEntity.ok(user);
    }

    /**
     * Method for handling requests for creating user
     * @param user valid user object to insert
     * @return ResponseEntity with created user
     */
    @PreAuthorize("hasRole('user') and hasRole('admin')")
    @PostMapping
    public ResponseEntity<User> createUser(
            @RequestBody @Valid User user
    ) {
        User dto = userService.insert(user);
        userLinkBuilder.buildLinks(dto);
        return ResponseEntity.ok(dto);
    }

    /**
     * Method for handling requests for updating user object
     * @param user valid user object with updated values
     * @return ResponseEntity with updated user
     */
    @PutMapping
    @PreAuthorize("hasRole('user') and hasRole('admin')")
    public ResponseEntity<User> updateUser(
            @RequestBody @Valid User user
    ) {
        User dto = userService.update(user);
        userLinkBuilder.buildLinks(dto);
        return ResponseEntity.ok(dto);
    }

    /**
     * Method for handling requests for deleting user
     * @param id id of user to delete
     */
    @PreAuthorize("hasRole('user') and hasRole('admin')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long id
    ) {
        userService.removeById(id);
        return ResponseEntity.ok().build();
    }
}
