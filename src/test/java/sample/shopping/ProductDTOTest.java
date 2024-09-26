package sample.shopping;

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
        String expectedString = "ProductDTO(productID=P001, productName=Test Product, price=100.0, "
                + "quantity=5, imageUrl=http://example.com/image.jpg)";
        assertEquals(expectedString, product.toString());
    }

    @Test
    void testProductDTOConstructorWithNullValues() {
        ProductDTO product = new ProductDTO(null, null, 0, 0, null);
        assertNull(product.getProductID());
        assertNull(product.getProductName());
        assertEquals(0, product.getPrice());
        assertEquals(0, product.getQuantity());
        assertNull(product.getImageUrl());
    }

    @Test
    void testProductDTOConstructorWithNegativePrice() {
        ProductDTO product = new ProductDTO("P004", "Test Product 4", -50.0F, 5, "http://example.com/image4.jpg");
        assertEquals(-50.0F, product.getPrice(), "Price should be negative");
    }

    @Test
    void testProductDTOConstructorWithZeroQuantity() {
        ProductDTO product = new ProductDTO("P005", "Test Product 5", 100.0F, 0, "http://example.com/image5.jpg");
        assertEquals(0, product.getQuantity(), "Quantity should be zero");
    }

    @Test
    void testProductDTOConstructorWithEmptyValues() {
        ProductDTO product = new ProductDTO("", "", 0.0F, 0, "");
        assertEquals("", product.getProductID());
        assertEquals("", product.getProductName());
        assertEquals(0.0F, product.getPrice());
        assertEquals(0, product.getQuantity());
        assertEquals("", product.getImageUrl());
    }

    @Test
    void testSpecialCharactersInProductName() {
        ProductDTO product = new ProductDTO("P006", "Test@Product#6", 100.0F, 5, "http://example.com/image6.jpg");
        assertEquals("Test@Product#6", product.getProductName());
    }

    @Test
    void testProductDTOConstructorWithLargeValues() {
        ProductDTO product = new ProductDTO("P007", "Test Product 7", Float.MAX_VALUE, Integer.MAX_VALUE,
                "http://example.com/image7.jpg");
        assertEquals(Float.MAX_VALUE, product.getPrice());
        assertEquals(Integer.MAX_VALUE, product.getQuantity());
    }
}