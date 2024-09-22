package sample.shopping;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

@Disabled
class ProductDAOTest {

    ProductDAO productDAO = null;

    @BeforeEach
    void setUp() throws Exception {
        productDAO = new ProductDAO();
    }

    @Test
    void testGetListProducts() {
        int expectedProductListSize = 12;
        try {
            int actualProductListSize = productDAO.getListProductsV2("").size();
            assertEquals(expectedProductListSize, actualProductListSize, "Error, testGetListProducts failed");
        } catch (Exception e) {
            fail("Error, testGetListProducts failed: " + e.getMessage());
        }
    }

    @Test
    void testInsertProduct() throws SQLException {
        ProductDTO newProduct = new ProductDTO("P013", "New Product", 150.0F, 20, "http://example.com/newimage.png");
        boolean isInserted = productDAO.insert(newProduct);
        assertTrue(isInserted, "Product should be inserted successfully");

        // Clean up: Delete the product after test
        productDAO.delete("P013");
    }

    @Test
    void testDeleteProduct() throws SQLException {
        // First, insert a product to delete
        ProductDTO productToDelete = new ProductDTO("P013", "Product to Delete", 200.0F, 15,
                "http://example.com/deleteimage.png");
        productDAO.insert(productToDelete);

        // Now, delete the product
        boolean isDeleted = productDAO.delete("P013");
        assertTrue(isDeleted, "Product should be deleted successfully");

        // Verify that the product no longer exists
        assertFalse(productDAO.checkDuplicate("P013"), "Product should not exist after deletion");
    }
}