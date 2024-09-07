package kha.productsdemo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kha.productsdemo.dto.response.ShowProductResponse;
import kha.productsdemo.repository.ProductRepository;
import kha.productsdemo.service.ProductService;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {
    private final ProductService productService;
    private final ObjectMapper objectMapper;
    private final ProductRepository productRepository;
    public ProductController(ProductService productService, ObjectMapper objectMapper, ProductRepository productRepository) {
        this.productService = productService;
        this.objectMapper = objectMapper;
        this.productRepository = productRepository;
    }

//    @PostMapping(value = "/createProduct", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ShowProductResponse createProduct(
//            @RequestPart("image") MultipartFile image,
//            @RequestPart("request") String requestJson) throws Exception {
//
//
//        CreateProductRequest2 request2 = objectMapper.readValue(requestJson, CreateProductRequest2.class);
//        return productService.createProduct(
//                productService.convertToCreateProductRequestFromCreateProductRequest2(request2, image));
//    }



    @PostMapping("/deleteProduct")
    public void deleteProduct(String id){
        productService.deleteProductById(id);
    }

//    @GetMapping("/findAllProducts")
//    public List<ShowProductResponse> findAllProducts(){
//        return productService.findAllProducts();
//    }

    @PostMapping("/findProductById/{id}")
    public ShowProductResponse findProductById(@PathVariable String id){
        return productService.findProductById(id);
    }

    @PostMapping("/findProductByName/{name}")
    public ShowProductResponse findProductByName(@PathVariable String name){
        return productService.findProductByName(name);
    }

}
