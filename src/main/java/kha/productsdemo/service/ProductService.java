package kha.productsdemo.service;

import kha.productsdemo.dto.converter.ConverterCreateProductsRequest;
import kha.productsdemo.dto.converter.ConverterShowProductConverter;
import kha.productsdemo.dto.converter.ConverterUpdateProductRequest;
import kha.productsdemo.dto.request.CreateProductRequest;
import kha.productsdemo.dto.request.CreateProductRequest2;
import kha.productsdemo.dto.request.UpdateProductRequest;
import kha.productsdemo.dto.response.ShowProductResponse;
import kha.productsdemo.entity.Product;
import kha.productsdemo.exception.ProductNotFoundException;
import kha.productsdemo.repository.ProductRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ConverterCreateProductsRequest converterCreateProductsRequest;
    private final ConverterShowProductConverter converterShowProductConverter;
    private final ConverterUpdateProductRequest converterUpdateProductRequest;
    private final ImageService imageService;
    public ProductService(ProductRepository productRepository,
                          ConverterCreateProductsRequest converterCreateProductsRequest,
                          ConverterShowProductConverter converterShowProductConverter,
                          ConverterUpdateProductRequest converterUpdateProductRequest, ImageService imageService) {
        this.productRepository = productRepository;
        this.converterCreateProductsRequest = converterCreateProductsRequest;
        this.converterShowProductConverter = converterShowProductConverter;

        this.converterUpdateProductRequest = converterUpdateProductRequest;

        this.imageService = imageService;
    }
    public ShowProductResponse createProduct(CreateProductRequest request){
        Product product = converterCreateProductsRequest
                .converterFromCreateProductRequestToProduct(request);
        productRepository.save(product);
        return converterShowProductConverter.convertFromProductToShowProductResponse(product);
    }

    public ShowProductResponse findProductByName(String name){
        return converterShowProductConverter
                .convertFromProductToShowProductResponse(
                        productRepository.findProductByName(name)
                                .orElseThrow(() -> new ProductNotFoundException(name)));
    }
    public ShowProductResponse findProductById(String id){
        return converterShowProductConverter
                .convertFromProductToShowProductResponse(
                        productRepository.findProductById(id)
                                .orElseThrow(()-> new ProductNotFoundException(id))
                );
    }


    public void deleteProductById(String id){
        try {
            String imageName = findProductById(id).getImageName();
            productRepository.deleteById(id);
            imageService.deleteImage(imageName);

        }catch (Exception exception){
            System.out.println("Exception " + exception.getMessage());
        }
    }
    public List<Product> getAllProductsSorted(String sortField, String sortType) {
        Sort.Direction direction = sortType.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        return productRepository.findAll(Sort.by(direction, sortField));
    }
    public List<ShowProductResponse> findAllProducts(String sortField, String sortType){
        List<ShowProductResponse> products = getAllProductsSorted(sortField, sortType)
                        .stream()
                .map(product -> converterShowProductConverter.convertFromProductToShowProductResponse(product))
                .collect(Collectors.toList());
        return products;
    }
    public CreateProductRequest convertToCreateProductRequestFromCreateProductRequest2(
            CreateProductRequest2 request2
            , MultipartFile image){
        return converterCreateProductsRequest.converterFromCreateProductRequest2ToCreateProductRequest(request2, image);
    }
    public UpdateProductRequest convertToUpdateProductRequestFromCreateProductRequest2(
            CreateProductRequest2 request2
            , MultipartFile image){
        return convertToUpdateProductRequestFromCreateProductRequest2(request2, image);
    }

    public ShowProductResponse updateProduct(UpdateProductRequest request)throws ProductNotFoundException{
        Product product =
                converterUpdateProductRequest
                        .convertFromUpdateProductRequestToProduct(request
                                , productRepository.findProductById(request.getId()).orElseThrow());
        productRepository.save(product);
        return converterShowProductConverter.convertFromProductToShowProductResponse(product);
    }
    public Product findByIdProduct(String id){
        return productRepository.findProductById(id).orElseThrow(()-> new ProductNotFoundException(id));
    }

    public UpdateProductRequest convertFromProductToUpdateProductRequest(Product product){
        return converterUpdateProductRequest.convertFromProductToUpdateProductRequest(product);
    }

    @Getter
    @Setter
    private HashMap<ShowProductResponse, Integer> cartProducts = new HashMap<>();
    public void addToCart(String id){
        ShowProductResponse product = findProductById(id);

        cartProducts.put(product, cartProducts.getOrDefault(product, 0) + 1);
    }

    public void deleteProductFromCart(String id){
        ShowProductResponse product = findProductById(id);
        if(product != null){
            if(cartProducts.containsKey(product)){
                cartProducts.remove(product);
            }
        }
    }
    public Integer getCartProductsSize(){
        return cartProducts.values().stream().mapToInt(value -> value.intValue()).sum();
    }
    public double totalCartPrice(){
        return cartProducts.keySet().stream().mapToDouble(
                product -> product.getPrice() * cartProducts.get(product)).sum();
    }
    public void updateProductCartQuantity(String id, int quantity){
        ShowProductResponse product = findProductById(id);
        if(product != null){
            if (quantity > 0){
                cartProducts.put(product, quantity);
            }else {
                cartProducts.remove(product);
            }
        }
    }






}
