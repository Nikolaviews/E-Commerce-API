package com.Firewheels.service;

import com.Firewheels.model.CartItem;
import com.Firewheels.controller.CartItemRequest;
import com.Firewheels.model.Product;
import com.Firewheels.repository.CartItemRepository;
import com.Firewheels.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartItemServiceImpl implements CartItemsService {

    private final CartItemRepository cartItemRepository;
    private final ProductService productService;

    @Autowired
    public CartItemServiceImpl(CartItemRepository cartItemRepository, ProductService productService) {
        this.cartItemRepository = cartItemRepository;
        this.productService = productService;
    }

    @Override
    public CartItem saveCartItem(CartItemRequest cartItemRequest) {
        Product product = productService.getProductById(cartItemRequest.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + cartItemRequest.getProductId()));

        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(cartItemRequest.getQuantity());

        return cartItemRepository.save(cartItem);
    }
}
