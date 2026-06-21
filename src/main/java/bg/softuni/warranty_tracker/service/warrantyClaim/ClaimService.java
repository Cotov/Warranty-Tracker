package bg.softuni.warranty_tracker.service.warrantyClaim;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bg.softuni.warranty_tracker.model.dto.user.UserDto;
import bg.softuni.warranty_tracker.model.dto.warrantyClaim.AddClaimRequest;
import bg.softuni.warranty_tracker.model.dto.warrantyClaim.ClaimDto;
import bg.softuni.warranty_tracker.model.entity.product.Product;
import bg.softuni.warranty_tracker.model.entity.warrantyClaim.Claim;
import bg.softuni.warranty_tracker.model.entity.warrantyClaim.ClaimStatus;
import bg.softuni.warranty_tracker.repository.product.ProductRepository;
import bg.softuni.warranty_tracker.repository.warrantyClaim.ClaimRepository;
import lombok.extern.slf4j.Slf4j;
import bg.softuni.warranty_tracker.constant.ExceptionMessages;
import bg.softuni.warranty_tracker.mapper.product.ProductMapper;
import bg.softuni.warranty_tracker.mapper.warrantyClaim.ClaimMapper;

@Slf4j
@Service
public class ClaimService {

    private final ClaimRepository claimRepository;
    private final ProductMapper productMapper;
    private final ClaimMapper claimMapper;
    private final ProductRepository productRepository;

    public ClaimService(ClaimRepository claimRepository, ProductMapper productMapper,
            ClaimMapper claimMapper, ProductRepository productRepository) {
        this.claimRepository = claimRepository;
        this.productMapper = productMapper;
        this.claimMapper = claimMapper;
        this.productRepository = productRepository;
    }

    public List<ClaimDto> getClaims(String productId, UserDto userDto) {
        List<Claim> claims = claimRepository.findByProductId(UUID.fromString(productId));
        List<ClaimDto> claimDtos = claims.stream().map(claimMapper::toClaimDto).collect(Collectors.toList());
        return claimDtos;
    }

    @Transactional
    public void addClaim(AddClaimRequest addClaimRequest) {
        if (addClaimRequest == null || addClaimRequest.getProduct() == null) {
            throw new RuntimeException(ExceptionMessages.ADD_CLAIM_FAILED);
        }

        UUID productId = addClaimRequest.getProduct().getId();
        List<ClaimDto> claimDtos = getClaims(productId.toString(),
                addClaimRequest.getProduct().getUser());

        boolean activeClaimExists = hasActiveClaim(claimDtos);
        if (activeClaimExists) {
            throw new RuntimeException(ExceptionMessages.ACTIVE_CLAIM_EXISTS);
        }

        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            throw new RuntimeException(ExceptionMessages.PRODUCT_NOT_FOUND);
        }

        addClaimRequest.setProduct(productMapper.toDto(product));
        Claim claim = claimMapper.toClaim(addClaimRequest);
        claimRepository.save(claim);
        log.info("Claim added successfully: {}", claim.getId());
    }

    public boolean hasActiveClaim(List<ClaimDto> claimDtos) {
        return claimDtos.stream().anyMatch(
            claimDto -> claimDto.getStatus() == ClaimStatus.PENDING || claimDto.getStatus() == ClaimStatus.ACTIVE);
    }
}
