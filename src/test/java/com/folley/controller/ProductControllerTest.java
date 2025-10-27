package com.folley.controller;

import com.folley.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class ProductControllerTest {

    private ProductController controller;

    @BeforeEach
    void setUp() {
        controller = new ProductController();
    }

    @Test
    void testGetAllProducts_ShouldReturnEmptyListInitially() {
        ResponseEntity<java.util.List<Product>> response = controller.getAllProducts();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void testCreateProduct_ShouldAddProductToList() {
        Product product = new Product(1L, "Laptop", 999.99, "High-performance laptop");

        ResponseEntity<Product> response = controller.createProduct(product);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(product, response.getBody());
        assertEquals(1, controller.getAllProducts().getBody().size());
    }

    @Test
    void testUpdateProduct_ShouldUpdateExistingProduct() {
        // Arrange: Create an initial product
        Product originalProduct = new Product(1L, "Laptop", 999.99, "High-performance laptop");
        controller.createProduct(originalProduct);

        // Create updated product data
        Product updatedProduct = new Product(1L, "Gaming Laptop", 1299.99, "High-end gaming laptop");

        // Act: Update the product
        ResponseEntity<Product> response = controller.updateProduct(1L, updatedProduct);

        // Assert: This test will FAIL because the controller doesn't actually update the product in the list
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Get the product from the list to verify it was updated
        ResponseEntity<Product> retrievedProduct = controller.getProductById(1L);
        assertEquals("Gaming Laptop", retrievedProduct.getBody().getName());
        assertEquals(1299.99, retrievedProduct.getBody().getPrice());
        assertEquals("High-end gaming laptop", retrievedProduct.getBody().getDescription());
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
    void testGetProductById_ShouldReturnNotFoundForNonexistentProduct() {
        ResponseEntity<Product> response = controller.getProductById(999L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
