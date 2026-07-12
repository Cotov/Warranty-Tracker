package bg.softuni.warranty_tracker.client.audit;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import bg.softuni.warranty_tracker.model.dto.warrantyClaim.audit.CreateAuditEntryRequest;
import bg.softuni.warranty_tracker.model.dto.warrantyClaim.audit.CreateAuditEntryResponse;
import bg.softuni.warranty_tracker.model.dto.warrantyClaim.audit.GetAuditResponse;

@FeignClient(name = "audit-svc", url = "${audit-svc-base-url}")
public interface AuditClient {

    @PostMapping("/audit")
    ResponseEntity<CreateAuditEntryResponse> createAuditEntry(@RequestBody CreateAuditEntryRequest request);

    @GetMapping("/audit/{claimId}")
    ResponseEntity<GetAuditResponse> getAuditEntries(@PathVariable UUID claimId);

    @DeleteMapping("/audit/{claimId}")
    ResponseEntity<Void> deleteAuditEntries(@PathVariable UUID claimId);

}
