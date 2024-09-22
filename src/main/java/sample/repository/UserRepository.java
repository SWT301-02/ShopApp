package sample.repository;

import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.AllArgsConstructor;

import java.util.List;
import sample.user.UserDTO;

@AllArgsConstructor
public class UserRepository {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory(
        "JPAs");

    public void saveUser(UserDTO user) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(user);
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

    public void deleteUser(int userId) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            // Find the entity by its ID to ensure it's managed
            UserDTO user = em.find(UserDTO.class, userId);
            if (user != null) {
                em.remove(user);
            }

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


    public void updateUser(UserDTO user) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(user);
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

    public int getTotalUsers() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                    "SELECT COUNT(u) FROM UserDTO u", Long.class)
                .getSingleResult().intValue();
        } finally {
            em.close();
        }
    }

    // getSingleResult() throws NoResultException if no result is found
    // can use getResultList() instead to return an empty list
    public UserDTO checkLogin(String userID, String password) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                    "SELECT u FROM UserDTO u WHERE (u.userID = :userID) AND u.password = "
                        + ":password",
                    UserDTO.class)
                .setParameter("userID", userID)
                .setParameter("password", password)
                .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public UserDTO getUserById(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(UserDTO.class, id);
        } finally {
            em.close();
        }
    }

    public UserDTO getUserByUsername(String username) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                    "SELECT u FROM UserDTO u WHERE u.fullName = :username",
                    UserDTO.class)
                .setParameter("username", username)
                .getSingleResult();
        } finally {
            em.close();
        }
    }

    public UserDTO getUserByEmail(String email) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                    "SELECT u FROM UserDTO u WHERE u.email = :email", UserDTO.class)
                .setParameter("email", email)
                .getSingleResult();
        } finally {
            em.close();
        }
    }

    public boolean checkUserIdDuplicate(String userID) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                    "SELECT COUNT(u) FROM UserDTO u WHERE u.userID = :userID",
                    Long.class)
                .setParameter("userID", userID)
                .getSingleResult() > 0;
        } finally {
            em.close();
        }
    }

    public boolean updatePasswordUser(UserDTO user, String newPassword) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            user.setPassword(newPassword);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public List<UserDTO> getData() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                    "SELECT u FROM UserDTO u ORDER BY u.roleID ASC", UserDTO.class)
                .getResultList();
        } finally {
            em.close();
        }
    }

    public static void main(String[] args) {
//        EntityManagerFactory emf = MyEntityManager.getEntityManagerFactory();
        try {
            UserRepository userRepository = new UserRepository(
                Persistence.createEntityManagerFactory(
                    "JPAs"));

            if (userRepository == null) {
                throw new Exception("UserRepository is null");
            } else {

//        UserDTO user = new UserDTO();
                UserDTO user = new UserDTO("hoang", "luu", "tester@gmail.com", "123");
        userRepository.saveUser(user);
//        userRepository.saveUser(user);
//        userRepository.deleteUser(user);
//        userRepository.updateUser(user);
//        userRepository.getTotalUsers();
                System.out.println(userRepository.checkLogin("hoang", "123"));
//        userRepository.getUserById(1);
//                System.out.println(userRepository.getUserByUsername("admin"));
                for (UserDTO u : userRepository.getData()) {
                    System.out.println(u);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }

}
