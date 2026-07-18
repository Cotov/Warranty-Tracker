package bg.softuni.warranty_tracker.service.audit;

import java.util.UUID;

import org.springframework.stereotype.Service;

import bg.softuni.warranty_tracker.model.dto.user.UserDto;
import bg.softuni.warranty_tracker.model.dto.product.ProductDto;
import bg.softuni.warranty_tracker.model.dto.warrantyClaim.ClaimDto;
import bg.softuni.warranty_tracker.model.dto.warrantyClaim.audit.ClaimHistoryDto;
import bg.softuni.warranty_tracker.model.dto.warrantyClaim.audit.GetAuditResponse;
import bg.softuni.warranty_tracker.service.warrantyClaim.ClaimService;
import bg.softuni.warranty_tracker.service.user.UserService;
import bg.softuni.warranty_tracker.mapper.claimHistory.ClaimHistoryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClaimHistoryService {

    private final AuditService auditService;
    private final ClaimService claimService;
    private final UserService userService;
    private final ClaimHistoryMapper claimHistoryMapper;

    public ClaimHistoryDto getClaimHistory(UUID claimId, UUID userId) {
        UserDto userDto = userService.getById(userId);
        ClaimDto claimDto = claimService.getById(claimId.toString(), userDto);
        GetAuditResponse auditResponse = auditService.getAuditEntries(claimId);

        return claimHistoryMapper.toClaimHistoryDto(claimDto, auditResponse);
    }

}
