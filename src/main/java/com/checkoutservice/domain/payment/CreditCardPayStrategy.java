package com.checkoutservice.domain.payment;

public class CreditCardPayStrategy implements PaymentStrategy {
    @Override
    public boolean pay(double amount) {
        System.out.println("Paid $" + amount + " using Credit Card.");
        return true;
    }

    @Override
    public String name() { return "CreditCardPay"; }
}
