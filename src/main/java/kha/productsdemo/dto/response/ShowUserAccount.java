package kha.productsdemo.dto.response;

import kha.productsdemo.entity.Role;
import lombok.*;

import java.util.Set;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ShowUserAccount {
    private String id;
    private String email;
    private String username;
    private Set<Role> roles;
}
