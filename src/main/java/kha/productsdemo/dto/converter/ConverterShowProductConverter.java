package kha.productsdemo.dto.converter;

import kha.productsdemo.dto.response.ShowProductResponse;
import kha.productsdemo.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ConverterShowProductConverter {
    public ShowProductResponse convertFromProductToShowProductResponse(Product product){
        ShowProductResponse response =  new ShowProductResponse();
        response.setId(product.getId());
        response.setImageName(product.getImageName());
        response.setName(product.getName());
        response.setBrand(product.getBrand());
        response.setPrice(product.getPrice());
        response.setCreatedDate(product.getCreatedDate());
        response.setDescription(product.getDescription());
        response.setCategory(product.getCategory());
        return response;
    }
}
