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
import java.util.ArrayList;
import java.util.List;

import sample.utils.DBUtils;

/**
 * @author lmao
 */
public class ProductDAO {

    private static final String SEARCH = "SELECT * FROM UserManagement.[dbo].[tblProducts]";
    private static final String SEARCH_NAME = "SELECT * FROM UserManagement.[dbo].[tblProducts] WHERE (productName like ?)";
    private static final String SEARCH_ID = "SELECT * FROM UserManagement.[dbo].[tblProducts] WHERE (productID = ?)";
    private static final String DELETE = "DELETE UserManagement.[dbo].[tblProducts] WHERE (productID = ?)";
    private static final String UPDATE = "UPDATE UserManagement.[dbo].[tblProducts] SET  price = ?, quantity = ? WHERE productID = ?";
    private static final String CHECK_DUPLICATE = "SELECT productID FROM UserManagement.[dbo].[tblProducts] WHERE productID = ?  ";
    private static final String INSERT = "INSERT INTO tblProducts (productID, productName, price, quantity, status, imageUrl) VALUES(?,?,?,?,?,?)";
    private static final String CHECK_QUANTITY = "SELECT quantity FROM UserManagement.[dbo].[tblProducts] WHERE productID = ?";

    @Deprecated
    public List<ProductDTO> getListProducts(String search) throws SQLException {
        List<ProductDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(SEARCH_NAME);
                ptm.setString(1, "%" + search + "%");
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String productID = rs.getString("productID");
                    String productName = rs.getString("productName");
                    float price = rs.getFloat("price");
                    int quantity = rs.getInt("quantity");
                    int status = rs.getInt("status");
                    String imageUrl = rs.getString("imageUrl");
                    list.add(new ProductDTO(productID, productName, price, quantity, imageUrl));
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
        return list;
    }

    // run with docker container
    public List<ProductDTO> getListProductsV2(String search) throws SQLException {
        List<ProductDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection(DBUtils.DOCKER_PORT, DBUtils.DB_USER, DBUtils.DOCKER_DB_PASSWORD);
            if (conn != null) {
                ptm = conn.prepareStatement(SEARCH_NAME);
                ptm.setString(1, "%" + search + "%");
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String productID = rs.getString("productID");
                    String productName = rs.getString("productName");
                    float price = rs.getFloat("price");
                    int quantity = rs.getInt("quantity");
                    int status = rs.getInt("status");
                    String imageUrl = rs.getString("imageUrl");
                    list.add(new ProductDTO(productID, productName, price, quantity, imageUrl));
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
        return list;
    }

    @Deprecated
    public List<ProductDTO> getListProducts(int min, int max) throws SQLException {
        List<ProductDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            try {
                conn = DBUtils.getConnection();
                if (conn != null) {
                    ptm = conn.prepareStatement(SEARCH);
                    rs = ptm.executeQuery();
                    while (rs.next()) {
                        String productID = rs.getString("productID");
                        String productName = rs.getString("productName");
                        float price = rs.getFloat("price");
                        int quantity = rs.getInt("quantity");
                        int status = rs.getInt("status");
                        String imageUrl = rs.getString("imageUrl");
                        if (price >= min && price <= max) {
                            list.add(new ProductDTO(productID, productName, price, quantity, imageUrl));
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
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
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public List<ProductDTO> getListProductsV2(int min, int max) throws SQLException {
        List<ProductDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            try {
                conn = DBUtils.getConnection(DBUtils.DOCKER_PORT, DBUtils.DB_USER, DBUtils.DOCKER_DB_PASSWORD);
                if (conn != null) {
                    ptm = conn.prepareStatement(SEARCH);
                    rs = ptm.executeQuery();
                    while (rs.next()) {
                        String productID = rs.getString("productID");
                        String productName = rs.getString("productName");
                        float price = rs.getFloat("price");
                        int quantity = rs.getInt("quantity");
                        int status = rs.getInt("status");
                        String imageUrl = rs.getString("imageUrl");
                        if (price >= min && price <= max) {
                            list.add(new ProductDTO(productID, productName, price, quantity, imageUrl));
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
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
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }


    public boolean delete(String productID) throws SQLException {
        boolean checkDelete = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBUtils.getConnection(DBUtils.DOCKER_PORT, DBUtils.DB_USER, DBUtils.DOCKER_DB_PASSWORD);
            if (conn != null) {
                ptm = conn.prepareStatement(DELETE);
                ptm.setString(1, productID);
                checkDelete = ptm.executeUpdate() > 0;
            }
        } catch (Exception e) {
            // Log the exception
            System.out.println(e.getMessage());
        } finally {
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return checkDelete;
    }

    public boolean update(ProductDTO product) throws SQLException {
        boolean checkUpdate = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(UPDATE);
                ptm.setString(1, product.getProductName());
                ptm.setFloat(2, product.getPrice());
                ptm.setInt(3, product.getQuantity());
                checkUpdate = ptm.executeUpdate() > 0;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return checkUpdate;
    }

    public boolean checkDuplicate(String productID) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(CHECK_DUPLICATE);
                ptm.setString(1, productID);
                rs = ptm.executeQuery();
                if (rs.next()) {
                    check = true;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
        return check;
    }

    public boolean checkQuantity(String productID, int newQuantity) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(CHECK_QUANTITY);
                ptm.setString(1, productID);
                rs = ptm.executeQuery();
                if (rs.next()) {
                    int quantity = rs.getInt("quantity");
                    if (quantity >= newQuantity) {
                        check = true;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
        return check;
    }

    public int getQuantity(String productID) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(CHECK_QUANTITY);
                ptm.setString(1, productID);
                rs = ptm.executeQuery();
                if (rs.next()) {
                    return rs.getInt("quantity");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
        return 0;
    }

    public static void main(String[] args) throws SQLException {
        List<ProductDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(SEARCH_NAME);
                ptm.setString(1, "%" + "g" + "%");
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String productID = rs.getString("productID");
                    String productName = rs.getString("productName");
                    float price = rs.getFloat("price");
                    int quantity = rs.getInt("quantity");
                    int status = rs.getInt("status");
                    String imageUrl = rs.getString("imageUrl");
                    list.add(new ProductDTO(productID, productName, price, quantity, imageUrl));
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
        for (ProductDTO productDTO : list) {
            System.out.println(productDTO.toString());
        }
    }

    public ProductDTO getProductById(String productID) throws SQLException {
        ProductDTO product = new ProductDTO();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(SEARCH_ID);
                ptm.setString(1, productID);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String productName = rs.getString("productName");
                    float price = rs.getFloat("price");
                    int quantity = rs.getInt("quantity");
                    int status = rs.getInt("status");
                    String imageUrl = rs.getString("imageUrl");
                    return new ProductDTO(productID, productName, price, quantity, imageUrl);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
        return product;
    }

    public boolean insert(ProductDTO product) throws SQLException {
        boolean checkInsert = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBUtils.getConnection(DBUtils.DOCKER_PORT, DBUtils.DB_USER, DBUtils.DOCKER_DB_PASSWORD);
            if (conn != null) {
                ptm = conn.prepareStatement(INSERT);
                ptm.setString(1, product.getProductID());
                ptm.setString(2, product.getProductName());
                ptm.setFloat(3, product.getPrice());
                ptm.setInt(4, product.getQuantity());
                ptm.setInt(5, 1);
                ptm.setString(6, product.getImageUrl());
                checkInsert = ptm.executeUpdate() > 0;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return checkInsert;
    }

    public List<ProductSalesDTO> getTopSellingProducts(int limit) throws SQLException {
        List<ProductSalesDTO> topProducts = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT p.productID, p.productName, SUM(od.quantity) as quantitySold, SUM(od.price * od.quantity) as totalRevenue "
                        +
                        "FROM tblProduct p " +
                        "JOIN tblOrderDetail od ON p.productID = od.productID " +
                        "GROUP BY p.productID, p.productName " +
                        "ORDER BY quantitySold DESC " +
                        "LIMIT ?";
                ptm = conn.prepareStatement(sql);
                ptm.setInt(1, limit);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String productID = rs.getString("productID");
                    String productName = rs.getString("productName");
                    int quantitySold = rs.getInt("quantitySold");
                    double totalRevenue = rs.getDouble("totalRevenue");
                    topProducts.add(new ProductSalesDTO(productID, productName, quantitySold, totalRevenue));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null)
                rs.close();
            if (ptm != null)
                ptm.close();
            if (conn != null)
                conn.close();
        }
        return topProducts;
    }
}
