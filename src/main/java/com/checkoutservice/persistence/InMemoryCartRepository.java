package com.checkoutservice.persistence;

import com.checkoutservice.app.exceptions.CartNotFoundException;
import com.checkoutservice.domain.cart.*;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.HashMap;
import java.util.UUID;

@Repository
public class InMemoryCartRepository implements CartRepository {
    private final Map<String, Cart> cartById = new HashMap<>();

    public String create(String currency){
        String id = UUID.randomUUID().toString();
        cartById.put(id, new Cart(id, currency));
        return id;
    }

    public Cart get(String cartId) throws CartNotFoundException {
        Cart foundCart = cartById.get(cartId);
        if (foundCart == null){
            throw new CartNotFoundException("Cart not found: " + cartId);
        }
        return foundCart;
    }

    public Cart addItem(String cartId, CartItem item){
        Cart cart = get(cartId);
        cart.addItem(item);
        return cart;
    }

    @Override
    public void delete(String cartId) {
        cartById.remove(cartId);
    }
}