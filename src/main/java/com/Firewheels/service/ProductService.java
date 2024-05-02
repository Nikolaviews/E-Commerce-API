package com.Firewheels.service;

import com.Firewheels.controller.CartItemRequest;
import com.Firewheels.model.CartItem;
import com.Firewheels.model.Product;
import com.Firewheels.repository.ProductRepository;
import com.Firewheels.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Autowired
    private CartItemRepository cartItemRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public List<String> getAllProductDescriptions() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(Product::getDescription).collect(Collectors.toList());
    }

    public void addToCart(CartItemRequest cartItemRequest) {
        Optional<Product> productOptional = productRepository.findById(cartItemRequest.getProductId());
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(cartItemRequest.getQuantity());
            cartItemRepository.save(cartItem);
        } else {
            throw new IllegalArgumentException("Product not found with ID: " + cartItemRequest.getProductId());
        }
    }

    // Method to update product rating
    public void updateProductRating(Long productId, double rating, String ratingImage) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            product.setRating(rating);
            product.setRatingImage(ratingImage);
            productRepository.save(product);
        } else {
            throw new IllegalArgumentException("Product not found with ID: " + productId);
        }
    }

    public List<Product> getRandomProducts() {
        return productRepository.findRandomProducts();
    }
    public List<Product> getRandomProducts2() {
        return productRepository.findRandomProducts();
    }
}
