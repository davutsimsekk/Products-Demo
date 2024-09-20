package kha.productsdemo.service;

import kha.productsdemo.entity.Product;
import kha.productsdemo.entity.User;
import kha.productsdemo.repository.UserRepository;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CartService {
    private final ProductService productService;
    private final UserRepository userRepository;
    private Map<Product, Integer> cart = new HashMap<>();

    public CartService(ProductService productService, UserRepository userRepository) {
        this.productService = productService;
        this.userRepository = userRepository;
    }


//    public User isAuthentication(){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if(authentication != null && authentication.isAuthenticated()){
//            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//            String email = userDetails.getUsername();
//            User user = userRepository.findUserByEmail(email);
//            return user;
//        }
//        return null;
//    }
public User isAuthentication() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.isAuthenticated() &&
            !(authentication instanceof AnonymousAuthenticationToken)) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            String email = userDetails.getUsername();
            return userRepository.findUserByEmail(email);
        }
    }
    return null;
}
    public Map<Product, Integer> getCartProducts(){
        User user = isAuthentication();
        if (user != null){
            return user.getCart();
        }
        else {
            return cart;
        }
    }
    public void addToCart(String productId){
        User user = isAuthentication();
        Product product = productService.findByIdProduct(productId);
        if (user != null){
            user.getCart().put(product, user.getCart().getOrDefault(product, 0) + 1);
            userRepository.save(user);
        }
        else {
            cart.put(product, cart.getOrDefault(product, 0) + 1);
        }
    }
    public void deleteProductFromCart(String productId){
        Product product = productService.findByIdProduct(productId);
        User user = isAuthentication();
        if (user != null){
            if (user.getCart().containsKey(product)){
                user.getCart().remove(product);
            }
            userRepository.save(user);
        }
        else {
            if (cart.containsKey(product)){
                cart.remove(product);
            }
        }
    }
    public Integer getCartProductsSize(){
        User user = isAuthentication();
        if(user != null){
            return user.getCart().values().stream().mapToInt(value -> value.intValue()).sum();
        }
        else {
            return cart.values().stream().mapToInt(value -> value.intValue()).sum();
        }
    }
    public double totalCartPrice(){
        User user = isAuthentication();
        if(user != null){
            Map<Product, Integer> userCart = user.getCart();
            return userCart.keySet().stream().mapToDouble(product -> product.getPrice() * userCart.get(product)).sum();

        }
        else{
            return cart.keySet().stream().mapToDouble(product -> product.getPrice() * cart.get(product)).sum();
        }
    }
    public void updateProductCartQuantity(String productId, int quantity){
        Product product = productService.findByIdProduct(productId);
        User user = isAuthentication();
        if(user != null){
            if (user.getCart().containsKey(product)){
                if (quantity > 0){
                    user.getCart().put(product, quantity);
                }
                else {
                    user.getCart().remove(product);
                }
            }
            userRepository.save(user);
        }
        else {
            if (cart.containsKey(product)){
                if (quantity > 0){
                    cart.put(product, quantity);
                }
                else {
                    cart.remove(product);
                }
            }
        }
    }

    public Map<Product, Integer> getDefaultCart(){
        return cart;
    }
}
