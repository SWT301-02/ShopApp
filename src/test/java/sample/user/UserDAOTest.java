package sample.user;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import sample.user.UserDAO;
import sample.user.UserDTO;

@Disabled
class UserDAOTest {
    private UserDAO userDAO;

    @BeforeEach
    void setUp() {
        userDAO = new UserDAO();
    }

    @Test
    void testCheckLogin() throws Exception {
        // Unit test for checkLogin method
        String userID = "admin";
        String password = "123456";
        UserDTO loginUser = userDAO.checkLoginv2(userID);
        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), loginUser.getPassword());
        assertTrue(result.verified, "Should return true for correct password");
    }

    @Test
    void testCheckDuplicate() throws Exception {
        // Unit test for checkDuplicate method
        String userID = "admin";
        boolean result = userDAO.checkDublicate(userID);
        assertTrue(result, "Should return true for existing user");
    }

    @Test
    void testInsertAndDelete() throws Exception {
        // Integration test for insert and delete methods
        UserDTO user = new UserDTO("newUser", "New User", "US", "password123", "newuser@example.com");

        boolean inserted = userDAO.insert(user);
        assertTrue(inserted, "User should be inserted successfully");

        boolean deleted = userDAO.deleteUser("newUser");
        assertTrue(deleted, "User should be deleted successfully");
    }
}
