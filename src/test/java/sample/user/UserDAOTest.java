package sample.user;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import sample.repository.UserRepository;
import sample.user.UserDAO;
import sample.user.UserDTO;

import java.util.List;

class UserDAOTest {
    private UserDAO userDAO;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new UserRepository(
                Persistence.createEntityManagerFactory(
                        "JPAs"));
    }

    @Test
    void testCheckLogin() throws Exception {
        UserDTO user = new UserDTO("hoang", "hoang luu", "US", "123", "hoangclw@gmail.com");
        userRepository.saveUser(user);
        // Unit test for checkLogin method
        UserDTO loginUser = userRepository.checkLogin(user.getUserID(), user.getPassword());
        assertNotNull(loginUser, "Should return user object for correct login");
        // Clean up: Delete the user after test
        userRepository.deleteUser(user.getUserID());
    }

    @Test
    void testCheckDuplicate() throws Exception {
        // Unit test for checkDuplicate method
        UserDTO user = new UserDTO("hoang", "hoang luu", "US", "123", "hoangclw@gmail.com");
        userRepository.saveUser(user);
        boolean duplicate = userRepository.checkUserIdDuplicate(user.getUserID());
        assertTrue(duplicate, "Should return true for duplicate user ID");
        // Clean up: Delete the user after test
        userRepository.deleteUser(user.getUserID());
    }

    @Test
    @Order(1)
    void testInsertUser() throws Exception {
        // Unit test for insert method
        UserDTO newUser = new UserDTO("hoang", "hoang luu", "US", "123", "hoangclw@gmail.com");
        userRepository.saveUser(newUser);
        UserDTO insertedUser = userRepository.getUserById(newUser.getUserID());
        assertNotNull(insertedUser, "User should be inserted successfully");
    }

    @Test
    @Order(2)
    void testDeleteUser() throws Exception {
        UserDTO newUser = new UserDTO("hoang", "hoang luu", "US", "123", "hoangclw@gmail.com");
        userRepository.saveUser(newUser);
        UserDTO existingUser = userRepository.getUserById("hoang");
        userRepository.deleteUser(existingUser.getUserID());
        UserDTO deletedUser = userRepository.getUserById(existingUser.getUserID());
        assertNull(deletedUser, "User should be deleted successfully");
    }

    @Test
    @Order(3)
    void testGetUserById() throws Exception {
        UserDTO user = new UserDTO("john", "John Doe", "US", "1234", "john@example.com");
        userRepository.saveUser(user);
        UserDTO retrievedUser = userRepository.getUserById("john");
        assertNotNull(retrievedUser, "Error, testGetUserById failed: User should exist");
        // Clean up: Delete the user after test
        userRepository.deleteUser(user.getUserID());
    }

    @Test
    @Order(4)
    void testUpdateUser() throws Exception {
        UserDTO userToUpdate = new UserDTO("jane", "Jane Doe", "US", "5678", "jane@example.com");
        userRepository.saveUser(userToUpdate);

        // Update user details
        userToUpdate.setPassword("newpassword");
        userRepository.updateUser(userToUpdate);

        UserDTO updatedUser = userRepository.getUserById("jane");
        assertNotNull(updatedUser, "Error, testUpdateUser failed: User should exist");
        assertEquals("newpassword", updatedUser.getPassword(), "Error, testUpdateUser failed: Password not updated");

        // Clean up: Delete the user after test
        userRepository.deleteUser(userToUpdate.getUserID());
    }

    @Test
    @Order(5)
    void testDeleteNonExistentUser() throws Exception {
        boolean isDeleted = userRepository.deleteUser("nonexistent");
        assertFalse(isDeleted, "Error, testDeleteNonExistentUser failed: Should not delete a non-existent user");
    }

    @Test
    @Order(6)
    void testCheckDuplicateUserId() throws Exception {
        UserDTO user = new UserDTO("duplicateUser", "Duplicate User", "US", "1234", "duplicate@example.com");
        userRepository.saveUser(user);

        boolean isDuplicate = userRepository.checkUserIdDuplicate("duplicateUser");
        assertTrue(isDuplicate, "Error, testCheckDuplicateUserId failed: User ID should be marked as duplicate");

        // Clean up: Delete the user after test
        userRepository.deleteUser(user.getUserID());
    }

    @Test
    @Order(7)
    void testInsertDuplicateUser() throws Exception {
        UserDTO user = new UserDTO("duplicateUser", "Duplicate User", "US", "1234", "duplicate@example.com");
        userRepository.saveUser(user);
        UserDTO duplicateUser = new UserDTO("duplicateUser", "Another Duplicate User", "US", "5678",
                "anotherduplicate@example.com");

        Exception exception = assertThrows(Exception.class, () -> {
            userRepository.saveUser(duplicateUser);
        });
        assertTrue(exception.getMessage().contains("already exists"),
                "Error, testInsertDuplicateUser failed: Should throw a duplicate exception");

        // Clean up: Delete the user after test
        userRepository.deleteUser(user.getUserID());
    }

    @Test
    @Order(8)
    void testGetAllUsersEmpty() throws Exception {
        // Clear all users for this test
        userRepository.executeUpdate("DELETE FROM UserDTO");
        List<UserDTO> users = userRepository.getAllUsers();
        assertTrue(users.isEmpty(), "Error, testGetAllUsersEmpty failed: User list should be empty");
    }

    @Test
    @Order(9)
    void testUpdateNonExistentUser() throws Exception {
        UserDTO nonExistentUser = new UserDTO("nonexistent", "Non-Existent User", "US", "0000",
                "nonexistent@example.com");
        Exception exception = assertThrows(Exception.class, () -> {
            userRepository.updateUser(nonExistentUser);
        });
        assertTrue(exception.getMessage().contains("not exist"),
                "Error, testUpdateNonExistentUser failed: Should throw an exception");
    }

    @Test
    @Order(10)
    void testInsertUserWithNullValues() throws Exception {
        UserDTO userWithNulls = new UserDTO(null, null, null, null, null);
        Exception exception = assertThrows(Exception.class, () -> {
            userRepository.saveUser(userWithNulls);
        });
        assertTrue(exception.getMessage().contains("not valid"),
                "Error, testInsertUserWithNullValues failed: Should throw an exception for null values");
    }

}
