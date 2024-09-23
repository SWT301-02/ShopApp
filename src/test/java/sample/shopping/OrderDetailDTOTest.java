package sample.shopping;

import org.junit.jupiter.api.Test;

import jakarta.persistence.Persistence;
import sample.repository.OrderDetailRepository;
import sample.repository.OrderRepository;
import sample.repository.ProductRepository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;

class OrderDetailDTOTest {
    private OrderDetailRepository orderDetailRepository = new OrderDetailRepository(
            Persistence.createEntityManagerFactory("JPAs"));
    private OrderRepository orderRepository = new OrderRepository(Persistence.createEntityManagerFactory("JPAs"));
    private ProductRepository productRepository = new ProductRepository(Persistence.createEntityManagerFactory("JPAs"));

    private OrderDTO pseudoOrder;

    @BeforeEach
    void setUp() {
        // Create a pseudo order for testing
        pseudoOrder = new OrderDTO(1, "user1", 100.0, new java.util.Date());
    }

    @Test
    @Order(1)
    void testOrderDetailDTOFields() {
        OrderDetailDTO orderDetail = new OrderDetailDTO(1, "P001", 100.0, 2, pseudoOrder);
        assertEquals(1, orderDetail.getId());
        assertEquals("P001", orderDetail.getProductID());
        assertEquals(100.0, orderDetail.getPrice());
        assertEquals(2, orderDetail.getQuantity());
        assertEquals(pseudoOrder, orderDetail.getOrder());
    }

    @Test
    @Order(2)
    void testOrderDetailDTODefaultConstructor() {
        OrderDetailDTO orderDetail = new OrderDetailDTO();
        assertEquals(0, orderDetail.getId());
        assertNull(orderDetail.getProductID());
        assertEquals(0.0, orderDetail.getPrice());
        assertEquals(0, orderDetail.getQuantity());
        assertNull(orderDetail.getOrder());
    }

    @Test
    @Order(3)
    void testOrderDetailDTOConstructorWithNullValues() {
        OrderDetailDTO orderDetail = new OrderDetailDTO();
        assertNull(orderDetail.getProductID());
        assertEquals(0.0, orderDetail.getPrice());
        assertEquals(0, orderDetail.getQuantity());
        assertNull(orderDetail.getOrder());
    }

    @Test
    @Order(4)
    void testOrderDetailDTOConstructorWithEmptyValues() {
        OrderDetailDTO orderDetail = new OrderDetailDTO(0, "", 0.0, 0, null);
        assertEquals(0, orderDetail.getId());
        assertEquals("", orderDetail.getProductID());
        assertEquals(0.0, orderDetail.getPrice());
        assertEquals(0, orderDetail.getQuantity());
        assertNull(orderDetail.getOrder());
    }

}