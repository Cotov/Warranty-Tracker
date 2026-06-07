package bg.softuni.warranty_tracker.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import bg.softuni.warranty_tracker.constant.ValidationMessages;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = ValidateNewVendorImpl.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateNewVendor {

    String message() default ValidationMessages.INVALID_VENDOR_DETAILS;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
