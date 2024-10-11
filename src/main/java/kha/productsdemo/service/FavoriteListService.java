package kha.productsdemo.service;

import kha.productsdemo.core.AuthenticationOperations;
import kha.productsdemo.entity.FavoriteList;
import kha.productsdemo.entity.Product;
import kha.productsdemo.entity.User;
import kha.productsdemo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class FavoriteListService {
    private final AuthenticationOperations authenticationOperations;
    private final ProductService productService;
    private final UserRepository userRepository;
    public FavoriteListService(AuthenticationOperations authenticationOperations, ProductService productService, UserRepository userRepository) {
        this.authenticationOperations = authenticationOperations;
        this.productService = productService;

        this.userRepository = userRepository;
    }

    public void favoriteListExists(User user) {
        if (user.getFavoriteList() == null) {
            user.setFavoriteList(new FavoriteList());
            user.getFavoriteList().setProducts(new HashSet<>());
            userRepository.save(user);
            System.out.println("new favorite list created");
        }
        if (user.getFavoriteList().getProducts() == null) {
            user.getFavoriteList().setProducts(new HashSet<>());
        }
    }

    public boolean productExists(Product product) {
        User user = authenticationOperations.isAuthentication();
        if (user == null) {
            return false;
        }
        else {
            favoriteListExists(user);
            return user.getFavoriteList().getProducts().contains(product);
        }
    }

    public Set<Product> getFavoriteProducts() {
        User user = authenticationOperations.isAuthentication();
        if (user == null) {
            return null;
        }
        else {
            favoriteListExists(user);
            return user.getFavoriteList().getProducts();
        }
    }

    public void addProductToFavoriteList(String productId) {
        Product product = productService.findByIdProduct(productId);
        User user = authenticationOperations.isAuthentication();
        if (user == null) {
            return;
        }
        else {

            favoriteListExists(user);
            user.getFavoriteList().addProduct(product);
            userRepository.save(user);
        }

    }

    public void removeProductFromFavoriteList(String productId) {
        Product product = productService.findByIdProduct(productId);
        User user = authenticationOperations.isAuthentication();
        if (user == null) {
            return;
        }
        else {
            favoriteListExists(user);
            if(productExists(product)) {
                user.getFavoriteList().removeProduct(product);
                userRepository.save(user);
            }
        }
    }
}
