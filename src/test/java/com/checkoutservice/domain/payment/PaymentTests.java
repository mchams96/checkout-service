package com.checkoutservice.domain.payment;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


public class PaymentTests {
    @Test
    void insure_that_PaymentProcessor_uses_strategy_to_process_payment(){
        PrintStream originalOut = System.out;
        ByteArrayOutputStream testOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOutputStream)); // Redirect System.out

        PaymentStrategy mockStrategy = mock(PaymentStrategy.class);
        when(mockStrategy.pay(anyDouble())).thenReturn(true);
        when(mockStrategy.name()).thenReturn("Mock");

        PaymentProcessor paymentProcessor = new PaymentProcessor(mockStrategy);
        double amount = 200.;
        paymentProcessor.processPayment(amount);

        // Verify that strategy methods were called:
        verify(mockStrategy).pay(amount);
        verify(mockStrategy).name();

        assertEquals("Payment with Mock successful.\r\n", testOutputStream.toString());
        System.setOut(originalOut);
    }

    @Test
    void test_credit_card_pay_strategy(){
        // Backup the original System.out
        PrintStream originalOut = System.out;

        // Create a stream to hold the output
        ByteArrayOutputStream testOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOutputStream)); // Redirect System.out

        var paymentStrategy = new CreditCardPayStrategy();
        assertEquals("CreditCardPay", paymentStrategy.name());
        assertTrue(paymentStrategy.pay(10.0));

        assertEquals("Paid $10.0 using Credit Card.\r\n", testOutputStream.toString());

        // Restore original System.out
        System.setOut(originalOut);
    }

    @Test
    void test_paypal_pay_strategy(){
        PrintStream originalOut = System.out;
        ByteArrayOutputStream testOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOutputStream));

        var paymentStrategy = new PaypalPayStrategy();
        assertEquals("PaypalPay", paymentStrategy.name());
        assertTrue(paymentStrategy.pay(10.0));
        assertEquals("Paid $10.0 using Paypal.\r\n", testOutputStream.toString());

        System.setOut(originalOut);
    }

    @Test
    void test_mock_pay_strategy(){
        PrintStream originalOut = System.out;
        ByteArrayOutputStream testOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOutputStream));

        var paymentStrategy = new MockPayStrategy();
        assertEquals("MockPay", paymentStrategy.name());
        assertTrue(paymentStrategy.pay(10.0));
        assertEquals("Paid $10.0 using MockPay.\r\n", testOutputStream.toString());

        System.setOut(originalOut);
    }
}

