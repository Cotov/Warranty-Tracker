package bg.softuni.warranty_tracker.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import bg.softuni.warranty_tracker.constant.ValidationMessages;
import bg.softuni.warranty_tracker.model.dto.user.UserRegisterRequest;

public class ValidateRegisterUserImpl implements ConstraintValidator<ValidateRegisterUser, UserRegisterRequest> {

    @Override
    public boolean isValid(UserRegisterRequest request, ConstraintValidatorContext context) {
        boolean isValid = true;
        if (request == null) {
            return isValid;
        }

        context.disableDefaultConstraintViolation();
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            context.buildConstraintViolationWithTemplate(ValidationMessages.CONFIRM_PASSWORD_MATCH)
                    .addPropertyNode("confirmPassword").addConstraintViolation();
            isValid = false;
        }
        return isValid;
    }

}
