package kha.productsdemo.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UpdateProductRequest {
    private String id;
    @NotEmpty
    @Size(max = 30)
    private String name;
    @NotEmpty
    @Size(max = 30)
    private String brand;
    @NotEmpty
    @Size(max = 30)
    private String category;
    @NotEmpty
    @Size(min = 10, max = 500)
    private String description;
    @Min(0)
    private double price;

    private MultipartFile imageFile;
}
