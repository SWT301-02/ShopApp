/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.shopping;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import sample.utils.DBUtils;

/**
 *
 * @author lmao
 */
public class CartDAO {

    private static final String INSERT_ORDER = "INSERT INTO tblOrders (orderID, userID, total, date) VALUES (?, ?, ?, GETDATE())";
    private static final String CHECK_ID = "SELECT OrderID FROM tblOrders WHERE OrderID = ?";
    private static final String INSERT_ORDER_DETAIL = "INSERT INTO tblOrderDetail (orderID, productID, price, quantity) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_PRODUCT_QUANTITY = "UPDATE tblProducts SET quantity = quantity - ? WHERE productID = ?";

    public boolean saveOrder(int orderID, String userID, double total) throws SQLException, ClassNotFoundException {
        boolean result = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(INSERT_ORDER);
                ptm.setInt(1, orderID);
                ptm.setString(2, userID);
                ptm.setDouble(3, total);
                result = ptm.executeUpdate() > 0;
            }
        } catch (Exception e) {
            System.out.println("Error ar save Order: " + e.getMessage());
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return result;
    }

    public boolean checkDuplicateID(int orderID) throws SQLException, ClassNotFoundException {
        boolean result = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(CHECK_ID);
                ptm.setInt(1, orderID);
                result = ptm.executeUpdate() > 0;
            }
        } catch (Exception e) {
            System.out.println("Error ar save Order: " + e.getMessage());
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return result;
    }

    public boolean saveOrderDetail(int orderID, ProductDTO product) throws SQLException, ClassNotFoundException {
        boolean result = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(INSERT_ORDER_DETAIL);
                ptm.setInt(1, orderID);
                ptm.setString(2, product.getProductID());
                ptm.setDouble(3, product.getPrice());
                ptm.setInt(4, product.getQuantity());
                result = ptm.executeUpdate() > 0;
            }
        } catch (Exception e) {
            System.out.println("Error at Save Order Detail: " + e.getMessage());
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return result;
    }

    public boolean updateProductQuantity(String productID, int quantity) throws SQLException, ClassNotFoundException {
        boolean result = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(UPDATE_PRODUCT_QUANTITY);
                ptm.setInt(1, quantity);
                ptm.setString(2, productID);
                result = ptm.executeUpdate() > 0;
            }
        } catch (Exception e) {
            System.out.println("Error at Update Quantity: " + e.getMessage());
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return result;
    }

}
