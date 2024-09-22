package sample.user;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
//        BCrypt.Result result = BCrypt.verifyer().verify(user.getPassword().toCharArray(), loginUser.getPassword());
//        assertTrue(result.verified, "Should return true for correct password");
        assertNotNull(loginUser, "Should return user object for correct login");
    }

//    @Disabled
//    @Test
//    void testCheckDuplicate() throws Exception {
//        // Unit test for checkDuplicate method
//        String userID = "admin";
//        boolean result = userDAO.checkDublicate(userID);
//        assertTrue(result, "Should return true for existing user");
//    }
//
//    @Disabled
//    @Test
//    void testInsertAndDelete() throws Exception {
//        // Integration test for insert and delete methods
//        UserDTO user = new UserDTO("newUser", "New User", "US", "password123", "newuser@example.com");
//
//        boolean inserted = userDAO.insert(user);
//        assertTrue(inserted, "User should be inserted successfully");
//
//        boolean deleted = userDAO.deleteUser("newUser");
//        assertTrue(deleted, "User should be deleted successfully");
//    }
}
