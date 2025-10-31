package com.checkoutservice.domain.pricing;

import com.checkoutservice.domain.cart.Cart;
import com.checkoutservice.domain.cart.Item;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.List;

public class PriceCalculatorTests {

    @Test
    void pricing_context_test(){
        Cart cart = new Cart(List.of(
                new Item("T-shirt", 25.0),
                new Item("Shoes", 60.0)
        ));

        PricingContext pricingContext = new PricingContext(cart, "SAVE10");

        PricingCalculator pricingCalculator = new BasePriceCalculator();
        pricingCalculator.setNext(new CouponCalculator())
                    .setNext(new TaxCalculator(8))
                    .setNext(new ShippingCalculator(5, 100));

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
