package sample.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DBUtilsTest {

    DBUtils dbUtils = null;
    String dbName = "UserManagement";
    String user = "sa";
    // String password = "Luucaohoang1604^^";
    String password = "123455";

    @BeforeEach
    public void setup() throws Exception {
        dbUtils = new DBUtils();
    }

    @Test
    void getConnection() {
        try {
            assertNotNull(dbUtils.getConnection(dbName, user, password));
        } catch (Exception e) {
            fail("Error, DB configuration false: " + e.getMessage());
        }
    }
}