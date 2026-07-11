package bg.softuni.warranty_tracker.model.dto.warrantyClaim;

import java.util.UUID;

import bg.softuni.warranty_tracker.constant.ValidationMessages;
import bg.softuni.warranty_tracker.model.entity.warrantyClaim.ClaimStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditClaimRequest {

    private UUID id;

    @NotNull(message = ValidationMessages.STATUS_REQUIRED)
    private ClaimStatus status;

    @NotBlank(message = ValidationMessages.FAULT_DESCRIPTION_REQUIRED)
    private String faultDescription;
}
