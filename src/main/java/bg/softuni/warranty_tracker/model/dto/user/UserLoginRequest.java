package bg.softuni.warranty_tracker.model.dto.user;

import bg.softuni.warranty_tracker.constant.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginRequest {

    @NotBlank(message = ValidationMessages.USERNAME_REQUIRED)
    private String username;

    @NotBlank(message = ValidationMessages.PASSWORD_REQUIRED)
    private String password;

}
