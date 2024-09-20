package kha.productsdemo.controller;

import kha.productsdemo.entity.User;
import kha.productsdemo.service.CartService;
import kha.productsdemo.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/cart")
public class CartController {
    private final UserService userService;
    public CartController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping({"","/"})
    public String showCart(Model model, Authentication authentication){
        User currentUser = userService.convertFromAuthenticationToUser(authentication);
        model.addAttribute("basketProducts", currentUser.getCart());
        model.addAttribute("totalPrice", userService.totalCartPrice());
        return "showCart";
    }


    @PostMapping("/removeFromCart")
    public String removeProductFromCart(@RequestParam String productId){
        userService.deleteProductFromCart(productId);
        return "redirect:/cart";
    }

    @PostMapping("/updateCart")
    public String updateProductCartQuantity(@RequestParam String productId, @RequestParam int quantity){
        userService.updateProductCartQuantity(productId, quantity);
        return "redirect:/cart";
    }
}
