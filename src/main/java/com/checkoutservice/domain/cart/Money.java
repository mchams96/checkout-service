package com.checkoutservice.domain.cart;

public class Money {
    private final double amount;
    private final String currency;

    public Money(double amount, String currency) {
        this.amount = amount;
        this.currency = currency == null ? "USD" : currency;
    }

    public double amount() { return amount; }
    public String currency() { return currency; }

    public Money plus(Money o) {
        ensure(o);
        return new Money(amount + o.amount, currency);
    }

    public Money minus(Money o) {
        ensure(o);
        return new Money(amount - o.amount, currency);
    }

    public Money percent(double p) {
        return new Money(amount * p, currency);
    }

    private void ensure(Money o) {
        if (!this.currency.equals(o.currency)) {
            throw new IllegalArgumentException("Currency mismatch");
        }
    }
}
