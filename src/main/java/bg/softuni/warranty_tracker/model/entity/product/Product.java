package bg.softuni.warranty_tracker.model.entity.product;

import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.UUID;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import bg.softuni.warranty_tracker.model.entity.vendor.Vendor;
import bg.softuni.warranty_tracker.model.entity.user.User;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import bg.softuni.warranty_tracker.constant.ValidationMessages;
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotEmpty(message = ValidationMessages.SERIAL_NUMBER_REQUIRED)
    @Column(nullable = false)
    private String serialNumber;

    @NotEmpty(message = ValidationMessages.DESCRIPTION_REQUIRED)
    @Column(nullable = false)
    private String description;

    @NotNull(message = ValidationMessages.PURCHASE_DATE_REQUIRED)
    @Column(nullable = false)
    private LocalDate purchaseDate;

    @NotNull(message = ValidationMessages.WARRANTY_START_DATE_REQUIRED)
    @Column(nullable = false)
    private LocalDate warrantyStartDate;

    @NotNull(message = ValidationMessages.WARRANTY_END_DATE_REQUIRED)
    @Column(nullable = false)
    private LocalDate warrantyEndDate;

    @NotEmpty(message = ValidationMessages.PHYSICAL_RECEIPT_LOCATION_REQUIRED)
    @Column(nullable = false)
    private String physicalReceiptLocation;

    @ManyToOne
    @JoinColumn(name = "vendor_id", nullable = false)
    private Vendor vendor;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
