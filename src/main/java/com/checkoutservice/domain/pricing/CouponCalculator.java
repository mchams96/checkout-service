package com.checkoutservice.domain.pricing;

import java.util.List;

public class CouponCalculator extends AbstractPricingCalculator {
    private final List<String> coupons;

    public CouponCalculator(List<String> coupons) {
        this.coupons = coupons != null ? coupons : List.of();
    }

    @Override
    protected void apply(PricingContext ctx) {
        if (ctx.coupon != null && coupons.contains(ctx.coupon)) {
            ctx.discounts = ctx.subtotal * 0.10;
        }
    }
}
