package com.checkoutservice.domain.payment;

public class MockPayStrategy implements PaymentStrategy {
    @Override
    public boolean pay(double amount){
        System.out.println("Paid $" + amount + " using MockPay.");
        return true;
    }

    @Override
    public String name(){ return "MockPay"; }
}
