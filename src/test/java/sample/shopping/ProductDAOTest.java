package sample.shopping;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductDAOTest {

    ProductDAO productDAO = null;

    @BeforeEach
    void setUp() throws Exception {
        productDAO = new ProductDAO();
    }

    @Test
    void testGetListProducts() {
        int expectedProductListSize = 12;
        try{
            int actualProductListSize = productDAO.getListProductsV2("").size();
            assertEquals(expectedProductListSize, actualProductListSize, "Error, testGetListProducts failed");
        }catch (Exception e){
            fail("Error, testGetListProducts failed: " + e.getMessage());
        }
    }
}