package com.Firewheels.controller;

import com.Firewheels.model.Product;
import com.Firewheels.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/showall")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PostMapping("/saveproduct")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product savedProduct = productService.saveProduct(product);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    @GetMapping("/descriptions")
    public ResponseEntity<List<String>> getAllProductDescriptions() {
        List<String> descriptions = productService.getAllProductDescriptions();
        return ResponseEntity.ok(descriptions);
    }

    @PostMapping("/addtocart")
    public ResponseEntity<String> addToCart(@RequestBody CartItemRequest cartItemRequest) {
        try {
            productService.addToCart(cartItemRequest);
            return ResponseEntity.ok("Product added to cart successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding product to cart: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        if (!productService.getProductById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        product.setId(id);
        Product updatedProduct = productService.saveProduct(product);
        return ResponseEntity.ok(updatedProduct);
    }

    @PutMapping("/{id}/updaterating")
    public ResponseEntity<Product> updateProductRating(@PathVariable Long id, @RequestParam double rating, @RequestParam String ratingImage) {
        try {
            productService.updateProductRating(id, rating, ratingImage);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/random")
    public ResponseEntity<List<Product>> getRandomProducts() {
        List<Product> randomProducts = productService.getRandomProducts();
        return ResponseEntity.ok(randomProducts);
    }
    @GetMapping("/random2")
    public ResponseEntity<List<Product>> getRandomProducts2() {
        List<Product> randomProducts = productService.getRandomProducts();
        return ResponseEntity.ok(randomProducts);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}