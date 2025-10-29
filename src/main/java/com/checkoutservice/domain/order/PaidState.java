package com.checkoutservice.domain.order;

public class PaidState implements OrderState {
    public OrderState paymentSucceeded(){ return this; }
    public OrderState paymentFailed(){ return this; }
    public String name(){ return "Paid"; }
}
