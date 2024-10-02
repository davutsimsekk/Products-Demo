package kha.productsdemo.repository;


import kha.productsdemo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, String> {
    Optional<Product> findProductByName(String name);
    Optional<Product> findProductById(String id);

}
