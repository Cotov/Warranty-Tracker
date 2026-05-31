package bg.softuni.warranty_tracker.model.entity.contact;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Column;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import bg.softuni.warranty_tracker.constant.ValidationMessages;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Embeddable
public class Contact {

    @NotEmpty(message = ValidationMessages.EMAIL_REQUIRED)
    @Email
    @Column(nullable = false)
    private String email;

    @NotEmpty(message = ValidationMessages.PHONE_REQUIRED)
    @Column(nullable = false)
    private String phone;

    @NotEmpty(message = ValidationMessages.WEBSITE_REQUIRED)
    @Column(nullable = false)
    private String website;
}
