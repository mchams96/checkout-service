package com.checkoutservice.domain.payment;

public class PaymentProcessor {
    private PaymentStrategy strategy;

    public PaymentProcessor(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    public void setPaymentStrategy(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    public void processPayment(double amount) {
        if (strategy.pay(amount)) {
            System.out.println("Payment with " + strategy.name() + " successful.");
        } else {
            System.out.println("Payment failed.");
        }
    }
}
