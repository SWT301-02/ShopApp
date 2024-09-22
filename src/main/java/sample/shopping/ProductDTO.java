/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.shopping;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author lmao
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "products")
public class ProductDTO {

    @Id
    private String productID;
    private String productName;
    private float price;
    private int quantity;
    private String imageUrl;

    public ProductDTO(String productID, String name, float price, int quantity) {
        this.productID = productID;
        this.productName = name;
        this.price = price;
        this.quantity = quantity;
    }

}
