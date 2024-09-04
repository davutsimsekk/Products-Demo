package kha.productsdemo.dto.response;

import lombok.*;

import java.util.Date;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ShowProductResponse {
    private String id;
    private String name;
    private String brand;
    private String category;
    private String description;
    private Date createdDate;
    private double price;
    private String imageName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShowProductResponse that = (ShowProductResponse) o;
        return Double.compare(price, that.price) == 0 && Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(brand, that.brand) && Objects.equals(category, that.category) && Objects.equals(description, that.description) && Objects.equals(createdDate, that.createdDate) && Objects.equals(imageName, that.imageName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, brand, category, description, createdDate, price, imageName);
    }
}
