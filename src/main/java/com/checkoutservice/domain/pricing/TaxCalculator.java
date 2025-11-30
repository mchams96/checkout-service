package com.checkoutservice.domain.pricing;

public class TaxCalculator extends AbstractPricingCalculator {
    private final Double rate;

    public TaxCalculator(Double rate) {
        this.rate = rate;
    }

    @Override
    protected void apply(PricingContext ctx) {
        double taxPercent = rate != null ? rate : 0;
        ctx.tax = Math.max(0, ctx.subtotal - ctx.discounts) * (taxPercent / 100.0);
    }
}
