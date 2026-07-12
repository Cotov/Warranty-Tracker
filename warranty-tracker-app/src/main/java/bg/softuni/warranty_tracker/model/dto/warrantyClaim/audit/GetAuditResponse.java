package bg.softuni.warranty_tracker.model.dto.warrantyClaim.audit;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetAuditResponse {

    private List<GetAuditResponseEntry> auditEntries;

}
