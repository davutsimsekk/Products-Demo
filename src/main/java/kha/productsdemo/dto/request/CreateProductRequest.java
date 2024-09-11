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
    @Size(max = 100)
    private String name;

    @NotEmpty
    @Size(max = 50)
    private String brand;
    @NotEmpty
    @Size(max = 100)
    private String category;
    @Size(min = 10, max = 2500)
    private String description;
    @Min(0)
    private double price;

    private MultipartFile imageFile;
}
