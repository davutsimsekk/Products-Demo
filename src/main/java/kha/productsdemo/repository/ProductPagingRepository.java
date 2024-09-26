package kha.productsdemo.repository;

import kha.productsdemo.entity.Product;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductPagingRepository extends PagingAndSortingRepository<Product, String> {
}
