package com.checkoutservice.app;

import com.checkoutservice.app.beans.CheckoutJob;
import com.checkoutservice.app.beans.CheckoutResult;

import com.checkoutservice.domain.cart.Cart;
import com.checkoutservice.domain.cart.CartItem;
import com.checkoutservice.domain.cart.CartRepository;
import com.checkoutservice.domain.cart.Money;
import com.checkoutservice.domain.order.OrderRepository;
import com.checkoutservice.domain.pricing.*;

import com.checkoutservice.persistence.InMemoryCartRepository;
import com.checkoutservice.persistence.InMemoryOrderRepository;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckoutServiceComponentTest {
    @Test
    void start_checkout_test(){
        CartRepository cartRepository = new InMemoryCartRepository();
        String cartId = cartRepository.create("USD");
        Cart cart = cartRepository.get(cartId);
        cart.addItem(new CartItem("p_123", 1, new Money(25, "USD")));
        cart.addItem(new CartItem("p_456", 2, new Money(10, "USD")));

        CheckoutService service = new CheckoutService(cartRepository, new InMemoryOrderRepository());

        CheckoutJob checkoutJob = new CheckoutJob(cartId, "USD", "WELCOME10", "CreditCardPay",
                List.of("WELCOME10", "HAPPY2026", "WELCOMEBACK"), 15.0, 5.0, 50.0);
        CheckoutResult result = service.start(checkoutJob);

        double price = 25 + 20;
        System.out.println("Base price: " + price);
        price = (45.0 * (1 - 0.1));
        System.out.println("Price after coupon:" + price);
        System.out.println("Tax amount:" + price * 0.15);
        price = price * (1.0 + 0.15);
        System.out.println("Price after tax:" + price);
        price += 5.0;
        System.out.println("Price after shipping:" + price);
        double totalExpected =  price;
        assertEquals("Paid", result.state());
        assertEquals(totalExpected, result.total(), 1e-6);
        assertEquals("CreditCardPay", result.provider());
    }
}
