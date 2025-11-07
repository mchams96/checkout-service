package com.checkoutservice.domain.cart;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private final String id;
    private final String currency;
    private final List<CartItem> cartItems = new ArrayList<>();

    public Cart(String id, String currency) {
        this.id = id;
        this.currency = currency == null ? "USD" : currency;
    }

    // Methods:
    public void addItem(CartItem item) { cartItems.add(item); }

    // Getters:
    public String getId() {
        return id;
    }

    public String getCurrency() {
        return currency;
    }

    public List<CartItem> getItems() {
        return cartItems;
    }
}

