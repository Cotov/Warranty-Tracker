package bg.softuni.warranty_tracker.model.entity.vendor;

import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.util.UUID;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import bg.softuni.warranty_tracker.model.entity.contact.Contact;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotEmpty;
import bg.softuni.warranty_tracker.constant.ValidationMessages;

@Table(name = "vendors")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotEmpty(message = ValidationMessages.NAME_REQUIRED)
    @Column(nullable = false)
    private String name;

    @Embedded
    private Contact contact;
}
