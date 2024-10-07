package kha.productsdemo.core;

import kha.productsdemo.entity.User;
import kha.productsdemo.repository.UserRepository;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationOperations {
    private final UserRepository userRepository;

    public AuthenticationOperations(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User isAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() &&
                !(authentication instanceof AnonymousAuthenticationToken)) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                String email = userDetails.getUsername();
                return userRepository.findUserByEmail(email);
            }
        }
        return null;
    }
}
