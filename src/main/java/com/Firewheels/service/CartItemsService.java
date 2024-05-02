package com.Firewheels.service;

import com.Firewheels.controller.CartItemRequest;
import com.Firewheels.model.CartItem;

public interface CartItemsService {
    CartItem saveCartItem(CartItemRequest cartItemRequest);
}
