package bg.softuni.warranty_tracker.model.dto.vendor;

import bg.softuni.warranty_tracker.constant.ValidationMessages;
import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.URL;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterVendorRequest {
    private String name;

    @Email(message = ValidationMessages.EMAIL_INVALID)
    private String email;
    private String phone;

    @URL(message = ValidationMessages.WEBSITE_INVALID)
    private String website;

}
