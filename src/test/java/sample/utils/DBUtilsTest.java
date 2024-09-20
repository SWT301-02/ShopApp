package sample.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DBUtilsTest {

    DBUtils dbUtils = null;

    @BeforeEach
    public void setup() throws Exception {
        dbUtils = new DBUtils();
    }

    @Test
    void getConnection() {
        try {
            assertNotNull(dbUtils.getConnection("1433", DBUtils.DB_ROOT_USER,
                                                DBUtils.DOCKER_DB_PASSWORD));
        } catch (Exception e) {
            fail("Error, DB configuration false: " + e.getMessage());
        }
    }
}