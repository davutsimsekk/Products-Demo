package kha.productsdemo.service;


import kha.productsdemo.dto.converter.*;
import kha.productsdemo.dto.request.ChangePasswordRequest;
import kha.productsdemo.dto.request.CreateUserRequest;
import kha.productsdemo.dto.request.UpdateUserRequest;
import kha.productsdemo.dto.response.ShowUserAccount;
import kha.productsdemo.dto.response.ShowUserResponse;
import kha.productsdemo.entity.Product;
import kha.productsdemo.entity.Role;
import kha.productsdemo.entity.User;
import kha.productsdemo.repository.UserRepository;
import kha.productsdemo.security.CustomUserDetailService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final CreateUserRequestConverter userRequestConverter;
    private final ConverterShowUserResponse converterShowUserResponse;
    private final ConverterShowUserAccount converterShowUserAccount;
    private final ConverterUpdateProductRequest converterUpdateProductRequest;
    private final ConverterUpdateUserRequest converterUpdateUserRequest;
    private final CustomUserDetailService customUserDetailService;
    private final UserDetailsService userDetailsService;
    private final CartService cartService;
    public UserService(UserRepository userRepository, CreateUserRequestConverter userRequestConverter, ConverterShowUserResponse converterShowUserResponse, ConverterShowUserAccount converterShowUserAccount, ConverterUpdateProductRequest converterUpdateProductRequest, ConverterUpdateUserRequest converterUpdateUserRequest, CustomUserDetailService customUserDetailService, UserDetailsService userDetailsService, CartService cartService) {
        this.userRepository = userRepository;
        this.userRequestConverter = userRequestConverter;

        this.converterShowUserResponse = converterShowUserResponse;
        this.converterShowUserAccount = converterShowUserAccount;
        this.converterUpdateProductRequest = converterUpdateProductRequest;
        this.converterUpdateUserRequest = converterUpdateUserRequest;
        this.customUserDetailService = customUserDetailService;
        this.userDetailsService = userDetailsService;
        this.cartService = cartService;
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


    public User findUserByEmail(String email){
        return userRepository.findUserByEmail(email);
    }

    public User convertFromAuthenticationToUser(Authentication authentication){
        if (authentication != null && authentication.isAuthenticated()) {
            org.springframework.security.core.userdetails.User springUser
                    = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
            User user = findUserByEmail(springUser.getUsername());
            return user;
        }
        return null;
    }

    public UpdateUserRequest convertFromUserToUpdateProductRequest(User user){
        UpdateUserRequest updateUserRequest
                = converterUpdateUserRequest.convertFromUserToUpdateUserRequest(user);
        return updateUserRequest;
    }

    public User updateUser(UpdateUserRequest from, User to){
        to = converterUpdateUserRequest.convertFromUpdateUserRequestToUser(from, to);
        return userRepository.save(to);
    }

    public Authentication createNewAuthentication(User user){
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    public void changePassword(User user, ChangePasswordRequest changePasswordRequest){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        User updatedUser = userRepository.save(user);
        updateAuthentication(updatedUser);
    }
    public void updateAuthentication(User user){
        Authentication authentication = createNewAuthentication(user);
        SecurityContextHolder.getContext().setAuthentication(authentication);

    }
    public void saveUser(User user){
        userRepository.save(user);
    }

    public void addToCart(String productId){
        cartService.addToCart(productId);
    }

    public void deleteProductFromCart(String productId){
        cartService.deleteProductFromCart(productId);
    }

    public Integer getCartProductsSize(){
        return cartService.getCartProductsSize();
    }

    public double totalCartPrice(){
        return cartService.totalCartPrice();
    }

    public void updateProductCartQuantity(String productId, int quantity){
        cartService.updateProductCartQuantity(productId, quantity);
    }

    public Map<Product, Integer> getCartProducts(){
        return cartService.getCartProducts();
    }

    public Map<Product, Integer> getDefaultCart(){
        return cartService.getDefaultCart();
    }

    @Transactional(readOnly = true)
    public User getUserWithCart(Authentication authentication) {
        User user = convertFromAuthenticationToUser(authentication);
        user.getCart().size(); // This will force the initialization of the cart
        return user;
    }

}
