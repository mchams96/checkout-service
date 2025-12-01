package com.checkoutservice.domain.pricing;

import java.util.List;

public class PricingCalculatorBuilder {
    private final BasePriceCalculator baseCalculator = new BasePriceCalculator();
    private Double shippingCost;
    private Double shippingThreshold;
    private List<String> coupons;
    private Double taxRate;

    public PricingCalculatorBuilder(){}

    public PricingCalculatorBuilder withShipping(double shippingCost, double shippingThreshold){
        this.shippingCost = shippingCost;
        this.shippingThreshold = shippingThreshold;
        return this;
    }

    public PricingCalculatorBuilder withCoupons(List<String> coupons){
        this.coupons = coupons;
        return this;
    }

    public PricingCalculatorBuilder withTaxRate(double taxRate){
        this.taxRate = taxRate;
        return this;
    }

    public PricingCalculator build(){
        PricingCalculator calc = baseCalculator;
        if(coupons != null){
            calc = baseCalculator.setNext(new CouponCalculator(coupons));
        }

        if(taxRate != null){
            calc = calc.setNext(new TaxCalculator(taxRate));
        }

        if(shippingCost != null){
            calc.setNext(new ShippingCalculator(shippingCost, shippingThreshold));
        }

        return baseCalculator;
    }
}
