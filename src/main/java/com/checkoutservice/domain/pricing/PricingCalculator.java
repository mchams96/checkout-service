package com.checkoutservice.domain.pricing;

public interface PricingCalculator {
    PricingCalculator setNext(PricingCalculator next);
    PricingContext calculate(PricingContext ctx);
}
