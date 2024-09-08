package kha.productsdemo.dto.request;



import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;



@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CreateProductRequest {
    @NotEmpty
    @Max(100)
    @Min(10)
    private String name;

    @Max(50)
    private String brand;
    @NotEmpty
    @Max(100)
    private String category;
    @Min(10)
    @Max(2500)
    private String description;
    @Min(0)
    private double price;

    private MultipartFile imageFile;
}
