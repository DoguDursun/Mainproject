package task.v2.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "dim_product")
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long productId;

    @Column(name = "stock_quantity")
    private Long stockQuantity;

    @Column(name = "price")
    private Double price;

    @Column(name = "product_name")
    private String productName;




    @Override
    public String toString() {
        return "Product{" +
                "id=" + productId +
                ", stockQuantity=" + stockQuantity +
                ", price=" + price +
                ", product_name='" + productName + '\'' +
                '}';
    }
}
