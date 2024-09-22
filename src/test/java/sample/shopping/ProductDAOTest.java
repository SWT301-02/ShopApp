package sample.shopping;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.persistence.Persistence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import sample.repository.ProductRepository;

import java.sql.SQLException;
import java.util.List;

class ProductDAOTest {
    private ProductRepository productRepository;
    private ProductDAO productDAO;

    @BeforeEach
    void setUp() throws Exception {
        productRepository = new ProductRepository(Persistence.createEntityManagerFactory("JPAs"));

        // Clear existing products
        productRepository.executeUpdate("DELETE FROM products");

        // Initialize the sample product data
        String insertSQL = "INSERT INTO products (productID, productName, price, quantity, imageUrl) VALUES " +
                "('P001', 'Gucci Bag', 500, 10, 'img/gucci_bag.jpg'), " +
                "('P002', 'Luon Vui Tuoi T-Shirt', 750, 5, 'img/luonvuituoi.jpg'), " +
                "('P003', 'Mini Skrrr', 250, 20, 'img/mini_skirt.jpg'), " +
                "('P004', 'Short Skirt', 300, 30, 'img/short_skirt.jpg'), " +
                "('P005', 'Short T-Shirt', 150, 40, 'img/short_t_shirt.jpg'), " +
                "('P006', 'Long Skirt', 400, 50, 'img/long_skirt.jpg'), " +
                "('P007', 'Croptop Shirt', 250, 15, 'img/croptop_shirt.jpg'), " +
                "('P008', 'Denim Jacket', 89.99, 50, 'img/denim_jacket.jpg'), " +
                "('P009', 'Summer Dress', 59.99, 75, 'img/summer_dress.jpg'), " +
                "('P010', 'Leather Boots', 129.99, 30, 'img/leather_boots.jpg'), " +
                "('P011', 'Silk Scarf', 29.99, 100, 'img/silk_scarf.jpg'), " +
                "('P012', 'Sports Sneakers', 79.99, 60, 'img/sports_sneakers.jpg');";

        // Execute the insert SQL
        productRepository.executeUpdate(insertSQL);
    }

    @Test
    @Order(1)
    void testGetListProducts() {
        int expectedProductListSize = 12;
        try {
            int actualProductListSize = productRepository.getAllProducts().size();
            assertEquals(expectedProductListSize, actualProductListSize, "Error, testGetListProducts failed");
        } catch (Exception e) {
            fail("Error, testGetListProducts failed: " + e.getMessage());
        }
    }

    @Test
    @Order(2)
    void testInsertProduct() throws SQLException {
        ProductDTO newProduct = new ProductDTO("P013", "New Product", 150.0F, 20, "http://example.com/newimage.png");
        productRepository.saveProduct(newProduct);
        ProductDTO product = productRepository.getProductById("P013");
        assertNotNull(product, "Error, testInsertProduct failed");
    }

    @Test
    @Order(3)
    void testDeleteProduct() throws SQLException {
        // insert product P013
        ProductDTO newProduct = new ProductDTO("P013", "New Product", 150.0F, 20, "http://example.com/newimage.png");
        productRepository.saveProduct(newProduct);
        // check the product exists P013
        productRepository.deleteProduct("P013");
        ProductDTO product = productRepository.getProductById("P013");
        assertNull(product, "Error, testDeleteProduct failed");
    }

    @Test
    @Order(4)
    void testUpdateProduct() throws SQLException {
        // First, insert a product to update
        ProductDTO productToUpdate = new ProductDTO("P013", "Product to Update", 200.0F, 15,
                "http://example.com/updateimage.png");
        productRepository.saveProduct(productToUpdate);

        // Update the product
        productToUpdate.setPrice(250.0F);
        productRepository.updateProduct(productToUpdate);

        // Verify the product was updated
        ProductDTO updatedProduct = productRepository.getProductById("P013");
        assertNotNull(updatedProduct, "Error, testUpdateProduct failed");
        assertEquals(250.0F, updatedProduct.getPrice(), "Error, testUpdateProduct failed: Price not updated");

        // Clean up: Delete the product after test
        productRepository.deleteProduct("P014");
    }

    @Test
    @Order(5)
    void testGetProductByIdNotFound() {
        // Attempt to retrieve a product that does not exist
        ProductDTO product = productRepository.getProductById("P999");
        assertNull(product, "Error, testGetProductByIdNotFound failed: Product should not exist");
    }

    @Test
    @Order(6)
    void testCheckProductIdDuplicate() throws SQLException {
        // Insert a product
        ProductDTO product = new ProductDTO("P013", "Duplicate Check Product", 100.0F, 10,
                "http://example.com/duplicatecheck.png");
        productRepository.saveProduct(product);

        // Check for duplicate
        boolean isDuplicate = productRepository.checkProductIdDuplicate("P013");
        assertTrue(isDuplicate, "Error, testCheckProductIdDuplicate failed: Product ID should be marked as duplicate");

        // Clean up: Delete the product after test
        productRepository.deleteProduct("P015");
    }

    @Test
    @Order(7)
    void testDeleteNonExistentProduct() throws SQLException {
        // Attempt to delete a product that does not exist
        boolean isDeleted = productRepository.deleteProduct("P999");
        assertFalse(isDeleted, "Error, testDeleteNonExistentProduct failed: Should not delete a non-existent product");
    }

    @Test
    @Order(8)
    void testGetAllProductsEmpty() {
        // Clear all products for this test
        productRepository.executeUpdate("DELETE FROM products");
        // Verify that the product list is empty
        List<ProductDTO> products = productRepository.getAllProducts();
        assertTrue(products.isEmpty(), "Error, testGetAllProductsEmpty failed: Product list should be empty");
    }

    @Test
    @Order(9)
    void testUpdateNonExistentProduct() {
        // Attempt to update a product that does not exist
        ProductDTO nonExistentProduct = new ProductDTO("P999", "Non-Existent Product", 100.0F, 10,
                "http://example.com/nonexistent.png");
        Exception exception = assertThrows(Exception.class, () -> {
            productRepository.updateProduct(nonExistentProduct);
        });
        assertTrue(exception.getMessage().contains("not exist"),
                "Error, testUpdateNonExistentProduct failed: Should throw an exception");
    }

    @Test
    @Order(10)
    void testInsertDuplicateProduct() {
        // Insert a product
        ProductDTO product = new ProductDTO("P013", "Duplicate Product", 100.0F, 10,
                "http://example.com/duplicate.png");
        productRepository.saveProduct(product);

        // Attempt to insert a product with the same ID
        ProductDTO duplicateProduct = new ProductDTO("P013", "Another Duplicate Product", 150.0F, 5,
                "http://example.com/anotherduplicate.png");
        Exception exception = assertThrows(Exception.class, () -> {
            productRepository.saveProduct(duplicateProduct);
        });
        assertTrue(exception.getMessage().contains("already exists"),
                "Error, testInsertDuplicateProduct failed: Should throw a duplicate exception");

        // Clean up: Delete the product after test
        productRepository.deleteProduct("P013");
    }

    @Test
    @Order(11)
    void testGetAllProductsWhenNoneExist() {
        // Clear all products for this test
        productRepository.executeUpdate("DELETE FROM products");

        // Verify that the product list is empty
        List<ProductDTO> products = productRepository.getAllProducts();
        assertTrue(products.isEmpty(), "Error, testGetAllProductsWhenNoneExist failed: Product list should be empty");
    }

    @Test
    @Order(12)
    void testCheckDuplicateIdWhenNoProducts() {
        // Clear all products for this test
        productRepository.executeUpdate("DELETE FROM products");

        // Check for duplicate ID when no products exist
        boolean isDuplicate = productRepository.checkProductIdDuplicate("P999");
        assertFalse(isDuplicate,
                "Error, testCheckDuplicateIdWhenNoProducts failed: Should return false for non-existent product ID");
    }

    @Test
    @Order(13)
    void testProductPersistenceAfterRollback() {
        // Attempt to insert a product and simulate an error
        ProductDTO product = new ProductDTO("P014", "Rollback Test Product", 100.0F, 10,
                "http://example.com/rollback.png");
        productRepository.saveProduct(product);

        // Simulate an error during the transaction
        try {
            productRepository.executeUpdate("INVALID SQL"); // This should cause an error
        } catch (Exception e) {
            // Expected exception
        }

        // Verify that the product still exists
        ProductDTO persistedProduct = productRepository.getProductById("P014");
        assertNotNull(persistedProduct,
                "Error, testProductPersistenceAfterRollback failed: Product should still exist");

        // Clean up: Delete the product after test
        productRepository.deleteProduct("P014");
    }

    @Test
    @Order(14)
    void testUpdateProductWithInvalidData() {
        // Attempt to update a product with invalid data (negative price)
        ProductDTO productToUpdate = new ProductDTO("P001", "Invalid Product", -100.0F, 10,
                "http://example.com/invalid.png");
        Exception exception = assertThrows(Exception.class, () -> {
            productRepository.updateProduct(productToUpdate);
        });
        assertTrue(exception.getMessage().contains("data not valid"),
                "Error, testUpdateProductWithInvalidData failed: Should throw an exception for invalid data");
    }

    @Test
    @Order(15)
    void testGetAllProductsWhenSomeExist() {
        // Verify that the product list contains the expected number of products
        List<ProductDTO> products = productRepository.getAllProducts();
        assertFalse(products.isEmpty(),
                "Error, testGetAllProductsWhenSomeExist failed: Product list should not be empty");
    }

    @Test
    @Order(16)
    void testInsertProductWithNullValues() {
        // Attempt to insert a product with null values
        ProductDTO productWithNulls = new ProductDTO(null, null, 0.0F, 0, null);
        Exception exception = assertThrows(Exception.class, () -> {
            productRepository.saveProduct(productWithNulls);
        });
        assertTrue(exception.getMessage().contains("not valid"),
                "Error, testInsertProductWithNullValues failed: Should throw an exception for null values");
    }

    @Test
    @Order(17)
    void testCheckDuplicateIdWithExistingProducts() {
        // Check for duplicate ID when products exist
        boolean isDuplicate = productRepository.checkProductIdDuplicate("P001");
        assertTrue(isDuplicate,
                "Error, testCheckDuplicateIdWithExistingProducts failed: Product ID should be marked as duplicate");
    }

    @Test
    @Order(18)
    void testDeleteProductWithActiveTransactions() {
        // Attempt to delete a product while another transaction is active
        ProductDTO product = new ProductDTO("P014", "Active Transaction Product", 100.0F, 10,
                "http://example.com/active.png");
        productRepository.saveProduct(product);

        // Start a new transaction and attempt to delete
        boolean isDeleted = productRepository.deleteProduct("P014");
        assertTrue(isDeleted, "Error, testDeleteProductWithActiveTransactions failed: Should delete the product");
    }

    // Add more test cases
    @Test
    @Order(19)
    void testGetProductById() {
        ProductDTO product = productRepository.getProductById("P001");
        assertNotNull(product, "Error, testGetProductById failed: Product should exist");
    }
}