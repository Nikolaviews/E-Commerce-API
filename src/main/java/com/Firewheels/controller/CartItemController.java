package com.Firewheels.controller;

import com.Firewheels.model.CartItem;
import com.Firewheels.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartItemController {

    private final CartItemService cartItemService;

    @Autowired
    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @GetMapping("/items")
    public ResponseEntity<List<Object[]>> getCartItemsWithDetails() {
        List<Object[]> cartItemsWithDetails = cartItemService.getCartItemsWithDetails();
        return ResponseEntity.ok(cartItemsWithDetails);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestParam Long productId, @RequestParam int quantity) {
        try {
            cartItemService.addToCart(productId, quantity);
            return ResponseEntity.ok("Product added to cart successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding product to cart: " + e.getMessage());
        }
    }

    @PutMapping("/{cartItemId}")
    public ResponseEntity<String> updateCartItemQuantity(@PathVariable Long cartItemId, @RequestParam int quantity) {
        try {
            cartItemService.updateCartItemQuantity(cartItemId, quantity);
            return ResponseEntity.ok("Cart item quantity updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating cart item quantity: " + e.getMessage());
        }
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<String> removeCartItem(@PathVariable Long cartItemId) {
        try {
            cartItemService.removeCartItem(cartItemId);
            return ResponseEntity.ok("Cart item removed successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error removing cart item: " + e.getMessage());
        }
    }

    @DeleteMapping("/clear")
    public ResponseEntity<String> clearCart() {
        try {
            cartItemService.clearCart();
            return ResponseEntity.ok("Cart cleared successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error clearing cart: " + e.getMessage());
        }
    }
}
