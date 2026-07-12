package bg.softuni.warranty_tracker.model.dto.warrantyClaim.audit;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAuditEntryResponse {
    @NotNull
    private UUID auditEntryId;


}
