package sample.shopping;

public class ProductSalesDTO {
    private String productID;
    private String productName;
    private int quantitySold;
    private double totalRevenue;

    public ProductSalesDTO(String productID, String productName, int quantitySold, double totalRevenue) {
        this.productID = productID;
        this.productName = productName;
        this.quantitySold = quantitySold;
        this.totalRevenue = totalRevenue;
    }

    // Add getters and setters
}