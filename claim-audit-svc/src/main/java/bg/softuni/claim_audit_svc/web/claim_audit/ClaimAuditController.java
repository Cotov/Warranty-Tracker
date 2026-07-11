package bg.softuni.claim_audit_svc.web.claim_audit;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/claims")
@RequiredArgsConstructor
public class ClaimAuditController {

    private final ClaimAuditService claimAuditService;

    @GetMapping("/{claimId}/audit")
    public List<ClaimAuditEntryDto> getAuditEntries(@PathVariable UUID claimId) {
        return claimAuditService.getAuditEntries(claimId);
    }

}
