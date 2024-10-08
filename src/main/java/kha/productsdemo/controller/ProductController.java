package kha.productsdemo.controller;

import kha.productsdemo.dto.response.ShowProductResponse;
import kha.productsdemo.entity.ProductCounts;
import kha.productsdemo.entity.SortOption;
import kha.productsdemo.service.ProductPagingService;
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
    private final ProductPagingService productPagingService;
    public ProductController(ProductService productService, UserService userService, ProductPagingService productPagingService) {
        this.productService = productService;
        this.userService = userService;
        this.productPagingService = productPagingService;
    }

//    @GetMapping({"/listProducts", "/listProducts/{sortField}/{sortType}"})
//    public String findAllProducts(
//            @PathVariable(required = false) String sortField,
//            @PathVariable(required = false) String sortType,
//            @PathVariable(required = false) Integer startIndex,
//            @PathVariable(required = false) Integer productCount,
//            Model model) {
//
//        sortField = (sortField != null) ? sortField : "name";
//        sortType = (sortType != null) ? sortType : "asc";
//        startIndex = (startIndex != null) ? startIndex : 0;
//        productCount= (productCount != null) ? productCount : 50;
//        String sortBy = "";
//        if ("price".equals(sortField)) {
//            sortBy = "price".equals(sortField) ?
//                    ("asc".equals(sortType) ? "Low to High" : "High to Low") : "";
//        } else if ("name".equals(sortField)) {
//            sortBy = "name".equals(sortField) ?
//                    ("asc".equals(sortType) ? "According to Name (A-Z)" : "According to Name (Z-A)") : "";
//        }
////        List<ShowProductResponse> products = productService.findAllProducts(sortField, sortType);
//        List<ShowProductResponse> products = productPagingService.getProducts(startIndex, productCount, sortField, sortType);
//        model.addAttribute("products", products);
//        model.addAttribute("sortBy", sortBy);
//        model.addAttribute("basketSize", userService.getCartProductsSize());
//        model.addAttribute("productCount", productCount);
//        return "listProducts";
//    }

    @GetMapping("/listProducts")
    public String findAllProducts(
            @RequestParam(defaultValue = "price-asc") String sort,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int productCount,
            Model model) {
        long productCounts = productService.countOfAllProducts();
        double value = (double) productCounts / productCount;
        int totalPages = (int) Math.ceil(value);
        List<ShowProductResponse> products = productPagingService.getProducts(pageNum - 1, productCount, sort);
        model.addAttribute("products", products);
        model.addAttribute("currentSort", sort);
        model.addAttribute("basketSize", userService.getCartProductsSize());
        model.addAttribute("productCounts", new ProductCounts().getProductCounts());
        model.addAttribute("sortOptions", SortOption.values());
        model.addAttribute("currentCount", productCount);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", pageNum);
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

    @GetMapping("/listProducts/{id}/addToFavoriteList")
    public String addProductToFavoriteList(@PathVariable String id){
        userService.addProductToFavoriteList(id);
        return "redirect:/products/listProducts/{id}";
    }




}
