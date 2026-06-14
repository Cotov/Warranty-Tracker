package bg.softuni.warranty_tracker.validation;

import bg.softuni.warranty_tracker.constant.Constants;
import bg.softuni.warranty_tracker.constant.ValidationMessages;
import bg.softuni.warranty_tracker.model.dto.product.RegisterProductRequest;
import bg.softuni.warranty_tracker.model.dto.vendor.RegisterVendorRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

public class ValidateNewVendorImpl implements ConstraintValidator<ValidateNewVendor, RegisterProductRequest> {

    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    @Override
    public boolean isValid(RegisterProductRequest request, ConstraintValidatorContext context) {
        if (request == null) {
            return true;
        }

        if (!Constants.CREATE_VENDOR_FLAG.equals(request.getVendorId())) {
            return true;
        }

        RegisterVendorRequest vendorRequest = request.getRegisterVendorRequest();
        boolean valid = true;

        context.disableDefaultConstraintViolation();

        if (vendorRequest == null || isBlank(vendorRequest.getName())) {
            ValidationUtils.addFieldError(context, "registerVendorRequest.name", ValidationMessages.VENDOR_REQUIRED);
            valid = false;
        }

        if (vendorRequest == null || isBlank(vendorRequest.getEmail())) {
            ValidationUtils.addFieldError(context, "registerVendorRequest.email", ValidationMessages.EMAIL_REQUIRED);
            valid = false;
        } else if (!isValidEmail(vendorRequest.getEmail())) {
            ValidationUtils.addFieldError(context, "registerVendorRequest.email", ValidationMessages.EMAIL_INVALID);
            valid = false;
        }

        if (vendorRequest == null || isBlank(vendorRequest.getPhone())) {
            ValidationUtils.addFieldError(context, "registerVendorRequest.phone", ValidationMessages.PHONE_REQUIRED);
            valid = false;
        }

        if (vendorRequest == null || isBlank(vendorRequest.getWebsite())) {
            ValidationUtils.addFieldError(context, "registerVendorRequest.website", ValidationMessages.WEBSITE_REQUIRED);
            valid = false;
        } else if (!isValidWebsite(vendorRequest.getWebsite())) {
            ValidationUtils.addFieldError(context, "registerVendorRequest.website", ValidationMessages.WEBSITE_INVALID);
            valid = false;
        }
        return valid;
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    private boolean isValidEmail(String email) {
        return VALIDATOR.validateValue(RegisterVendorRequest.class, "email", email).isEmpty();
    }

    private boolean isValidWebsite(String website) {
        return VALIDATOR.validateValue(RegisterVendorRequest.class, "website", website).isEmpty();
    }
}
