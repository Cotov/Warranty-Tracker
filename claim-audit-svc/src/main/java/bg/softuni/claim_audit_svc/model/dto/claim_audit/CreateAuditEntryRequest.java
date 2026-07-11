package bg.softuni.claim_audit_svc.model.dto.claim_audit;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAuditEntryRequest {

    @NotNull
    private UUID claimId;

    @NotBlank
    private String previousStatus;

    @NotBlank
    private String newStatus;

}
