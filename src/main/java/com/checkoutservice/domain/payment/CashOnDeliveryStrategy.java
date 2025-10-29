package com.checkoutservice.domain.payment;

public class CashOnDeliveryStrategy implements PaymentStrategy {
    @Override
    public boolean pay(double amount) {
        // For now, we return true.
        // In reality, we mark as AwaitingPayment until delivery.
        System.out.println("$" + amount + " to be payed on delivery.");
        return true;
    }

    @Override
    public String name() { return "CashOnDelivery"; }
}
