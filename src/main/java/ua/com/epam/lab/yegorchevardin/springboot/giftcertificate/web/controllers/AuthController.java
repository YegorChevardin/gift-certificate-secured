package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.services.AuthService;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.dtos.User;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.dtos.security.JwtRequest;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.dtos.security.JwtResponse;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.handlers.linkbuilders.LinkBuilder;

/**
 * Controller for handling auth actions
 * @author yegorchevardin
 * @version 0.0.1
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final LinkBuilder<User> userLinkBuilder;

    /**
     * Method for getting current logged user
     */
    @GetMapping
    @PreAuthorize("hasRole('user')")
    public ResponseEntity<User> showCurrentUser() {
        User user = authService.findCurrentAccount();
        userLinkBuilder.buildLinks(user);
        return ResponseEntity.ok(user);
    }

    /**
     * Method for registering new user in the System
     * @param user user to register
     * @return registered user object
     */
    @PostMapping("/register")
    public ResponseEntity<User> registerNewAccount(
            @RequestBody @Valid User user
    ) {
        User registeredUser = authService.register(user);
        userLinkBuilder.buildLinks(registeredUser);
        return ResponseEntity.ok(registeredUser);
    }

    /**
     * Method for logging in into system
     * @param jwtRequest correct credentials to log in
     * @return Jwt response with generated token and user account
     */
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(
            @RequestBody @Valid JwtRequest jwtRequest
    ) {
        JwtResponse jwtResponse = authService.login(jwtRequest);
        return ResponseEntity.ok(jwtResponse);
    }

    /**
     * Method for updating current user account
     * @param user user with updated username
     * @return Updated user
     */
    @PutMapping
    @PreAuthorize("hasRole('user')")
    public ResponseEntity<User> updateCurrentAccount(
            @RequestBody @Valid User user
    ) {
         User dto = authService.updateAccount(user);
         userLinkBuilder.buildLinks(user);
         return ResponseEntity.ok(dto);
    }

    /**
     * Method for deleting current user account
     * Admins cannot delete their account if they will
     * not be downed to user
     */
    @PreAuthorize("hasRole('user') and !hasRole('admin')")
    @DeleteMapping ResponseEntity<Void> deleteCurrentAccount(
            HttpServletRequest request, HttpServletResponse response
    ) {
        authService.deleteAccount(request, response);
        return ResponseEntity.ok().build();
    }
}
