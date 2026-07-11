package bg.softuni.claim_audit_svc.mapper.claim_audit_entry;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import bg.softuni.claim_audit_svc.model.dto.claim_audit.CreateAuditEntryRequest;
import bg.softuni.claim_audit_svc.model.dto.claim_audit.GetClaimAuditEntry;
import bg.softuni.claim_audit_svc.model.entity.ClaimAuditEntry;

@Component
public class ClaimAuditEntryMapper {

    public ClaimAuditEntry toAuditEntry(CreateAuditEntryRequest request) {
        return ClaimAuditEntry.builder()
                .claimId(request.getClaimId())
                .previousStatus(request.getPreviousStatus())
                .newStatus(request.getNewStatus())
                .changedAt(LocalDateTime.now())
                .build();
    }

    public GetClaimAuditEntry toGetAuditEntry(ClaimAuditEntry auditEntry) {
        return GetClaimAuditEntry.builder()
                .changedAt(auditEntry.getChangedAt())
                .previousStatus(auditEntry.getPreviousStatus())
                .newStatus(auditEntry.getNewStatus())
                .build();
    }



}
