package bg.softuni.warranty_tracker.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import bg.softuni.warranty_tracker.constant.ValidationMessages;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = ValidateRegisterUserImpl.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateRegisterUser {
    String message() default ValidationMessages.CONFIRM_PASSWORD_MATCH;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
