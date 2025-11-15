package com.checkoutservice.app.beans;

import java.util.List;
public record GetCartResult(String id, String currency, List<CartItemResult> items) {}
