package com.checkoutservice.domain.order;

public class AwaitingPaymentState implements OrderState {
    public OrderState paymentSucceeded(){ return new PaidState(); }
    public OrderState paymentFailed(){ return new FailedState(); }
    public String name(){ return "AwaitingPayment"; }
}
