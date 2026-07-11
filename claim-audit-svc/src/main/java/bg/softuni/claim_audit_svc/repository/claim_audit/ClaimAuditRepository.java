package bg.softuni.claim_audit_svc.repository.claim_audit;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import bg.softuni.claim_audit_svc.model.entity.ClaimAuditEntry;

public interface ClaimAuditRepository extends JpaRepository<ClaimAuditEntry, UUID> {

    List<ClaimAuditEntry> findByClaimIdOrderByChangedAtDesc(UUID claimId);
}
