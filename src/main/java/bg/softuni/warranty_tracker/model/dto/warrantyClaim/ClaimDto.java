package bg.softuni.warranty_tracker.model.dto.warrantyClaim;

import java.time.LocalDate;
import java.util.UUID;

import bg.softuni.warranty_tracker.model.dto.product.ProductDto;
import bg.softuni.warranty_tracker.model.entity.warrantyClaim.ClaimStatus;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ClaimDto {

    private UUID id;
    private ClaimStatus status;
    private String faultDescription;
    private LocalDate dateFiled;
    private ProductDto product;
}
