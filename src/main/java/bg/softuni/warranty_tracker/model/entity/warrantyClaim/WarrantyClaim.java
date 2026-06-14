package bg.softuni.warranty_tracker.model.entity.warrantyClaim;

import java.time.LocalDate;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.util.UUID;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import bg.softuni.warranty_tracker.model.entity.product.Product;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotEmpty;
import bg.softuni.warranty_tracker.constant.ValidationMessages;

@Table(name = "warranty_claims")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class WarrantyClaim {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotEmpty(message = ValidationMessages.STATUS_REQUIRED)
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private WarrantyClaimStatus status;

    @NotEmpty(message = ValidationMessages.DATE_SENT_REQUIRED)
    @Column(nullable = false)
    private LocalDate dateSent;

    @NotEmpty(message = ValidationMessages.NOTES_REQUIRED)
    @Column(nullable = true)
    private String notes;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

}