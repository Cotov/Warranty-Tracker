package bg.softuni.warranty_tracker.model.dto.warrantyClaim.audit;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetAuditResponseEntry {

    private LocalDateTime changedAt;
    private String previousStatus;
    private String newStatus;

}
