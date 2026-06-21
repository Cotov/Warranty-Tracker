package bg.softuni.warranty_tracker.validation;

import bg.softuni.warranty_tracker.constant.ValidationMessages;
import bg.softuni.warranty_tracker.model.dto.product.ProductFormRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidateProductDatesImpl implements ConstraintValidator<ValidateProductDates, ProductFormRequest> {

    @Override
    public boolean isValid(ProductFormRequest request, ConstraintValidatorContext context) {

        boolean isValid = true;
        if (request.getPurchaseDate() == null || request.getWarrantyStart() == null || request.getWarrantyEnd() == null) {
            return isValid;
        }

        if (request.getWarrantyEnd().isBefore(request.getWarrantyStart())) {
            isValid = false;
            ValidationUtils.addFieldError(context, "warrantyStart",
                    ValidationMessages.WARRANTY_START_CANNOT_BE_BEFORE_START);
        }

        return isValid;
    }
}
