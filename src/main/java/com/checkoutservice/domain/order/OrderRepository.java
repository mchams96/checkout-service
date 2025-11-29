package com.checkoutservice.domain.order;

public interface OrderRepository {
    void save(Order order);
    Order get(String id);
    void delete(String orderId);
}
