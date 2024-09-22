/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.naming.NamingException;

/**
 *
 * @author hd
 */
public class DBUtils {

    public static final String DB_NAME = "UserManagement";
    public static final String DB_ROOT_USER = "sa";
    public static final String DB_CI_USER = "dev";
    public static final String DB_PASSWORD = "12345";
    public static final String DOCKER_DB_PASSWORD = "Luucaohoang1604^^";
    public static final String DOCKER_PORT = "1434";
    public static final String CI_PORT = "1435";

    //Connect to docker container
    public static final Connection getConnection(String dockerPort, String user, String password) throws ClassNotFoundException,
        SQLException {
        Connection conn = null;
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String url = String.format("jdbc:sqlserver://localhost:%s;databaseName=master", dockerPort);
        conn = DriverManager.getConnection(url, user, password);
        return conn;
    }

    public static final Connection getConnection() throws ClassNotFoundException,
        SQLException {
        Connection conn = null;
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String url = "jdbc:sqlserver://localhost:1433;databaseName=" + DB_NAME;
        conn = DriverManager.getConnection(url, DB_ROOT_USER, DB_PASSWORD);
        return conn;
    }


    public static void main(String[] args) throws SQLException, ClassNotFoundException, NamingException {
        Connection conn = getConnection();
        System.out.println(conn);
    }
}
