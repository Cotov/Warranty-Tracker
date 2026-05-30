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

    @Column(nullable = false)
    private String serialNumber;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDate purchaseDate;

    @Column(nullable = false)
    private LocalDate warrantyStartDate;

    @Column(nullable = false)
    private LocalDate warrantyEndDate;

    @Column(nullable = false)
    private String physicalReceiptLocation;

    @ManyToOne
    @JoinColumn(name = "vendor_id", nullable = false)
    private Vendor vendor;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
