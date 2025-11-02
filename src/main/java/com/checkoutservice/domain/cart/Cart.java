package com.checkoutservice.domain.cart;

import java.util.List;

public class Cart {
    private final List<Item> items;

    public Cart(List<Item> items) {
        this.items = items;
    }

    public List<Item> getItems() {
        return items;
    }
}

