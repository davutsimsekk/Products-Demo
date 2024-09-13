package kha.productsdemo.controller;

import kha.productsdemo.dto.response.ShowUserAccount;
import kha.productsdemo.service.UserService;
import org.springframework.security.core.Authentication;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
            if (authentication != null && authentication.isAuthenticated()) {
                org.springframework.security.core.userdetails.User springUser =
                        (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
                System.out.println(springUser.getUsername());
                kha.productsdemo.entity.User user = userService.findUserByEmail(springUser.getUsername());
                ShowUserAccount showUserAccount = userService.convertToShowAccount(user);
                System.out.println(showUserAccount);
                model.addAttribute("user", showUserAccount);
                return "account";
            }
        }
            return "redirect:/login";
    }
}
