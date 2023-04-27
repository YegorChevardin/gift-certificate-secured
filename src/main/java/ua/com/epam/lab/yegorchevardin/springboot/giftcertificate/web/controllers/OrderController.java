package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.services.OrderService;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.dtos.Order;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.handlers.linkbuilders.LinkBuilder;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

/**
 * Controller for handling requests in orders
 * @author yegorchevardin
 * @version 0.0.1
 */
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final LinkBuilder<Order> orderLinkBuilder;

    /**
     * Method for getting all orders
     */
    @GetMapping
    public ResponseEntity<CollectionModel<Order>> findAll(
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "5", required = false) int size
    ) {
        List<Order> orders = orderService.findAll(page, size).stream().peek(
                orderLinkBuilder::buildLinks
        ).toList();
        Link link = linkTo(methodOn(OrderController.class).findAll(page, size)).withSelfRel();
        return ResponseEntity.ok(CollectionModel.of(orders, link));
    }

    /**
     * Method for finding orders by some userId
     */
    @GetMapping("/users/{id}")
    public ResponseEntity<CollectionModel<Order>> findByUserId(
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "5", required = false) int size,
            @PathVariable(value = "id") Long id
    ) {
        List<Order> orders = orderService.findByUserId(id, page, size).stream().peek(
                orderLinkBuilder::buildLinks
        ).toList();
        Link link = linkTo(methodOn(OrderController.class).findAll(page, size)).withSelfRel();
        return ResponseEntity.ok(CollectionModel.of(orders, link));
    }

    /**
     * Method for getting order by id
     */
    @GetMapping("/{id}")
    public ResponseEntity<Order> findById(@PathVariable(name = "id") Long id) {
        Order order = orderService.findById(id);
        orderLinkBuilder.buildLinks(order);
        return ResponseEntity.ok(order);
    }

    /**
     * Method for creating new order
     */
    @PostMapping
    public ResponseEntity<Order> insertOrder(
            @RequestBody @Valid Order order
    ) {
        Order dto = orderService.insert(order);
        orderLinkBuilder.buildLinks(dto);
        return ResponseEntity.ok(dto);
    }

    /**
     * Method for deleting order
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(
            @PathVariable(name = "id") Long id
    ) {
        orderService.removeById(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Method for updating order
     */
    @PutMapping
    public ResponseEntity<Order> updateOrder(
            @RequestParam(name = "isPurchased", required = false, defaultValue = "false")
            boolean isPurchased,
            @RequestBody @Valid Order order
    ) {
        Order dto = orderService.update(order, isPurchased);
        orderLinkBuilder.buildLinks(dto);
        return ResponseEntity.ok(dto);
    }
}
