package sample.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import sample.shopping.OrderDetailDTO;

import java.util.List;


public class OrderDetailRepository {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPAs");

    public void saveOrderDetail(OrderDetailDTO orderDetail) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(orderDetail); // Save the order detail
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

    public void updateOrderDetail(OrderDetailDTO orderDetail) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(orderDetail); // Update the order detail
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

    public void deleteOrderDetail(int id) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            OrderDetailDTO orderDetail = em.find(OrderDetailDTO.class, id);
            em.remove(orderDetail); // Delete the order detail
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

    public OrderDetailDTO getOrderDetail(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(OrderDetailDTO.class, id); // Retrieve the order detail
        } finally {
            em.close();
        }
    }

    public List<OrderDetailDTO> getOrderDetailsByOrderID(int orderID) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT od FROM OrderDetailDTO od WHERE od.orderID = :orderID", OrderDetailDTO.class)
                    .setParameter("orderID", orderID)
                    .getResultList(); // Retrieve the order details by order ID
        } finally {
            em.close();
        }
    }
}