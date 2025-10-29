package com.checkoutservice.domain.payment;

public class FailPayStrategy implements PaymentStrategy {
    @Override
    public boolean pay(double amount){ return false; }

    @Override
    public String name(){ return "FailPay"; }
}
