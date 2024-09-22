package sample.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserDTOTest {

    @Test
    void testUserDTOConstructor() {
        UserDTO user = new UserDTO("user1", "User One", "US", "password", "user1@example.com");
        assertEquals("user1", user.getUserID());
        assertEquals("User One", user.getFullName());
        assertEquals("US", user.getRoleID());
        assertEquals("password", user.getPassword());
        assertEquals("user1@example.com", user.getEmail());
    }

    @Test
    void testSettersAndGetters() {
        UserDTO user = new UserDTO();
        user.setUserID("user2");
        user.setFullName("User Two");
        user.setRoleID("US");
        user.setPassword("newpassword");
        user.setEmail("user2@example.com");

        assertEquals("user2", user.getUserID());
        assertEquals("User Two", user.getFullName());
        assertEquals("US", user.getRoleID());
        assertEquals("newpassword", user.getPassword());
        assertEquals("user2@example.com", user.getEmail());
    }

    @Test
    void testToString() {
        UserDTO user = new UserDTO("user3", "User Three", "US", "password", "user3@example.com");
        String expectedString = "UserDTO(userID=user3, fullName=User Three, roleID=US, password=password, email=user3@example.com)";
        assertEquals(expectedString, user.toString());
    }

    @Test
    void testUserDTOConstructorWithNullValues() {
        UserDTO user = new UserDTO(null, null, null, null, null);
        assertNull(user.getUserID());
        assertNull(user.getFullName());
        assertNull(user.getRoleID());
        assertNull(user.getPassword());
        assertNull(user.getEmail());
    }

    @Test
    void testSetPasswordToNull() {
        UserDTO user = new UserDTO("user4", "User Four", "US", "password", "user4@example.com");
        user.setPassword(null);
        assertNull(user.getPassword());
    }

    @Test
    void testUpdateEmail() {
        UserDTO user = new UserDTO("user5", "User Five", "US", "password", "user5@example.com");
        user.setEmail("newemail@example.com");
        assertEquals("newemail@example.com", user.getEmail());
    }

    @Test
    void testSetPasswordToEmpty() {
        UserDTO user = new UserDTO("user6", "User Six", "US", "password", "user6@example.com");
        user.setPassword("");
        assertEquals("", user.getPassword());
    }

    @Test
    void testSpecialCharacters() {
        UserDTO user = new UserDTO("user7", "User@Seven!", "US$", "p@ssw0rd!", "user7@example.com");
        assertEquals("user7", user.getUserID());
        assertEquals("User@Seven!", user.getFullName());
        assertEquals("US$", user.getRoleID());
        assertEquals("p@ssw0rd!", user.getPassword());
        assertEquals("user7@example.com", user.getEmail());
    }

    @Test
    void testUserDTOConstructorWithEmptyValues() {
        UserDTO user = new UserDTO("", "", "", "", "");
        assertEquals("", user.getUserID());
        assertEquals("", user.getFullName());
        assertEquals("", user.getRoleID());
        assertEquals("", user.getPassword());
        assertEquals("", user.getEmail());
    }
}
