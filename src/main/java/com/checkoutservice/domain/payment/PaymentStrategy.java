package com.checkoutservice.domain.payment;

public interface PaymentStrategy {
    boolean pay(double amount);
    String name();
}
