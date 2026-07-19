package bg.softuni.warranty_tracker.service.audit;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import bg.softuni.warranty_tracker.client.audit.AuditClient;
import bg.softuni.warranty_tracker.constant.ExceptionMessages;
import bg.softuni.warranty_tracker.constant.LogMessages;
import bg.softuni.warranty_tracker.customExceptions.claim.audit.CreateAuditEntryException;
import bg.softuni.warranty_tracker.customExceptions.claim.audit.DeleteAuditEntriesException;
import bg.softuni.warranty_tracker.customExceptions.claim.audit.GetAuditException;
import bg.softuni.warranty_tracker.mapper.audit.AuditMapper;
import bg.softuni.warranty_tracker.model.dto.warrantyClaim.audit.CreateAuditEntryRequest;
import bg.softuni.warranty_tracker.model.dto.warrantyClaim.audit.CreateAuditEntryResponse;
import bg.softuni.warranty_tracker.model.dto.warrantyClaim.audit.GetAuditResponse;
import bg.softuni.warranty_tracker.model.entity.warrantyClaim.ClaimStatus;
import feign.FeignException;
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

    public GetAuditResponse getAuditEntries(UUID claimId) {
        try {
            ResponseEntity<GetAuditResponse> response = auditClient.getAuditEntries(claimId, apiKey);
            log.info(LogMessages.AUDIT_ENTRIES_FETCHED_SUCCESSFULLY, claimId);
            return response.getBody();
        } catch (FeignException e) {
            log.error("Failed to fetch audit entries for claim ID: {}", claimId, e);
            throw new GetAuditException(ExceptionMessages.FAILED_TO_FETCH_AUDIT_ENTRIES);
        }
    }

    public void deleteAuditEntries(UUID claimId) {
        try {
            auditClient.deleteAuditEntries(claimId, apiKey);
            log.info(LogMessages.AUDIT_ENTRIES_DELETED_SUCCESSFULLY, claimId);
        } catch (FeignException e) {
            throw new DeleteAuditEntriesException(
                    String.format(ExceptionMessages.FAILED_TO_DELETE_AUDIT_ENTRIES, claimId));
        }
    }

    public void createAuditEntry(UUID claimId, ClaimStatus previousStatus, ClaimStatus newStatus) {
        ResponseEntity<CreateAuditEntryResponse> response = null;
        try {
            CreateAuditEntryRequest request = auditMapper.toCreateAuditEntryRequest(claimId, previousStatus, newStatus);
            response = auditClient.createAuditEntry(request, apiKey);
            log.info(LogMessages.AUDIT_ENTRY_CREATED_SUCCESSFULLY, response.getBody().getAuditEntryId());
        } catch (FeignException e) {
            log.error("Failed to create audit entry for claim ID: {}", claimId, e, response.getBody(),
                    response.getStatusCode());
            throw new CreateAuditEntryException(String.format(ExceptionMessages.FAILED_TO_CREATE_AUDIT_ENTRY,
                    response.getBody(), response.getStatusCode()));

        }
    }

}
