package bg.softuni.warranty_tracker.model.entity.contact;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Column;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import jakarta.validation.constraints.Email;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Embeddable
public class Contact {

    @Email
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String website;
}
