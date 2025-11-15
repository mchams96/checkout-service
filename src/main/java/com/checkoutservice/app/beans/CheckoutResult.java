package com.checkoutservice.app.beans;

public record CheckoutResult(
        String orderId,
        String state,
        double total,
        String provider
) {}