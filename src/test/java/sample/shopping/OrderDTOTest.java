package sample.shopping;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;
import static org.junit.jupiter.api.Assertions.*;

class OrderDTOTest {

    @Test
    @Order(1)
    void testOrderDTOConstructor() {
        OrderDTO order = new OrderDTO(1, "user1", 100.0, new java.util.Date());
        assertEquals(1, order.getOrderID());
        assertEquals("user1", order.getUserID());
        assertEquals(100.0, order.getTotal());
        assertNotNull(order.getDate());
    }

    @Test
    @Order(2)
    void testSettersAndGetters() {
        OrderDTO order = new OrderDTO();
        order.setOrderID(2);
        order.setUserID("user2");
        order.setTotal(200.0);
        order.setDate(new java.util.Date());

        assertEquals(2, order.getOrderID());
        assertEquals("user2", order.getUserID());
        assertEquals(200.0, order.getTotal());
        assertNotNull(order.getDate());
    }

    @Test
    @Order(3)
    void testToString() {
        OrderDTO order = new OrderDTO(1, "user1", 100.0, new java.util.Date());
        String expectedString = "OrderDTO(orderID=1, userID=user1, total=100.0, date=" + order.getDate() + ")";
        assertEquals(expectedString, order.toString());
    }

    @Test
    @Order(4)
    void testOrderDTOConstructorWithNullValues() {
        OrderDTO order = new OrderDTO();
        assertNull(order.getUserID());
        assertEquals(0, order.getTotal());
        assertNull(order.getDate());
    }

    @Test
    @Order(5)
    void testOrderDTOConstructorWithEmptyValues() {
        OrderDTO order = new OrderDTO(0, "", 0.0, null);
        assertEquals(0, order.getOrderID());
        assertEquals("", order.getUserID());
        assertEquals(0.0, order.getTotal());
        assertNull(order.getDate());
    }

    @Test
    @Order(6)
    void testOrderDTOFields() {
        OrderDTO order = new OrderDTO(1, "user1", 100.0, new java.util.Date());
        assertEquals(1, order.getOrderID());
        assertEquals("user1", order.getUserID());
        assertEquals(100.0, order.getTotal());
        assertNotNull(order.getDate());
    }

    @Test
    @Order(7)
    void testOrderDTODefaultConstructor() {
        OrderDTO order = new OrderDTO();
        assertNull(order.getUserID());
        assertEquals(0, order.getTotal());
        assertNull(order.getDate());
    }
}