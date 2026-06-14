package bg.softuni.warranty_tracker.validation;

import jakarta.validation.ConstraintValidatorContext;

public class ValidationUtils {

    public static void addFieldError(ConstraintValidatorContext context, String field, String message) {
        context.buildConstraintViolationWithTemplate(message)
                .addPropertyNode(field)
                .addConstraintViolation();
    }

}
