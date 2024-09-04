package kha.productsdemo.dto.converter;

import kha.productsdemo.dto.request.CreateProductRequest;
import kha.productsdemo.dto.request.CreateProductRequest2;
import kha.productsdemo.entity.Product;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Component
public class ConverterCreateProductsRequest {
    private final ImageConverter imageConverter;

    public ConverterCreateProductsRequest(ImageConverter imageConverter) {
        this.imageConverter = imageConverter;
    }

    public Product converterFromCreateProductRequestToProduct(CreateProductRequest request){
        Product product = new Product();
        product.setName(request.getName());
        product.setCategory(request.getCategory());
        product.setBrand(request.getBrand());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setImageName(imageConverter.SaveImage(request.getImageFile()));
        product.setCreatedDate(new Date());
        return  product;
    }

    public CreateProductRequest converterFromCreateProductRequest2ToCreateProductRequest(
            CreateProductRequest2 request2
            , MultipartFile image){
        CreateProductRequest request = new CreateProductRequest();
        request.setName(request2.getName());
        request.setBrand(request2.getBrand());
        request.setImageFile(request.getImageFile());
        request.setPrice(request2.getPrice());
        request.setCategory(request2.getCategory());
        request.setDescription(request2.getDescription());
        return  request;
    }

}
