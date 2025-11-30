package com.checkoutservice.api;

import com.checkoutservice.app.CheckoutService;
import com.checkoutservice.app.beans.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping
public class CheckoutController {

    private final CheckoutService service;

    public CheckoutController(CheckoutService service) {
        this.service = service;
    }

    // --- Carts ---
    @PostMapping("/carts")
    public ResponseEntity<CreateCartResult> createCart(@RequestBody CreateCartJob createCartJob) {
        var createCartResult = service.createCart(createCartJob);
        return ResponseEntity.ok(createCartResult);
    }

    @DeleteMapping("/carts/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable("id") String id) {
        service.deleteCart(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/carts/{id}/items")
    public ResponseEntity<GetCartResult> addItem(@PathVariable("id") String id, @RequestBody AddCartItemJob addCartItemJob) {
        GetCartResult updatedCart = service.addItemToCart(id, addCartItemJob);
        return ResponseEntity.ok(updatedCart);
    }

    @GetMapping("/carts/{id}")
    public ResponseEntity<GetCartResult> getCart(@PathVariable("id") String id) {
        return ResponseEntity.ok(service.getCart(id));
    }

    // --- Checkout ---
    @PostMapping("/checkouts")
    public ResponseEntity<CheckoutResult> startCheckout(@RequestBody CheckoutJob checkoutJob) {
        CheckoutResult result = service.start(checkoutJob);
        return ResponseEntity.created(URI.create("/orders/" + result.orderId())).body(result);
    }

    // --- Orders ---
    @GetMapping("/orders/{id}")
    public ResponseEntity<GetOrderResult> getOrder(@PathVariable("id") String id) {
        return ResponseEntity.ok(service.getOrder(id));
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable("id") String id) {
        service.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
