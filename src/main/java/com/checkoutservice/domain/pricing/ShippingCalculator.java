package com.checkoutservice.domain.pricing;

public class ShippingCalculator extends AbstractPricingCalculator {
    private final double shipping;
    private final double freeThreshold;

    public ShippingCalculator(double shipping, double freeThreshold) {
        this.shipping = shipping;
        this.freeThreshold = freeThreshold;
    }

    @Override
    protected void apply(PricingContext ctx) {
        ctx.shipping = ctx.subtotal >= freeThreshold ? 0 : shipping;
    }
}
