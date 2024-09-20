package kha.productsdemo.controller;

import kha.productsdemo.dto.response.ShowProductResponse;
import kha.productsdemo.service.ProductService;
import kha.productsdemo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final UserService userService;
    public ProductController(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
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
        model.addAttribute("basketSize", userService.getCartProductsSize());
        return "listProducts";
    }

    @GetMapping("/listProducts/{id}")
    public String showProduct(@PathVariable String id, Model model){
        ShowProductResponse product = productService.findProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("basketSize", userService.getCartProductsSize());
        return "showProduct";
    }

    @GetMapping("/listProducts/{id}/addToCart")
    public String addProductToCart(@PathVariable String id){
        userService.addToCart(id);
        return "redirect:/products/listProducts/{id}";
    }
//    @GetMapping("/cart")
//    public String showCartPage(Model model){
//        model.addAttribute("basketProducts", productService.getCartProducts());
//        model.addAttribute("totalPrice", productService.totalCartPrice());
//
//        return "showCart";
//    }
//
//    @PostMapping("/listProducts/{id}/removeFromCart")
//    public String removeProductFromCart(@RequestParam String productId, @PathVariable String id){
//        userService.deleteProductFromCart(productId);
//        return "redirect:/products/cart";
//    }
//    @PostMapping("/listProducts/{id}/updateCart")
//    public String updateProductCartQuantity(@RequestParam String productId, @RequestParam int quantity, @PathVariable String id){
//        userService.updateProductCartQuantity(productId, quantity);
//        return "redirect:/products/cart";
//    }


}
