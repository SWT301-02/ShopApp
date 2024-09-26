package sample.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import sample.shopping.OrderDTO;
import sample.model.Order;
import sample.shopping.OrderDetailDTO;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//@Disabled
class OrderIntegrationTest {

    private final OrderRepository orderRepository = new OrderRepository();
    private final OrderDetailRepository orderDetailRepository = new OrderDetailRepository();
    private final UserRepository userRepository = new UserRepository();

    @BeforeEach
    public void setUp() {
        // setup fake user
        userRepository.saveUser(
                new sample.user.UserDTO("user123", "user123", "US", "123", "user@gmail.com"));
        Order order = new Order();
        order.setUserID("user123");
        order.setTotal(100.0);
        order.setDate(new Date());

        OrderDetailDTO orderDetail1 = new OrderDetailDTO();
        orderDetail1.setProductID("prod1");
        orderDetail1.setPrice(50.0);
        orderDetail1.setQuantity(1);

        OrderDetailDTO orderDetail2 = new OrderDetailDTO();
        orderDetail2.setProductID("prod2");
        orderDetail2.setPrice(25.0);
        orderDetail2.setQuantity(2);

        int orderId = orderRepository.saveOrder(order);
        orderDetail1.setOrder(new OrderDTO(orderId, order.getUserID(), order.getTotal(), order.getDate()));
        orderDetail2.setOrder(new OrderDTO(orderId, order.getUserID(), order.getTotal(), order.getDate()));
        orderDetailRepository.saveOrderDetail(orderDetail1);
        orderDetailRepository.saveOrderDetail(orderDetail2);
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    void testSaveOrderAndOrderDetails() {
        // Arrange
        Order order = new Order();
        order.setUserID("user123");
        order.setTotal(100.0);
        order.setDate(new Date());

        OrderDetailDTO orderDetail1 = new OrderDetailDTO();
        orderDetail1.setProductID("prod1");
        orderDetail1.setPrice(50.0);
        orderDetail1.setQuantity(1);

        OrderDetailDTO orderDetail2 = new OrderDetailDTO();
        orderDetail2.setProductID("prod2");
        orderDetail2.setPrice(25.0);
        orderDetail2.setQuantity(2);

        // Act
        int orderId = orderRepository.saveOrder(order);
        orderDetail1.setOrder(new OrderDTO(orderId, order.getUserID(), order.getTotal(), order.getDate()));
        orderDetail2.setOrder(new OrderDTO(orderId, order.getUserID(), order.getTotal(), order.getDate()));
        orderDetailRepository.saveOrderDetail(orderDetail1);
        orderDetailRepository.saveOrderDetail(orderDetail2);

        // Assert
        assertNotNull(orderId);
        assertNotNull(orderDetailRepository.getOrderDetailsByOrderID(orderId));
        assertNotNull(orderRepository.getOrder(orderId));
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    void testGetOrderWithDetails() {
        List<OrderDetailDTO> orderDetails = orderDetailRepository.getOrderDetailsByOrderID(1);
        OrderDTO order = orderRepository.getOrder(1);
        for (OrderDetailDTO orderDetail : orderDetails) {
            assertEquals(order.getOrderID(), orderDetail.getOrder().getOrderID());
            assertNotNull(orderDetail.getProductID());
            assertNotNull(orderDetail.getPrice());
            assertNotNull(orderDetail.getQuantity());
        }
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    void testUpdateOrderAndOrderDetails() {
        OrderDTO order = orderRepository.getOrder(1);
        order.setTotal(200.0);

        OrderDetailDTO orderDetail = orderDetailRepository.getOrderDetail(1);
        orderDetail.setPrice(100.0);
        orderDetail.setQuantity(2);

        orderRepository.updateOrder(order);
        orderDetailRepository.updateOrderDetail(orderDetail);

        assertEquals(200.0, orderRepository.getOrder(1).getTotal());
        assertEquals(100.0, orderDetailRepository.getOrderDetail(1).getPrice());
        assertEquals(2, orderDetailRepository.getOrderDetail(1).getQuantity());

    }

    @Test
    @org.junit.jupiter.api.Order(4)
    void testDeleteOrderAndOrderDetails() {
        orderRepository.deleteOrder(1);
        assertNull(orderRepository.getOrder(1));
        assertNull(orderDetailRepository.getOrderDetail(1));

    }
}