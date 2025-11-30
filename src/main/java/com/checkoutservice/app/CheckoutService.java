package com.checkoutservice.app;

import com.checkoutservice.app.beans.*;
import com.checkoutservice.app.exceptions.*;

import com.checkoutservice.domain.cart.Cart;
import com.checkoutservice.domain.cart.CartItem;
import com.checkoutservice.domain.cart.CartRepository;
import com.checkoutservice.domain.cart.Money;

import com.checkoutservice.domain.order.Order;
import com.checkoutservice.domain.order.OrderRepository;

import com.checkoutservice.domain.payment.*;

import com.checkoutservice.domain.pricing.BasePriceCalculator;
import com.checkoutservice.domain.pricing.CouponCalculator;
import com.checkoutservice.domain.pricing.PricingCalculator;
import com.checkoutservice.domain.pricing.PricingContext;
import com.checkoutservice.domain.pricing.TaxCalculator;
import com.checkoutservice.domain.pricing.ShippingCalculator;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckoutService {

    // ===== Instance variables =====
    private final CartRepository carts;
    private final OrderRepository orders;

    // // ===== Constructor =====
    public CheckoutService(CartRepository carts, OrderRepository orders) {
        this.carts = carts;
        this.orders = orders;
    }

    // ===== Cart handling =====
    public CreateCartResult createCart(CreateCartJob job) {
        String cartId = carts.create(job.currency());
        return new CreateCartResult(cartId);
    }

    public GetCartResult addItemToCart(String cartId, AddCartItemJob job) {
        Cart cart = carts.get(cartId);
        if (cart == null) {
            throw new CartNotFoundException(cartId);
        }

        Money unitPrice = new Money(job.unitPrice(), cart.getCurrency());
        CartItem item = new CartItem(job.productId(), job.qty(), unitPrice);

        Cart updated = carts.addItem(cartId, item);

        return convertToGetCartResult(updated);
    }

    public GetCartResult getCart(String cartId) {
        Cart cart = carts.get(cartId);
        if (cart == null) {
            throw new CartNotFoundException(cartId);
        }

        return convertToGetCartResult(cart);
    }

    private GetCartResult convertToGetCartResult(Cart cart) {
        List<CartItemResult> items = cart.getItems().stream()
                .map(ci -> new CartItemResult(
                        ci.getProductID(),
                        ci.getQuantity(),
                        ci.getUnitPrice().amount(),
                        ci.lineTotal().amount()
                ))
                .toList();

        return new GetCartResult(cart.getId(), cart.getCurrency(), items);
    }

    public void deleteCart(String cartId) {
        Cart cart = carts.get(cartId);
        if (cart == null) {
            throw new CartNotFoundException(cartId);
        }
        
        carts.delete(cartId);
    }


    // ===== Checkout handling =====

    public CheckoutResult start(CheckoutJob checkoutJob) {
        Cart cart = carts.get(checkoutJob.cartId());
        if (cart == null) {
            throw new CartNotFoundException(checkoutJob.cartId());
        }

        // Build pricing chain of commands dynamically per request
        PricingCalculator calculator =
                new BasePriceCalculator()
                        .setNext(new CouponCalculator(checkoutJob.coupons()))
                        .setNext(new TaxCalculator(checkoutJob.taxRate()))
                        .setNext(new ShippingCalculator(
                                checkoutJob.shippingCost(),
                                checkoutJob.shippingThreshold()
                        ));

        // Run pricing pipeline
        PricingContext pricingContext = calculator.calculate(new PricingContext(cart, checkoutJob.couponCode()));

        // Determine currency (prefer request; fallback to cart)
        String currency = (checkoutJob.currency() == null || checkoutJob.currency().isBlank())
                ? cart.getCurrency()
                : checkoutJob.currency();

        // Create Order with total as Money(double, currency)
        Order order = new Order(new Money(pricingContext.total, currency));

        // Pick payment strategy at runtime
        String providerName = (checkoutJob.paymentProvider() == null || checkoutJob.paymentProvider().isBlank())
                ? "FailPay"
                : checkoutJob.paymentProvider();

        PaymentStrategy strategy = resolvePaymentStrategy(providerName);

        boolean ok = strategy.pay(pricingContext.total);
        if (ok) {
            order.onPaymentSucceeded();
        } else {
            order.onPaymentFailed();
        }

        orders.save(order);
        return new CheckoutResult(order.getId(), order.stateName(), pricingContext.total, strategy.name());
    }

    // TODO: add exception, and exception handling. After that, Fail and Mock payments should be removed.
    private PaymentStrategy resolvePaymentStrategy(String providerName){
        return switch (providerName) {
            case "COD", "CashOnDelivery" -> new CashOnDeliveryStrategy();
            case "PaypalPay" -> new PaypalPayStrategy();
            case "CreditCardPay" -> new CreditCardPayStrategy();
            default -> new FailPayStrategy();
        };
    }

    // ===== Orders Handling =====

    public GetOrderResult getOrder(String id) {
        var order = orders.get(id);
        if (order == null) {
            throw new OrderNotFoundException(id);
        }

        return new GetOrderResult(order.getId(), order.amount().amount(), order.amount().currency(), order.stateName());
    }

    public void deleteOrder(String orderId) {
        var order = orders.get(orderId);
        if (order == null) {
            throw new OrderNotFoundException(orderId);
        }
        orders.delete(orderId);
    }
}
