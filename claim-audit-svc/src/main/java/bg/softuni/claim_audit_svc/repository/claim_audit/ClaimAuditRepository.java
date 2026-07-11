package bg.softuni.claim_audit_svc.repository.claim_audit;
import org.springframework.data.jpa.repository.JpaRepository;

import bg.softuni.claim_audit_svc.model.entity.ClaimAuditEntry;

public interface ClaimAuditRepository extends JpaRepository<ClaimAuditEntry, UUID> {


}
