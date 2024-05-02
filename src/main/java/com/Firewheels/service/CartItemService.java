package com.Firewheels.service;

import com.Firewheels.model.CartItem;
import com.Firewheels.model.Product;
import com.Firewheels.repository.CartItemRepository;
import com.Firewheels.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.List;
import java.util.Optional;

@Service
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final EntityManager entityManager;

    @Autowired
    public CartItemService(CartItemRepository cartItemRepository, ProductRepository productRepository, EntityManager entityManager) {
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.entityManager = entityManager;
    }

    public List<Object[]> getCartItemsWithDetails() {
        String queryString = "SELECT p.imageUrl, p.name, p.description, p.price, c.quantity " +
                "FROM Product p " +
                "JOIN CartItem c ON p.id = c.product.id";
        Query query = entityManager.createQuery(queryString);
        return query.getResultList();
    }
    public List<CartItem> getCartItems() {
        return cartItemRepository.findAll();
    }

    public void addToCart(Long productId, int quantity) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItemRepository.save(cartItem);
        } else {
            throw new IllegalArgumentException("Product not found with ID: " + productId);
        }
    }

    public void updateCartItemQuantity(Long cartItemId, int quantity) {
        Optional<CartItem> cartItemOptional = cartItemRepository.findById(cartItemId);
        if (cartItemOptional.isPresent()) {
            CartItem cartItem = cartItemOptional.get();
            cartItem.setQuantity(quantity);
            cartItemRepository.save(cartItem);
        } else {
            throw new IllegalArgumentException("Cart item not found with ID: " + cartItemId);
        }
    }

    public void removeCartItem(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    public void clearCart() {
        cartItemRepository.deleteAll();
    }
}
