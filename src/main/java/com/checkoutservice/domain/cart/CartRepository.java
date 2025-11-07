package com.checkoutservice.domain.cart;

public interface CartRepository {
    String create(String currency);
    Cart get(String id);
    Cart addItem(String cartId, CartItem item);
}
