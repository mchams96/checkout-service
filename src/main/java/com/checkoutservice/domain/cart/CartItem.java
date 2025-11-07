package com.checkoutservice.domain.cart;

public class CartItem {
    private final String productID;
    private final int quantity;
    private final Money unitPrice;

    public CartItem(String productID, int quantity, Money unitPrice) {
        this.productID = productID;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    // Methods:
    public Money lineTotal(){
        return new Money(unitPrice.amount() * quantity, unitPrice.currency());
    }

    // Getters:
    public String getProductID() {
        return productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public Money getUnitPrice() {
        return unitPrice;
    }
}
