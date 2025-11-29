package com.checkoutservice.persistence;

import com.checkoutservice.domain.order.*;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.HashMap;
import java.util.NoSuchElementException;

@Repository
public class InMemoryOrderRepository implements OrderRepository {
    private final Map<String, Order> orderByOrderId = new HashMap<>();

    public void save(Order order){
        orderByOrderId.put(order.getId(), order);
    }

    public Order get(String orderId) throws NoSuchElementException {
        Order foundOrder = orderByOrderId.get(orderId);
        if (foundOrder == null){
            throw new NoSuchElementException("Order not found: " + orderId);
        }

        return foundOrder;
    }
}
