package kha.productsdemo.dto.converter;

import kha.productsdemo.dto.request.UpdateProductRequest;
import kha.productsdemo.entity.Product;
import kha.productsdemo.repository.ProductRepository;
import org.springframework.stereotype.Component;

@Component
public class ConverterUpdateProductRequest {
    private final ImageConverter imageConverter;

    public ConverterUpdateProductRequest(ImageConverter imageConverter, ProductRepository productRepository) {
        this.imageConverter = imageConverter;
    }

    public Product convertFromUpdateProductRequestToProduct(UpdateProductRequest request, Product product){
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setBrand(request.getBrand());
        if(!request.getImageFile().isEmpty()){
            try {
                String oldImageName = product.getImageName();
                product.setImageName(imageConverter.SaveImage(request.getImageFile()));
                imageConverter.deleteImage(oldImageName);
            }catch (Exception e){
                System.out.println("Exception -> " + e.getMessage());
            }

        }
        product.setCategory(request.getCategory());
        return product;
    }

    public UpdateProductRequest convertFromProductToUpdateProductRequest(Product product){
        UpdateProductRequest updateProductRequest = new UpdateProductRequest();
        updateProductRequest.setId(product.getId());
        updateProductRequest.setDescription(product.getDescription());
        updateProductRequest.setBrand(product.getBrand());
        updateProductRequest.setCategory(product.getCategory());
        updateProductRequest.setPrice(product.getPrice());
        updateProductRequest.setName(product.getName());
        return updateProductRequest;
    }
}
