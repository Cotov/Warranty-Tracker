package bg.softuni.warranty_tracker.model.dto.warrantyClaim;

import bg.softuni.warranty_tracker.constant.ValidationMessages;
import bg.softuni.warranty_tracker.model.dto.product.ProductDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddClaimRequest {

    @NotBlank(message = ValidationMessages.FAULT_DESCRIPTION_REQUIRED)
    private String faultDescription;
    private ProductDto product;
}
