package kha.productsdemo.validation.interfaces;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import kha.productsdemo.validation.classes.EmailIsExistValidator;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD})
@Constraint(validatedBy = EmailIsExistValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailIsExist {
    String message() default "Email already exist";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
