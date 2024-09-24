package sample.shopping;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sample.model.Order;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tblOrderDetail")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String productID;
    private double price;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "orderID", insertable = false, updatable = false)
    private Order order; // This establishes the relationship with Order
}