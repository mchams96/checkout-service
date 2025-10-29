package com.checkoutservice.domain.order;

import com.checkoutservice.domain.cart.Money;
import java.util.concurrent.atomic.AtomicInteger;

public class Order {
    private static final AtomicInteger SEQ = new AtomicInteger(1000);

    private final String id = "O" + SEQ.getAndIncrement();
    private final Money amount;
    private OrderState state = new AwaitingPaymentState();

    public Order(Money amount) { this.amount = amount; }

    public String getId(){ return id; }
    public Money amount(){ return amount; }
    public String stateName(){ return state.name(); }

    public void onPaymentSucceeded(){ state = state.paymentSucceeded(); }
    public void onPaymentFailed(){ state = state.paymentFailed(); }
}
