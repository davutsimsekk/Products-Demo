package kha.productsdemo.dto.response;

import kha.productsdemo.entity.Role;
import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ShowUserResponse {
    private String id;
    private String username;
    private String email;
    private Set<Role> roles;
}
