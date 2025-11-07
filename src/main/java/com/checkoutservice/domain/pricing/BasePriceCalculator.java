package com.checkoutservice.domain.pricing;

import com.checkoutservice.domain.cart.CartItem;

public class BasePriceCalculator extends AbstractPricingCalculator {
    @Override
    protected void apply(PricingContext pricingContext) {
        pricingContext.subtotal = pricingContext.cart.getItems()
                        .stream()
                        .mapToDouble(cartItemIter -> cartItemIter.lineTotal().amount())
                        .sum();
    }
}
