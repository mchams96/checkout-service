package com.checkoutservice.app.beans;

import java.util.List;

public record CheckoutJob(
        String cartId,
        String currency,
        String couponCode,
        String paymentProvider,
        List<String> coupons,
        Double taxRate,
        Double shippingCost,
        Double shippingThreshold
) {}