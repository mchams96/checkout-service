package com.checkoutservice.domain.pricing;

import com.checkoutservice.domain.cart.Item;
public class BasePriceCalculator extends AbstractPricingCalculator {
    @Override
    protected void apply(PricingContext ctx) {
        
        ctx.subtotal = ctx.cart.getItems().stream().mapToDouble(Item::getPrice).sum(); 
    }
}
