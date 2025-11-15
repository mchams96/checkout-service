package com.checkoutservice.app.beans;

public record GetOrderResult(
        String id,
        double amount,
        String currency,
        String state
) {}