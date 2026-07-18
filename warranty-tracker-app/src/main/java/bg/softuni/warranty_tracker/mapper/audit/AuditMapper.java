package bg.softuni.warranty_tracker.mapper.audit;

import java.util.UUID;

import org.springframework.stereotype.Component;

import bg.softuni.warranty_tracker.model.dto.warrantyClaim.audit.CreateAuditEntryRequest;
import bg.softuni.warranty_tracker.model.entity.warrantyClaim.ClaimStatus;

@Component
public class AuditMapper {

    public CreateAuditEntryRequest toCreateAuditEntryRequest(UUID claimId, ClaimStatus previousStatus,
            ClaimStatus newStatus) {
        return CreateAuditEntryRequest.builder()
                .claimId(claimId)
                .previousStatus(previousStatus.name())
                .newStatus(newStatus.name())
                .build();
    }

}
