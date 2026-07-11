package bg.softuni.claim_audit_svc.model.dto.claim_audit;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetClaimAuditEntry {

    private LocalDateTime changedAt;
    private String previousStatus;
    private String newStatus;
}