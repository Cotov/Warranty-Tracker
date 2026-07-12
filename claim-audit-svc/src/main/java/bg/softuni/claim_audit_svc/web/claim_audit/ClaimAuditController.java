package bg.softuni.claim_audit_svc.web.claim_audit;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import bg.softuni.claim_audit_svc.model.dto.claim_audit.CreateAuditEntryRequest;
import bg.softuni.claim_audit_svc.model.dto.claim_audit.CreateAuditEntryResponse;
import bg.softuni.claim_audit_svc.model.dto.claim_audit.GetAuditResponse;
import bg.softuni.claim_audit_svc.service.claim_audit.ClaimAuditEntryService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/claims")
@RequiredArgsConstructor
public class ClaimAuditController {

    private final ClaimAuditEntryService claimAuditEntryService;

    @GetMapping("/{claimId}/audit")
    public ResponseEntity<GetAuditResponse> getAuditEntries(@PathVariable UUID claimId) {
        GetAuditResponse response = claimAuditEntryService.getAuditEntries(claimId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PostMapping("/audit")
    public ResponseEntity<CreateAuditEntryResponse> postAuditEntry(@Valid @RequestBody CreateAuditEntryRequest request) {

        CreateAuditEntryResponse response = claimAuditEntryService.createAuditEntry(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @DeleteMapping("/audit/{claimId}")
    public ResponseEntity<Void> deleteAuditEntries(@PathVariable UUID claimId) {
        claimAuditEntryService.deleteAuditEntriesForClaim(claimId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
