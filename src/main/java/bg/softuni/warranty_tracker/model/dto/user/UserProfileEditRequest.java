package bg.softuni.warranty_tracker.model.dto.user;

import bg.softuni.warranty_tracker.constant.ValidationMessages;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileEditRequest {
    @NotBlank(message = ValidationMessages.FIRST_NAME_REQUIRED)
    @Size(min = 3, max = 20, message = ValidationMessages.FIRST_NAME_LENGTH) 
    private String firstName;

    @NotBlank(message = ValidationMessages.LAST_NAME_REQUIRED)
    @Size(min = 3, max = 20, message = ValidationMessages.LAST_NAME_LENGTH)
    private String lastName;

    @NotBlank(message = ValidationMessages.EMAIL_REQUIRED)
    @Email
    private String email;
}
