package kha.productsdemo.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CreateProductRequest2 {
    @NotNull
    @Size(max = 30)
    private String name;
    @NotNull
    @Size(max = 30)
    private String brand;
    @NotNull
    @Size(max = 30)
    private String category;
    @NotEmpty
    @Min(10)
    @Max(500)
    private String description;
    @Min(0)
    private double price;


}