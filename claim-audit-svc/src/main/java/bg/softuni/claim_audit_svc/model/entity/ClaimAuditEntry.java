package bg.softuni.claim_audit_svc.model.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "claim_audit_entries")
public class ClaimAuditEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    UUID id;

    @Column(nullable = false)
    UUID claimId;

    @Column(nullable = false)
    UUID productId;

    @Column(nullable = false)
    String previousStatus;

    @Column(nullable = false)
    String newStatus;

    @Column(nullable = false)
    LocalDateTime changedAt;

}