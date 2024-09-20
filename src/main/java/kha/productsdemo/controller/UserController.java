package kha.productsdemo.controller;


import jakarta.validation.Valid;
import kha.productsdemo.dto.request.ChangePasswordRequest;

import kha.productsdemo.dto.request.UpdateUserRequest;
import kha.productsdemo.dto.response.ShowUserAccount;
import kha.productsdemo.entity.User;
import kha.productsdemo.service.UserService;
import org.springframework.security.core.Authentication;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/account")
    public String showAccountPage(Authentication authentication, Model model) {

            if (authentication != null && authentication.isAuthenticated()) {
                User user = userService.convertFromAuthenticationToUser(authentication);
                ShowUserAccount showUserAccount = userService.convertToShowAccount(user);
                model.addAttribute("user", showUserAccount);
                return "account";
            }

            return "redirect:/login";
    }

    @GetMapping("/editProfile")
    public String showEditProfilePage(Authentication authentication, Model model){
        if (authentication != null && authentication.isAuthenticated()) {
            User user = userService.convertFromAuthenticationToUser(authentication);
            UpdateUserRequest updateUserRequest = userService.convertFromUserToUpdateProductRequest(user);
            model.addAttribute("updateUserRequest", updateUserRequest);
            return "editProfile";
        }
        else {
            return "redirect:/login";
        }
    }

    @PostMapping("/editProfile")
    public String editProfile(@Valid
                                  @ModelAttribute("updateUserRequest")UpdateUserRequest updateUserRequest,
                              BindingResult result,
                              Authentication authentication){
        if (result.hasErrors()){
            return "editProfile";
        }
        User user = userService.convertFromAuthenticationToUser(authentication);
        User updatedUser = userService.updateUser(updateUserRequest, user);
        userService.updateAuthentication(updatedUser);

        return "redirect:/user/account";
    }

    @GetMapping("/changePassword")
    public String showChangePasswordPage(Model model){
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        model.addAttribute("changePasswordRequest", changePasswordRequest);
        return "changePasswordPage";
    }
    @PostMapping("/changePassword")
    public String changePassword(Authentication authentication, @Valid @ModelAttribute("changePasswordRequest")
                                     ChangePasswordRequest changePasswordRequest,
                                  BindingResult result){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User currentUser = userService.convertFromAuthenticationToUser(authentication);
        if (!passwordEncoder.matches(changePasswordRequest.getCurrentPassword(), currentUser.getPassword())){
            FieldError error =
                    new FieldError("changePasswordRequest",
                            "currentPassword", "Current Password is wrong");
            result.addError(error);
        }
        if (result.hasErrors()){
            changePasswordRequest.setConfirmPassword("");
            changePasswordRequest.setCurrentPassword("");
            changePasswordRequest.setNewPassword("");
            return "changePasswordPage";
        }
        userService.changePassword(currentUser, changePasswordRequest);
        return "redirect:/user/account";
    }

    @GetMapping("/cart")
    public String showCart(Model model, Authentication authentication){
        User currentUser = userService.convertFromAuthenticationToUser(authentication);
        model.addAttribute("basketProducts", currentUser.getCart());
        model.addAttribute("totalPrice", userService.totalCartPrice());
        return "showCart";
    }


    @PostMapping("/cart/removeFromCart")
    public String removeProductFromCart(@RequestParam String productId){
        userService.deleteProductFromCart(productId);
        return "redirect:/user/cart";
    }

    @PostMapping("/cart/updateCart")
    public String updateProductCartQuantity(@RequestParam String productId, @RequestParam int quantity){
        userService.updateProductCartQuantity(productId, quantity);
        return "redirect:/user/cart";
    }
}
