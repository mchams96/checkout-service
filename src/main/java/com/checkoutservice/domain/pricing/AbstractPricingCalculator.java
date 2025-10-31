package com.checkoutservice.domain.pricing;

public abstract class AbstractPricingCalculator implements PricingCalculator {
    protected PricingCalculator next;

    @Override
    public PricingCalculator setNext(PricingCalculator next) {
        this.next = next;
        return next; 
    }

    @Override
    public PricingContext calculate(PricingContext ctx) {
        apply(ctx);
        if (next != null) {
            return next.calculate(ctx);
        }
        return ctx;
    }

    protected abstract void apply(PricingContext ctx);
}
