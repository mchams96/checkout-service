package com.checkoutservice.persistence;

import com.checkoutservice.app.exceptions.OrderNotFoundException;
import com.checkoutservice.domain.order.*;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.HashMap;

@Repository
public class InMemoryOrderRepository implements OrderRepository {
    private final Map<String, Order> orderByOrderId = new HashMap<>();

    public void save(Order order){
        orderByOrderId.put(order.getId(), order);
    }

    public Order get(String orderId) throws OrderNotFoundException {
        Order foundOrder = orderByOrderId.get(orderId);
        if (foundOrder == null){
            throw new OrderNotFoundException("Order not found: " + orderId);
        }

        return foundOrder;
    }

    @Override
    public void delete(String orderId) {
        orderByOrderId.remove(orderId);
    }
}
