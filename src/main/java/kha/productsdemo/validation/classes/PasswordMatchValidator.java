package kha.productsdemo.validation.classes;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kha.productsdemo.validation.interfaces.PasswordMatch;
import org.springframework.beans.BeanWrapperImpl;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, Object> {
    private String newPasswordField;
    private String confirmPasswordField;
    private String message;

    @Override
    public void initialize(PasswordMatch constraintAnnotation){
        this.newPasswordField = constraintAnnotation.newPassword();
        this.confirmPasswordField = constraintAnnotation.confirmPassword();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        Object newPasswordValue = new BeanWrapperImpl(value).getPropertyValue(newPasswordField);
        Object confirmPasswordValue = new BeanWrapperImpl(value).getPropertyValue(confirmPasswordField);

        boolean isValid = newPasswordValue != null && newPasswordValue.equals(confirmPasswordValue);
        if (!isValid){
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(confirmPasswordField)
                    .addConstraintViolation();
        }
        return isValid;
    }


}
