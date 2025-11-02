package com.checkoutservice.domain.pricing;

import com.checkoutservice.domain.cart.Cart;

public class PricingContext {
    public Cart cart;          
    public String coupon;
    public double subtotal;
    public double discounts;
    public double tax;
    public double shipping;
    public double total;

    public PricingContext(Cart cart, String coupon) {
        this.cart = cart;
        this.coupon = coupon;
        this.subtotal = 0;
        this.discounts = 0;
        this.tax = 0;
        this.shipping = 0;
        this.total = 0;
    }

    public void finalizeTotals() {
        total = subtotal - discounts + tax + shipping;
    }
}
