package sample.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private String productID;

    @NotNull
    private String productName;

    @Min(0)
    private float price;

    @Min(0)
    private int quantity;

    private String imageUrl;
}