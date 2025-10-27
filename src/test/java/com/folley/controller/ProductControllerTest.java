package com.folley.controller;

import com.folley.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductControllerTest {

    private ProductController controller;

    @BeforeEach
    void setUp() {
        controller = new ProductController();
    }

    @Test
    void testGetAllProducts_ShouldReturnEmptyListInitially() {
        ResponseEntity<List<Product>> response = controller.getAllProducts();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void testGetAllProducts_ShouldReturnAllProducts() {
        Product product1 = new Product(1L, "Laptop", 999.99, "High-performance laptop");
        Product product2 = new Product(2L, "Mouse", 29.99, "Wireless mouse");
        controller.createProduct(product1);
        controller.createProduct(product2);

        ResponseEntity<List<Product>> response = controller.getAllProducts();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testCreateProduct_ShouldAddProductToList() {
        Product product = new Product(1L, "Laptop", 999.99, "High-performance laptop");

        ResponseEntity<Product> response = controller.createProduct(product);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(product.getId(), response.getBody().getId());
        assertEquals(product.getName(), response.getBody().getName());
        assertEquals(1, controller.getAllProducts().getBody().size());
    }

    @Test
    void testGetProductById_ShouldReturnProduct() {
        Product product = new Product(1L, "Laptop", 999.99, "High-performance laptop");
        controller.createProduct(product);

        ResponseEntity<Product> response = controller.getProductById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        assertEquals("Laptop", response.getBody().getName());
    }

    @Test
    void testGetProductById_ShouldReturnNotFoundForNonexistentProduct() {
        ResponseEntity<Product> response = controller.getProductById(999L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testDeleteProduct_ShouldRemoveProductFromList() {
        Product product = new Product(1L, "Laptop", 999.99, "High-performance laptop");
        controller.createProduct(product);

        ResponseEntity<Void> response = controller.deleteProduct(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertTrue(controller.getAllProducts().getBody().isEmpty());
    }

    @Test
    void testDeleteProduct_ShouldReturnNotFoundForNonexistentProduct() {
        ResponseEntity<Void> response = controller.deleteProduct(999L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testUpdateProduct_ShouldUpdateExistingProduct() {
        Product originalProduct = new Product(1L, "Laptop", 999.99, "High-performance laptop");
        controller.createProduct(originalProduct);

        Product updatedProduct = new Product(null, "Gaming Laptop", 1299.99, "High-end gaming laptop");
        ResponseEntity<Product> response = controller.updateProduct(1L, updatedProduct);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        assertEquals("Gaming Laptop", response.getBody().getName());
        assertEquals(1299.99, response.getBody().getPrice());

        // Verify the product was actually updated in the list
        ResponseEntity<Product> retrievedProduct = controller.getProductById(1L);
        assertEquals("Gaming Laptop", retrievedProduct.getBody().getName());
        assertEquals(1299.99, retrievedProduct.getBody().getPrice());
    }

    @Test
    void testUpdateProduct_ShouldReturnNotFoundForNonexistentProduct() {
        Product updatedProduct = new Product(1L, "Gaming Laptop", 1299.99, "High-end gaming laptop");

        ResponseEntity<Product> response = controller.updateProduct(999L, updatedProduct);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testMultipleOperations() {
        // Create multiple products
        controller.createProduct(new Product(1L, "Laptop", 999.99, "Laptop desc"));
        controller.createProduct(new Product(2L, "Mouse", 29.99, "Mouse desc"));
        controller.createProduct(new Product(3L, "Keyboard", 79.99, "Keyboard desc"));

        assertEquals(3, controller.getAllProducts().getBody().size());

        // Delete one product
        controller.deleteProduct(2L);
        assertEquals(2, controller.getAllProducts().getBody().size());

        // Update one product
        Product updatedKeyboard = new Product(null, "Mechanical Keyboard", 129.99, "Mechanical keyboard");
        controller.updateProduct(3L, updatedKeyboard);

        ResponseEntity<Product> updated = controller.getProductById(3L);
        assertEquals("Mechanical Keyboard", updated.getBody().getName());
        assertEquals(129.99, updated.getBody().getPrice());
    }
}
