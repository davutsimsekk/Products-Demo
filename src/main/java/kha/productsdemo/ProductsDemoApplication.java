package kha.productsdemo;

import kha.productsdemo.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProductsDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductsDemoApplication.class, args);
    }
    @Bean
    public CommandLineRunner initializeAdmin(UserService userService) {
        return args -> {
            userService.findOrCreateAdminUser("admin@gmail.com", "admin", "admin");
        };
    }
}
