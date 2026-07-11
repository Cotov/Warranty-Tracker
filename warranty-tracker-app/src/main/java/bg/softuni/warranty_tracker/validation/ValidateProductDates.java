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
@Constraint(validatedBy = ValidateProductDatesImpl.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateProductDates {

    String message() default ValidationMessages.INVALID_PRODUCT_DATES;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
