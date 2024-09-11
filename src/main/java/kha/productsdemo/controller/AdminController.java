package kha.productsdemo.controller;

import jakarta.validation.Valid;
import kha.productsdemo.dto.request.CreateProductRequest;
import kha.productsdemo.dto.request.UpdateProductRequest;
import kha.productsdemo.dto.response.ShowUserResponse;
import kha.productsdemo.entity.Product;
import kha.productsdemo.service.ProductService;
import kha.productsdemo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final ProductService productService;
    private final UserService userService;
    public AdminController(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }
    @GetMapping("/createProduct")
    public String showCreateForm(Model model) {
        CreateProductRequest product = new CreateProductRequest();
        model.addAttribute("product", product);
        return "createProduct";
    }

    @PostMapping("/createProduct")
    public String createProduct(@Valid @ModelAttribute("product") CreateProductRequest product,
                                BindingResult result) {

        if(product.getImageFile().isEmpty()){
            result.addError(
                    new FieldError("createProductRequest",
                            "imageFile", "The image file is required"));
        }
        if (result.hasErrors()){
            return "createProduct";
        }
        try {
            productService.createProduct(product);

        }catch (Exception e){
            result.rejectValue("imageFile", "error.imageFile",
                    "an error occurred while loading the image");
            return "createProduct";
        }


        return "redirect:/products/listProducts";
    }


    @GetMapping("/updateProduct/{id}")
    public String showUpdateForm(@PathVariable String id, Model model){
        Product product = productService.findByIdProduct(id);
        UpdateProductRequest updateProductRequest =
                productService.convertFromProductToUpdateProductRequest(product);
        model.addAttribute("updateProduct", updateProductRequest);
        return "updateProduct";
    }

    @PostMapping("/updateProduct/{id}")
    public String updateProduct(@Valid @ModelAttribute("updateProduct")
                                UpdateProductRequest updateProductRequest,
                                BindingResult result){

        if(result.hasErrors()){
            return "updateProduct";
        }
        try {
            productService.updateProduct(updateProductRequest);

        }catch (Exception e){
            System.out.println("Exception " + e.getMessage());
            return "updateProduct";
        }

        return "redirect:/products/listProducts/" + updateProductRequest.getId();
    }
    @PostMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable String id){
        productService.deleteProductById(id);
        return "redirect:/products/listProducts";
    }

    @GetMapping("/listUsers")
    public String showUsers(Model model){
        List<ShowUserResponse> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "listUsers";
    }

    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam String id){
        userService.deleteUser(id);
        return "redirect:/admin/listUsers";
    }

    @PostMapping("/makeAdmin")
    public String makeAdmin(@RequestParam String id){
        userService.makeAdmin(id);
        return "redirect:/admin/listUsers";
    }

    @PostMapping("/dropAdmin")
    public String dropAdmin(@RequestParam String id){
        userService.dropAdmin(id);
        return "redirect:/admin/listUsers";
    }





}
