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

@Disabled
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

//    @Disabled
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
        // Unit test for delete method
        //use the same user as in testInsertUser
        UserDTO existingUser = userRepository.getUserById("hoang");
        userRepository.deleteUser(existingUser.getUserID());
        UserDTO deletedUser = userRepository.getUserById(existingUser.getUserID());
        assertNull(deletedUser, "User should be deleted successfully");
    }
}
