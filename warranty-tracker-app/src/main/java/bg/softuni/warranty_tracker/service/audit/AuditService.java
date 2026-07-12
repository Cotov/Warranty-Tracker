package bg.softuni.warranty_tracker.service.audit;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import bg.softuni.warranty_tracker.client.audit.AuditClient;
import bg.softuni.warranty_tracker.constant.LogMessages;
import bg.softuni.warranty_tracker.mapper.audit.AuditMapper;
import bg.softuni.warranty_tracker.model.dto.warrantyClaim.audit.CreateAuditEntryRequest;
import bg.softuni.warranty_tracker.model.dto.warrantyClaim.audit.CreateAuditEntryResponse;
import bg.softuni.warranty_tracker.model.entity.warrantyClaim.ClaimStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuditService {

    private final AuditClient auditClient;
    private final AuditMapper auditMapper;

    @Value("${api-key}")
    private String apiKey;

    public void getAuditEntries(UUID claimId) {
        auditClient.getAuditEntries(claimId, apiKey);
        log.info(LogMessages.AUDIT_ENTRIES_FETCHED_SUCCESSFULLY, claimId);
    }

    public void deleteAuditEntries(UUID claimId) {
        auditClient.deleteAuditEntries(claimId, apiKey);
        log.info(LogMessages.AUDIT_ENTRIES_DELETED_SUCCESSFULLY, claimId);
    }

    public void createAuditEntry(UUID claimId, ClaimStatus previousStatus, ClaimStatus newStatus) {
        CreateAuditEntryRequest request = auditMapper.toCreateAuditEntryRequest(claimId, previousStatus, newStatus);
        ResponseEntity<CreateAuditEntryResponse> response = auditClient.createAuditEntry(request, apiKey);
        log.info(LogMessages.AUDIT_ENTRY_CREATED_SUCCESSFULLY, response.getBody().getAuditEntryId());
    }

}
