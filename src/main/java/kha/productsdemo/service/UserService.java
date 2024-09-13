package kha.productsdemo.service;


import kha.productsdemo.dto.converter.ConverterShowUserAccount;
import kha.productsdemo.dto.converter.ConverterShowUserResponse;
import kha.productsdemo.dto.converter.CreateUserRequestConverter;
import kha.productsdemo.dto.request.CreateUserRequest;
import kha.productsdemo.dto.response.ShowUserAccount;
import kha.productsdemo.dto.response.ShowUserResponse;
import kha.productsdemo.entity.Role;
import kha.productsdemo.entity.User;
import kha.productsdemo.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final CreateUserRequestConverter userRequestConverter;
    private final ConverterShowUserResponse converterShowUserResponse;
    private final ConverterShowUserAccount converterShowUserAccount;
    public UserService(UserRepository userRepository, CreateUserRequestConverter userRequestConverter, ConverterShowUserResponse converterShowUserResponse, ConverterShowUserAccount converterShowUserAccount) {
        this.userRepository = userRepository;
        this.userRequestConverter = userRequestConverter;

        this.converterShowUserResponse = converterShowUserResponse;
        this.converterShowUserAccount = converterShowUserAccount;
    }

    public void createUser(CreateUserRequest createUserRequest){
        User user = userRequestConverter.convertFromCreateUserRequestToUser(createUserRequest);
        user.setRoles(Collections.singleton(Role.ROLE_USER));
        userRepository.save(user);
    }
    public void makeAdmin(String id){
        User user = userRepository.findUserById(id);
        if(user != null){
            user.getRoles().add(Role.ROLE_ADMIN);
            userRepository.save(user);
        }
    }
    public void dropAdmin(String id){
        User user = userRepository.findUserById(id);
        if(user != null){
            user.getRoles().remove(Role.ROLE_ADMIN);
            userRepository.save(user);
        }
    }
    public List<ShowUserResponse> getAllUsers(){
        return userRepository
                .findAll()
                .stream()
                .map(converterShowUserResponse::convertFromUserToShowUserResponse)
                .collect(Collectors.toList());
    }

    public void deleteUser(String id){
        try {
            userRepository.deleteById(id);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
    public User findOrCreateAdminUser(String email, String username, String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User adminUser = userRepository.findUserByEmail(email);
        if (adminUser == null) {
            adminUser = new User();
            adminUser.setUsername(username);
            adminUser.setEmail(email);
            adminUser.setPassword(passwordEncoder.encode(password));
            Set<Role> roles = new HashSet<>();
            roles.add(Role.ROLE_ADMIN);
            roles.add(Role.ROLE_USER);
            adminUser.setRoles(roles);
            return userRepository.save(adminUser);
        }
        return adminUser;
    }

    public ShowUserAccount convertToShowAccount(User user){
        return converterShowUserAccount.convertFromUserToShowUserAccount(user);
    }

    public User convertFromUserDetailsToUser(UserDetails userDetails){
        User user = new User();
        return user;
    }

    public User findUserByEmail(String email){
        return userRepository.findUserByEmail(email);
    }





}
