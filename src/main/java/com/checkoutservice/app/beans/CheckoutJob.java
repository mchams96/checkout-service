package com.checkoutservice.app.beans;

public record CheckoutJob(
        String cartId,
        String currency,
        String couponCode,
        String paymentProvider
) {}