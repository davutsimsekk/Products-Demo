package kha.productsdemo.exception;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(String productName) {
        super("Product Not Found -> " + productName);
    }
}
