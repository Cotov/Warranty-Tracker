package bg.softuni.warranty_tracker.service.audit;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import bg.softuni.warranty_tracker.client.audit.AuditClient;
import bg.softuni.warranty_tracker.model.dto.warrantyClaim.audit.CreateAuditEntryRequest;
import bg.softuni.warranty_tracker.model.dto.warrantyClaim.audit.CreateAuditEntryResponse;
import bg.softuni.warranty_tracker.model.dto.warrantyClaim.audit.GetAuditResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuditService {

    private final AuditClient auditClient;

    @Value("${api-key}")
    private String apiKey;

    public ResponseEntity<GetAuditResponse> getAuditEntries(UUID claimId) {
        return auditClient.getAuditEntries(claimId);
    }

    public ResponseEntity<Void> deleteAuditEntries(UUID claimId) {
        return auditClient.deleteAuditEntries(claimId);
    }

    public ResponseEntity<CreateAuditEntryResponse> createAuditEntry(CreateAuditEntryRequest request) {
        return auditClient.createAuditEntry(request);
    }

}
