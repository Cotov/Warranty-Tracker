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
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private WarrantyClaimStatus status;

    @Column(nullable = false)
    private LocalDate dateSent;

    @Column(nullable = true)
    private String notes;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;



}
//Duplicate application entry points (bg.softuni.warranty.tracker vs bg.softuni.warranty_tracker) — packaging cleanup, not domain design