package bg.softuni.claim_audit_svc.service.claim_audit;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import bg.softuni.claim_audit_svc.mapper.claim_audit_entry.ClaimAuditEntryMapper;
import bg.softuni.claim_audit_svc.model.dto.claim_audit.CreateAuditEntryRequest;
import bg.softuni.claim_audit_svc.model.dto.claim_audit.CreateAuditEntryResponse;
import bg.softuni.claim_audit_svc.model.dto.claim_audit.GetAuditResponse;
import bg.softuni.claim_audit_svc.model.dto.claim_audit.GetClaimAuditEntry;
import bg.softuni.claim_audit_svc.model.entity.ClaimAuditEntry;
import bg.softuni.claim_audit_svc.repository.claim_audit.ClaimAuditRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClaimAuditEntryService {

    private final ClaimAuditRepository claimAuditRepository;
    private final ClaimAuditEntryMapper claimAuditEntryMapper;

    @Transactional
    public CreateAuditEntryResponse createAuditEntry( CreateAuditEntryRequest request) {
        ClaimAuditEntry auditEntry = claimAuditEntryMapper.toAuditEntry(request);
        claimAuditRepository.save(auditEntry);

        return CreateAuditEntryResponse.builder()
                .auditEntryId(auditEntry.getId())
                .build();
    }

    public GetAuditResponse getAuditEntries(UUID claimId) {
        List<ClaimAuditEntry> auditEntries = claimAuditRepository.findByClaimIdOrderByChangedAtDesc(claimId);

        List<GetClaimAuditEntry> auditEntryDtos = auditEntries.stream()
                .map(claimAuditEntryMapper::toGetAuditEntry)
                .toList();

        return GetAuditResponse.builder()
                .auditEntries(auditEntryDtos)
                .build();
    }
}
