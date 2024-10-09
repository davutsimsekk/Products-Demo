package kha.productsdemo.validation.classes;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kha.productsdemo.repository.UserRepository;
import kha.productsdemo.validation.interfaces.EmailIsExist;

public class EmailIsExistValidator implements ConstraintValidator<EmailIsExist, String> {

    private final UserRepository userRepository;

    public EmailIsExistValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return email != null && !userRepository.existsByEmail(email);
    }
}
