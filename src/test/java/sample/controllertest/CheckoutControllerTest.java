package sample.controllertest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import sample.controller.CheckoutController;
import sample.shopping.CartDTO;
import sample.shopping.ProductDTO;
import sample.user.UserDTO;

class CheckoutControllerTest {
    private CheckoutController checkoutController;

    @BeforeEach
    void setUp() {
        checkoutController = new CheckoutController();
    }

    @Test
    void testProcessPayment() throws Exception {
        // Integration test for processPayment method
        UserDTO user = new UserDTO("user1", "user1", "US", "password", "manhduonglhp4@gmail.com");
        CartDTO cart = new CartDTO();
        cart.add("1", new ProductDTO("1", "Test Product", 100.0F , 1));
        cart.add("2", new ProductDTO("2", "Test Product 2", 200.0F , 1));
        boolean result = checkoutController.processPayment( cart, user, "cash");
        assertTrue(result, "Should return true for successful payment");

    }
}