package bg.softuni.warranty_tracker.model.dto.warrantyClaim.audit;

import java.time.LocalDate;

import bg.softuni.warranty_tracker.model.entity.warrantyClaim.ClaimStatus;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ClaimHistoryDto {
    private String productDescription;
    private String productSerialNumber;
    private LocalDate warrantyEndDate;
    private LocalDate dateClaimFiled;
    private String faultDescription;
    private ClaimStatus claimStatus;
    private GetAuditResponse auditResponse;

}
