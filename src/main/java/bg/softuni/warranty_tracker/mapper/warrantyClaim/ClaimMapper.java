package bg.softuni.warranty_tracker.mapper.warrantyClaim;

import org.springframework.stereotype.Component;

import bg.softuni.warranty_tracker.model.dto.warrantyClaim.ClaimDto;
import bg.softuni.warranty_tracker.model.entity.warrantyClaim.Claim;
import bg.softuni.warranty_tracker.mapper.product.ProductMapper;

@Component
public class ClaimMapper {

    private final ProductMapper productMapper;

    public ClaimMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    public Claim toClaim(ClaimDto claimDto) {
        return claimDto == null ? null
                : Claim.builder()
                        .id(claimDto.getId())
                        .status(claimDto.getStatus())
                        .faultDescription(claimDto.getFaultDescription())
                        .dateFiled(claimDto.getDateFiled())
                        .product(productMapper.toProduct(claimDto.getProduct()))
                        .build();
    }

    public ClaimDto toClaimDto(Claim claim) {
        return claim == null ? null
                : ClaimDto.builder()
                        .id(claim.getId())
                        .status(claim.getStatus())
                        .faultDescription(claim.getFaultDescription())
                        .dateFiled(claim.getDateFiled())
                        .product(productMapper.toDto(claim.getProduct()))
                        .build();
    }
}
