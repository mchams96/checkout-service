package com.checkoutservice.domain.pricing;

public class TaxCalculator extends AbstractPricingCalculator {
    private final double rate;

    public TaxCalculator(double rate) {
        this.rate = rate;
    }

    @Override
    protected void apply(PricingContext ctx) {
        ctx.tax = (ctx.subtotal - ctx.discounts) * (rate / 100.0);
    }
}
