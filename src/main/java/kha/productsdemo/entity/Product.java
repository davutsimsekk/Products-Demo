package kha.productsdemo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private String brand;
    private String category;
    private String imageName;
    @Column(columnDefinition = "TEXT")
    private String description;
    private double price;
    private Date createdDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(price, product.price) == 0 && Objects.equals(id, product.id) && Objects.equals(name, product.name) && Objects.equals(brand, product.brand) && Objects.equals(category, product.category) && Objects.equals(imageName, product.imageName) && Objects.equals(description, product.description) && Objects.equals(createdDate, product.createdDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, brand, category, imageName, description, price, createdDate);
    }
}
