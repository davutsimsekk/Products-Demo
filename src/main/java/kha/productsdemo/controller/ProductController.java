package kha.productsdemo.controller;

import jakarta.validation.Valid;
import kha.productsdemo.dto.request.CreateProductRequest;
import kha.productsdemo.dto.request.UpdateProductRequest;
import kha.productsdemo.dto.response.ShowProductResponse;
import kha.productsdemo.entity.Product;
import kha.productsdemo.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping({"/listProducts", "/listProducts/{sortField}/{sortType}"})
    public String findAllProducts(
            @PathVariable(required = false) String sortField,
            @PathVariable(required = false) String sortType,
            Model model) {

        sortField = (sortField != null) ? sortField : "name";
        sortType = (sortType != null) ? sortType : "asc";
        String sortBy = "";
        if ("price".equals(sortField)) {
            sortBy = "price".equals(sortField) ?
                    ("asc".equals(sortType) ? "Low to High" : "High to Low") : "";
        } else if ("name".equals(sortField)) {
            sortBy = "name".equals(sortField) ?
                    ("asc".equals(sortType) ? "According to Name (A-Z)" : "According to Name (Z-A)") : "";
        }
        List<ShowProductResponse> products = productService.findAllProducts(sortField, sortType);
        model.addAttribute("products", products);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("basketSize", productService.getCartProductsSize());
        return "listProducts";
    }

    @GetMapping("/listProducts/{id}")
    public String showProduct(@PathVariable String id, Model model){
        ShowProductResponse product = productService.findProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("basketSize", productService.getCartProductsSize());
        return "showProduct";
    }

    @GetMapping("/listProducts/{id}/addToCart")
    public String addProductToCart(@PathVariable String id){
        productService.addToCart(id);
        return "redirect:/products/listProducts/{id}";
    }
    @GetMapping("/cart")
    public String showCartPage(Model model){
        model.addAttribute("basketProducts", productService.getCartProducts());
        model.addAttribute("totalPrice", productService.totalCartPrice());

        return "showCart";
    }

    @PostMapping("/listProducts/{id}/removeFromCart")
    public String removeProductFromCart(@RequestParam String productId){
        productService.deleteProductFromCart(productId);
        return "redirect:/products/cart";
    }
    @PostMapping("/listProducts/{id}/updateCart")
    public String updateProductCartQuantity(@RequestParam String productId, @RequestParam int quantity){
        productService.updateProductCartQuantity(productId, quantity);
        return "redirect:/products/cart";
    }


}
