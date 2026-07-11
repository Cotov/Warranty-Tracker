package bg.softuni.claim_audit_svc.model.dto.claim_audit;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetAuditResponse {

    private List<GetClaimAuditEntry> auditEntries;


}
