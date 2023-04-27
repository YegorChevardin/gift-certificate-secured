package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Controller for homepage that shows server work status
 * @author yegorchevardin
 * @version 0.0.1
 */
@RestController
@RequestMapping({"/", "/api/v1/"})
public class HomeController {
    @GetMapping
    public ResponseEntity<Map<String, String>> showHome() {
        return ResponseEntity.ok().build();
    }
}
