package kha.productsdemo.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UpdateUserRequest {
    @NotEmpty
    private String username;
    @NotEmpty
    @Email
    private String email;
}
