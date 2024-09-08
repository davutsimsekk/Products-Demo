package kha.productsdemo.dto.converter;

import kha.productsdemo.dto.request.CreateProductRequest;
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
}
