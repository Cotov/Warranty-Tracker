package bg.softuni.warranty_tracker.model.entity.contact;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Column;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Embeddable
public class Contact {

    @NotEmpty(message = "Email is required")
    @Email
    @Column(nullable = false)
    private String email;

    @NotEmpty
    @Column(nullable = false)
    private String phone;

    @NotEmpty
    @Column(nullable = false)
    private String website;

    //todo create a file with constant messages
}
