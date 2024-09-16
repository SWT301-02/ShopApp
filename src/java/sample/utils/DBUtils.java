/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author hd
 */
public class DBUtils {

    private static final String DB_NAME = "UserManagement";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "12345";

    public static final Connection getConnection() throws ClassNotFoundException, SQLException {
        Connection conn = null;
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String url = "jdbc:sqlserver://localhost:1433;databaseName=" + DB_NAME;
        conn = DriverManager.getConnection(url, DB_USER, DB_PASSWORD);
        return conn;
    }


    public static void main(String[] args) throws SQLException, ClassNotFoundException, NamingException {
        Connection conn = getConnection();
        System.out.println(conn);
    }
}
