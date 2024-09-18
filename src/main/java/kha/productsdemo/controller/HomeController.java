package kha.productsdemo.controller;

import jakarta.validation.Valid;
import kha.productsdemo.dto.request.CreateUserRequest;
import kha.productsdemo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping({"/","", "/home"})
    public String home(){
        return "home";
    }

    @GetMapping({"/login"})
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model){
        CreateUserRequest createUserRequest = new CreateUserRequest();
        model.addAttribute("createUserRequest", createUserRequest);
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("createUserRequest") CreateUserRequest request
            , BindingResult result){
        if (result.hasErrors()){
            return "register";
        }
        userService.createUser(request);
        return "redirect:/login";
    }


}
