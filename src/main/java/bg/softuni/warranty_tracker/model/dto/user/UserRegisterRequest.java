package bg.softuni.warranty_tracker.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import bg.softuni.warranty_tracker.constant.ValidationMessages;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequest {

    @NotBlank(message = ValidationMessages.USERNAME_REQUIRED)
    @Size(min = 6, max = 20, message = ValidationMessages.USERNAME_LENGTH)
    private String username;

    @NotBlank(message = ValidationMessages.PASSWORD_REQUIRED)
    @Size(min = 6, max = 20, message = ValidationMessages.PASSWORD_LENGTH)
    private String password;
    
    @NotBlank(message = ValidationMessages.EMAIL_REQUIRED)
    @Email
    private String email;

    @NotBlank(message = ValidationMessages.FIRST_NAME_REQUIRED)
    @Size(min = 2, max = 20, message = ValidationMessages.FIRST_NAME_LENGTH)
    private String firstName;

    @NotBlank(message = ValidationMessages.LAST_NAME_REQUIRED)
    @Size(min = 2, max = 20, message = ValidationMessages.LAST_NAME_LENGTH)
    private String lastName;
}
