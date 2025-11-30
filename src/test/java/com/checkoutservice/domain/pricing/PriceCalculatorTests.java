package com.checkoutservice.domain.pricing;

import com.checkoutservice.domain.cart.Cart;
import com.checkoutservice.domain.cart.CartItem;

import static org.junit.jupiter.api.Assertions.*;

import com.checkoutservice.domain.cart.Money;
import org.junit.jupiter.api.Test;

import java.util.List;

public class PriceCalculatorTests {

    @Test
    void pricing_context_test(){
        Cart cart = new Cart("123", "EUR");
        cart.addItem(new CartItem("T-shirt", 1, new Money(25.0, "EUR")));
        cart.addItem(new CartItem("Shoes", 1, new Money(60.0, "EUR")));

        PricingContext pricingContext = new PricingContext(cart, "SAVE10");

        PricingCalculator pricingCalculator = new BasePriceCalculator();
        pricingCalculator.setNext(new CouponCalculator(List.of("SAVE10", "WIN10")))
                    .setNext(new TaxCalculator(8.0))
                    .setNext(new ShippingCalculator(5.0, 100.0));

        pricingCalculator.calculate(pricingContext);
        pricingContext.finalizeTotals();

        double expectedDiscounts = 85 * 0.1;
        double expectedTax = (85 - expectedDiscounts) * 0.08;
        double expectedShipping = 5;
        assertEquals(expectedDiscounts, pricingContext.discounts);
        assertEquals(expectedTax, pricingContext.tax);
        assertEquals(expectedShipping, pricingContext.shipping);
        assertEquals(85 + expectedShipping + expectedTax - expectedDiscounts , pricingContext.total);
        assertEquals(85.0, pricingContext.subtotal);
    }
}
