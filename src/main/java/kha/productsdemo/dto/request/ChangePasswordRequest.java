package kha.productsdemo.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class ChangePasswordRequest {
    @NotEmpty
    private String currentPassword;
    @Size(min = 6, max = 20)
    private String newPassword;
    @Size(min = 6, max = 20)

    private String confirmPassword;
}
