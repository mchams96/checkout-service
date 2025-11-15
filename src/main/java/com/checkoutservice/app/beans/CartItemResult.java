package com.checkoutservice.app.beans;

public record CartItemResult(String productId, int qty, double unitPrice, double lineTotal) {}
