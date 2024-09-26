package kha.productsdemo.service;

import kha.productsdemo.dto.response.ShowProductResponse;
import kha.productsdemo.repository.ProductPagingRepository;
import org.hibernate.query.SortDirection;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductPagingService {
    private final ProductPagingRepository productPagingRepository;
    private final ProductService productService;
    private final SortOptionService sortOptionService;
    public ProductPagingService(ProductPagingRepository productPagingRepository, ProductService productService, SortOptionService sortOptionService) {
        this.productPagingRepository = productPagingRepository;
        this.productService = productService;
        this.sortOptionService = sortOptionService;
    }

    public List<ShowProductResponse> getProducts(int startIndex, int productCount, String sortType){
        Sort sort = sortOptionService.createSort(sortType);
        Pageable pageable = PageRequest.of(startIndex, productCount, sort);
        List<ShowProductResponse> products = productPagingRepository.findAll(pageable)
                .stream()
                .map(product
                        -> productService.convertFromProductToShowProductResponse(product))
                .collect(Collectors.toList());
        return products;
    }
}
