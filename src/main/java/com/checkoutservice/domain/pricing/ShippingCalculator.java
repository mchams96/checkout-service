package com.checkoutservice.domain.pricing;

public class ShippingCalculator extends AbstractPricingCalculator {
    private final Double shipping;
    private final Double freeThreshold;

    public ShippingCalculator(Double shipping, Double freeThreshold) {
        this.shipping = shipping;
        this.freeThreshold = freeThreshold;
    }

    @Override
    protected void apply(PricingContext ctx) {
        double shippingValue = (shipping != null ? shipping : 0);
        double thresholdValue = (freeThreshold != null ? freeThreshold : 0);

        ctx.shipping = ctx.subtotal >= thresholdValue  ? 0 : shippingValue;
    }
}
