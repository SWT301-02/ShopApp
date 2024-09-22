package sample.service;

import sample.repository.OrderDetailRepository;
import sample.repository.OrderRepository;
import sample.shopping.OrderDTO;
import sample.shopping.OrderDetailDTO;

import java.util.List;

public class OrderService {
    private OrderRepository orderRepository = new OrderRepository();
    private OrderDetailRepository orderDetailRepository = new OrderDetailRepository();

    public void saveOrderWithDetails(OrderDTO order, List<OrderDetailDTO> orderDetails) {
        int orderId = orderRepository.saveOrder(order); // Save the order
        for (OrderDetailDTO detail : orderDetails) {
            detail.setOrderID(orderId); // Set the order ID for each detail
            orderDetailRepository.saveOrderDetail(detail); // Save each order detail
        }
    }
}