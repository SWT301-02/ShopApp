/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.shopping;

/**
 *
 * @author lmao
 */
public class ProductDTO {
    private String productID;
    private String productName;
    private float price;
    private int quantity;
    private String imageUrl;

    public ProductDTO() {
    }

    public ProductDTO(String productID, String name, float price, int quantity, String imageUrl) {
        this.productID = productID;
        this.productName = name;
        this.price = price;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
    }
    
    public ProductDTO(String productID, String name, float price, int quantity) {
        this.productID = productID;
        this.productName = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    

    @Override
    public String toString() {
        return "ProductDTO{" + "productID=" + productID + ", name=" + productName + ", price=" + price + ", quantity=" + quantity + ", imageUrl=" + imageUrl + '}';
    }

    
    
    
}
