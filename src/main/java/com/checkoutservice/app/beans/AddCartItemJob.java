package com.checkoutservice.app.beans;

public record AddCartItemJob(String productId, int qty, double unitPrice) {}
