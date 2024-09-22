package sample.repository;

import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.AllArgsConstructor;

import java.util.List;

import sample.shopping.ProductDTO;

@AllArgsConstructor
public class ProductRepository {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPAs");

    public void saveProduct(ProductDTO product) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            if (product.toString().contains("null")) {
                transaction.rollback();
                throw new IllegalArgumentException("Product data not valid");
            } else {
                ProductDTO existingProduct = em.find(ProductDTO.class, product.getProductID());
                if (existingProduct == null) {
                    em.merge(product);
                    transaction.commit();
                } else {
                    transaction.rollback();
                    throw new IllegalArgumentException("Product already exists");
                }
            }
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public boolean deleteProduct(String productId) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            ProductDTO product = em.find(ProductDTO.class, productId);
            if (product != null) {
                em.remove(product);
                transaction.commit();
                return true;
            } else {
                transaction.rollback();
                return false;
            }
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public void updateProduct(ProductDTO product) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            ProductDTO existingProduct = em.find(ProductDTO.class, product.getProductID());
            if (existingProduct != null) {
                if (product.getPrice() <= 0) {
                    transaction.rollback();
                    throw new IllegalArgumentException("Price data not valid");
                } else {
                    em.merge(product);
                    transaction.commit();
                }
            } else {
                transaction.rollback();
                throw new IllegalArgumentException("Product does not exist");
            }
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public ProductDTO getProductById(String id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(ProductDTO.class, id);
        } finally {
            em.close();
        }
    }

    public List<ProductDTO> getAllProducts() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT p FROM ProductDTO p", ProductDTO.class).getResultList();
        } finally {
            em.close();
        }
    }

    public boolean checkProductIdDuplicate(String productId) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT COUNT(p) FROM ProductDTO p WHERE p.productID = :productId", Long.class)
                    .setParameter("productId", productId)
                    .getSingleResult() > 0;
        } finally {
            em.close();
        }
    }

    public void executeUpdate(String sql) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNativeQuery(sql).executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e; // Rethrow the exception after rollback
        } finally {
            em.close();
        }
    }
}
