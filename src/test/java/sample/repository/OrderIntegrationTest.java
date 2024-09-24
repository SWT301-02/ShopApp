package sample.repository;

import jakarta.persistence.Persistence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sample.shopping.OrderDTO;
import sample.model.Order;
import sample.shopping.OrderDetailDTO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Disabled
class OrderIntegrationTest {

    @Mock
    private EntityManagerFactory emf;
    @Mock
    private EntityManager em;
    @Mock
    private EntityTransaction transaction;

    private OrderRepository orderRepository;
    private OrderDetailRepository orderDetailRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
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
        verify(em, times(3)).persist(any());
        verify(transaction, times(3)).begin();
        verify(transaction, times(3)).commit();
    }

    @Test
    void testGetOrderWithDetails() {
        // Arrange
        int orderId = 1;
        OrderDTO orderDTO = new OrderDTO(orderId, "user123", 100.0, new Date());
        List<OrderDetailDTO> orderDetails = Arrays.asList(
                new OrderDetailDTO(1, "prod1", 50.0, 1, orderDTO),
                new OrderDetailDTO(2, "prod2", 25.0, 2, orderDTO));

        when(em.find(OrderDTO.class, orderId)).thenReturn(orderDTO);
        when(em.createQuery(anyString(), eq(OrderDetailDTO.class)))
                .thenReturn(mock(jakarta.persistence.TypedQuery.class));
        when(em.createQuery(anyString(), eq(OrderDetailDTO.class)).setParameter(anyString(), any()))
                .thenReturn(mock(jakarta.persistence.TypedQuery.class));
        when(em.createQuery(anyString(), eq(OrderDetailDTO.class)).setParameter(anyString(), any()).getResultList())
                .thenReturn(orderDetails);

        // Act
        OrderDTO retrievedOrder = orderRepository.getOrder(orderId);
        List<OrderDetailDTO> retrievedOrderDetails = orderDetailRepository.getOrderDetailsByOrderID(orderId);

        // Assert
        assertNotNull(retrievedOrder);
        assertEquals(orderId, retrievedOrder.getOrderID());
        assertEquals(2, retrievedOrderDetails.size());
        assertEquals(orderDTO, retrievedOrderDetails.get(0).getOrder());
        assertEquals(orderDTO, retrievedOrderDetails.get(1).getOrder());
    }

    @Test
    void testUpdateOrderAndOrderDetails() {
        // Arrange
        int orderId = 1;
        OrderDTO orderDTO = new OrderDTO(orderId, "user123", 150.0, new Date());
        OrderDetailDTO orderDetail = new OrderDetailDTO(1, "prod1", 75.0, 2, orderDTO);

        when(em.merge(any())).thenReturn(null);
        when(transaction.isActive()).thenReturn(true);

        // Act
        orderRepository.updateOrder(orderDTO);
        orderDetailRepository.updateOrderDetail(orderDetail);

        // Assert
        verify(em, times(2)).merge(any());
        verify(transaction, times(2)).begin();
        verify(transaction, times(2)).commit();
    }

    @Test
    void testDeleteOrderAndOrderDetails() {
        // Arrange
        int orderId = 1;
        OrderDTO orderDTO = new OrderDTO(orderId, "user123", 100.0, new Date());
        List<OrderDetailDTO> orderDetails = Arrays.asList(
                new OrderDetailDTO(1, "prod1", 50.0, 1, orderDTO),
                new OrderDetailDTO(2, "prod2", 25.0, 2, orderDTO));

        when(em.find(OrderDTO.class, orderId)).thenReturn(orderDTO);
        when(em.find(OrderDetailDTO.class, 1)).thenReturn(orderDetails.get(0));
        when(em.find(OrderDetailDTO.class, 2)).thenReturn(orderDetails.get(1));
        when(em.createQuery(anyString(), eq(OrderDetailDTO.class)))
                .thenReturn(mock(jakarta.persistence.TypedQuery.class));
        when(em.createQuery(anyString(), eq(OrderDetailDTO.class)).setParameter(anyString(), any()))
                .thenReturn(mock(jakarta.persistence.TypedQuery.class));
        when(em.createQuery(anyString(), eq(OrderDetailDTO.class)).setParameter(anyString(), any()).getResultList())
                .thenReturn(orderDetails);

        // Act
        List<OrderDetailDTO> retrievedOrderDetails = orderDetailRepository.getOrderDetailsByOrderID(orderId);
        for (OrderDetailDTO detail : retrievedOrderDetails) {
            orderDetailRepository.deleteOrderDetail(detail.getId());
        }
        orderRepository.deleteOrder(orderId);

        // Assert
        verify(em, times(3)).remove(any());
        verify(transaction, times(3)).begin();
        verify(transaction, times(3)).commit();
    }
}