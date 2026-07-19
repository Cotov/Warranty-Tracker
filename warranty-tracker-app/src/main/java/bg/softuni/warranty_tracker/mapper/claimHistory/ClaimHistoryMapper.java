package bg.softuni.warranty_tracker.mapper.claimHistory;

import org.springframework.stereotype.Component;

import bg.softuni.warranty_tracker.constant.ExceptionMessages;
import bg.softuni.warranty_tracker.customExceptions.common.ObjectNotFoundException;
import bg.softuni.warranty_tracker.model.dto.product.ProductDto;
import bg.softuni.warranty_tracker.model.dto.warrantyClaim.ClaimDto;
import bg.softuni.warranty_tracker.model.dto.warrantyClaim.audit.ClaimHistoryDto;
import bg.softuni.warranty_tracker.model.dto.warrantyClaim.audit.GetAuditResponse;

@Component
public class ClaimHistoryMapper {

    public ClaimHistoryDto toClaimHistoryDto(ClaimDto claimDto, GetAuditResponse auditResponse) {
        ProductDto productDto = claimDto.getProduct();
        if (productDto == null) {
            throw new ObjectNotFoundException(ExceptionMessages.PRODUCT_NOT_FOUND);
        }
        return ClaimHistoryDto.builder()
                .productDescription(productDto.getDescription())
                .productSerialNumber(productDto.getSerialNumber())
                .warrantyEndDate(productDto.getWarrantyEndDate())
                .dateClaimFiled(claimDto.getDateFiled())
                .faultDescription(claimDto.getFaultDescription())
                .claimStatus(claimDto.getStatus())
                .auditResponse(auditResponse)
                .build();
    }

}
