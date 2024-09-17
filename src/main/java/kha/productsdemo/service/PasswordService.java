package kha.productsdemo.service;

import org.hibernate.type.TrueFalseConverter;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {
    public boolean comparePasswords(String newPassword, String confirmPassword){
        if (newPassword.equals(confirmPassword)){
            return true;
        }
        return false;
    }
}
