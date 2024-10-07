package kha.productsdemo.service;

import kha.productsdemo.core.AuthenticationOperations;
import kha.productsdemo.entity.Product;
import kha.productsdemo.entity.User;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class FavoriteListService {
    private final AuthenticationOperations authenticationOperations;
    private final ProductService productService;
    public FavoriteListService(AuthenticationOperations authenticationOperations, ProductService productService) {
        this.authenticationOperations = authenticationOperations;
        this.productService = productService;
    }

    public Set<Product> getFavoriteProducts() {
        User user = authenticationOperations.isAuthentication();
        if (user == null) {
            return null;
        }
        else {
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
            user.getFavoriteList().addProduct(product);
        }
    }

    public void removeProductFromFavoriteList(String productId) {
        Product product = productService.findByIdProduct(productId);
        User user = authenticationOperations.isAuthentication();
        if (user == null) {
            return;
        }
        else {
            user.getFavoriteList().removeProduct(product);
        }
    }
}
