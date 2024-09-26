package sample.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import sample.model.Order;
import sample.shopping.OrderDTO;

import java.util.List;

@RequiredArgsConstructor
 @AllArgsConstructor
public class OrderRepository {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPAs");

    public int saveOrder(Order order) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(order); // Save the order
            transaction.commit();
            return order.getOrderID(); // Return the generated order ID
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public OrderDTO getOrder(int orderID) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(OrderDTO.class, orderID); // Retrieve the order
        } finally {
            em.close();
        }
    }

    public void updateOrder(OrderDTO order) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(order); // Update the order
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public void deleteOrder(int orderID) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            OrderDTO order = em.find(OrderDTO.class, orderID);
            em.remove(order); // Delete the order
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public List<OrderDTO> getAllOrders() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT o FROM OrderDTO o", OrderDTO.class).getResultList(); // Retrieve all orders
        } finally {
            em.close();
        }
    }
}