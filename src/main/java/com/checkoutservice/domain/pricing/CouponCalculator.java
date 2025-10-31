package com.checkoutservice.domain.pricing;

public class CouponCalculator extends AbstractPricingCalculator {
    @Override
    protected void apply(PricingContext ctx) {
        if (ctx.coupon != null && ctx.coupon.equalsIgnoreCase("SAVE10")) {
            ctx.discounts = ctx.subtotal * 0.10;
        }
    }
}
