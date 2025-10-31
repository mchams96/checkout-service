package com.checkoutservice.domain.pricing;

import com.checkoutservice.domain.cart.Cart;
import com.checkoutservice.domain.cart.Item;


import java.util.List;

public class PricingDemo {
    public static void main(String[] args) {
        Cart cart = new Cart(List.of(
                new Item("T-shirt", 25.0),
                new Item("Shoes", 60.0)
        ));

        PricingContext ctx = new PricingContext(cart, "SAVE10");

        PricingCalculator calculator = new BasePriceCalculator();
        calculator
            .setNext(new CouponCalculator())
            .setNext(new TaxCalculator(8))
            .setNext(new ShippingCalculator(5, 100));

        calculator.calculate(ctx);
        ctx.finalizeTotals();

        System.out.println("Subtotal: $" + ctx.subtotal);
        System.out.println("Discounts: $" + ctx.discounts);
        System.out.println("Tax: $" + ctx.tax);
        System.out.println("Shipping: $" + ctx.shipping);
        System.out.println("Total: $" + ctx.total);
    }
}
