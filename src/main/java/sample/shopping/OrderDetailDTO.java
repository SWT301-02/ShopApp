package sample.shopping;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tblOrderDetail")
public class OrderDetailDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String productID;
    private double price;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "orderID", insertable = false, updatable = false)
    private OrderDTO order; // This establishes the relationship with OrderDTO
}