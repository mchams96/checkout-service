package com.checkoutservice.domain.payment;

public class PaypalPayStrategy implements PaymentStrategy{
    @Override
    public boolean pay(double amount) {
        System.out.println("Paid $" + amount + " using Paypal.");
        return true;
    }

    @Override
    public String name() { return "PaypalPay"; }
}
