package kha.productsdemo.dto.converter;

import kha.productsdemo.dto.request.CreateUserRequest;
import kha.productsdemo.entity.FavoriteList;
import kha.productsdemo.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;

@Component
public class CreateUserRequestConverter {
    private final PasswordEncoder passwordEncoder;

    public CreateUserRequestConverter(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User convertFromCreateUserRequestToUser(CreateUserRequest request){
        User user = new User();
        user.setUsername(request.getUserName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFavoriteList(new FavoriteList());
        user.getFavoriteList().setProducts(new HashSet<>());

        return user;
    }
}
