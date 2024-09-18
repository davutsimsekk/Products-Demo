package kha.productsdemo.validation.interfaces;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import kha.productsdemo.validation.classes.PasswordMatchValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordMatchValidator.class)
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordMatch {
    String message() default "Password doesn't match";

    String newPassword();
    String confirmPassword();

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface List{
        PasswordMatch[] value();
    }
}
