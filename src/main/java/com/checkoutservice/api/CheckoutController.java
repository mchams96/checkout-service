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
    public ResponseEntity<Void> deleteCart(@PathVariable("id") String cartId) {
        service.deleteCart(cartId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/carts/{id}/items")
    public ResponseEntity<GetCartResult> addItem(@PathVariable String cartId, @RequestBody AddCartItemJob addCartItemJob) {
        GetCartResult updatedCart = service.addItemToCart(cartId, addCartItemJob);
        return ResponseEntity.ok(updatedCart);
    }

    @GetMapping("/carts/{id}")
    public ResponseEntity<GetCartResult> getCart(@PathVariable String cartId) {
        return ResponseEntity.ok(service.getCart(cartId));
    }

    // --- Checkout ---
    @PostMapping("/checkouts")
    public ResponseEntity<CheckoutResult> startCheckout(@RequestBody CheckoutJob checkoutJob) {
        CheckoutResult result = service.start(checkoutJob);
        return ResponseEntity.created(URI.create("/orders/" + result.orderId())).body(result);
    }

    // --- Orders ---
    @GetMapping("/orders/{id}")
    public ResponseEntity<GetOrderResult> getOrder(@PathVariable String orderId) {
        return ResponseEntity.ok(service.getOrder(orderId));
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable("id") String orderId) {
        service.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }
}
