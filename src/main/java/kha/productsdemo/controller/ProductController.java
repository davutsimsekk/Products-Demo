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
    private String local_sort = "price-asc";
    private Integer local_pageNum = 1;
    private Integer local_productCount = 10;
    public ProductController(ProductService productService, UserService userService, ProductPagingService productPagingService) {
        this.productService = productService;
        this.userService = userService;
        this.productPagingService = productPagingService;
    }

    @GetMapping("/listProducts")
    public String findAllProducts(
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) Integer pageNum,
            @RequestParam(required = false) Integer productCount,
            Model model) {
        if(sort != null){
            this.local_sort = sort;
        }
        if(productCount != null){
            this.local_productCount = productCount;
        }
        if(pageNum != null){
            this.local_pageNum = pageNum;
        }

        long productCounts = productService.countOfAllProducts();
        double value = (double) productCounts / local_productCount;
        int totalPages = (int) Math.ceil(value);
        List<ShowProductResponse> products = productPagingService.getProducts(local_pageNum - 1, local_productCount, local_sort);
        model.addAttribute("products", products);
        model.addAttribute("currentSort", local_sort);
        model.addAttribute("basketSize", userService.getCartProductsSize());
        model.addAttribute("productCounts", new ProductCounts().getProductCounts());
        model.addAttribute("sortOptions", SortOption.values());
        model.addAttribute("currentCount", local_productCount);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", local_pageNum);
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
        return "redirect:/products/listProducts";
    }




}
