package sample.shopping;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProductDTOTest {

    @Test
    void testProductDTOConstructorWithImageUrl() {
        ProductDTO product = new ProductDTO("P001", "Test Product", 100.0F, 5, "http://example.com/image.jpg");
        assertEquals("P001", product.getProductID());
        assertEquals("Test Product", product.getProductName());
        assertEquals(100.0F, product.getPrice());
        assertEquals(5, product.getQuantity());
        assertEquals("http://example.com/image.jpg", product.getImageUrl());
    }

    @Test
    void testProductDTOConstructorWithoutImageUrl() {
        ProductDTO product = new ProductDTO("P002", "Test Product 2", 200.0F, 3);
        assertEquals("P002", product.getProductID());
        assertEquals("Test Product 2", product.getProductName());
        assertEquals(200.0F, product.getPrice());
        assertEquals(3, product.getQuantity());
        assertNull(product.getImageUrl());
    }

    @Test
    void testSettersAndGetters() {
        ProductDTO product = new ProductDTO();
        product.setProductID("P003");
        product.setProductName("Test Product 3");
        product.setPrice(150.0F);
        product.setQuantity(10);
        product.setImageUrl("http://example.com/image3.jpg");

        assertEquals("P003", product.getProductID());
        assertEquals("Test Product 3", product.getProductName());
        assertEquals(150.0F, product.getPrice());
        assertEquals(10, product.getQuantity());
        assertEquals("http://example.com/image3.jpg", product.getImageUrl());
    }

    @Test
    void testToString() {
        ProductDTO product = new ProductDTO("P001", "Test Product", 100.0F, 5, "http://example.com/image.jpg");
        String expectedString = "ProductDTO(productID=P001, name=Test Product, price=100.0, "
            + "quantity=5, imageUrl=http://example.com/image.jpg)";
        assertNotEquals(expectedString, product.toString());
    }
}