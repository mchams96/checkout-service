package com.checkoutservice.domain.order;

public interface OrderState {
    OrderState paymentSucceeded();
    OrderState paymentFailed();
    String name();
}
