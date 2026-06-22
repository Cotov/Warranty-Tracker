package bg.softuni.warranty_tracker.service.warrantyClaim;

import bg.softuni.warranty_tracker.service.product.ProductService;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bg.softuni.warranty_tracker.model.dto.product.ProductDto;
import bg.softuni.warranty_tracker.model.dto.user.UserDto;
import bg.softuni.warranty_tracker.model.dto.warrantyClaim.AddClaimRequest;
import bg.softuni.warranty_tracker.model.dto.warrantyClaim.ClaimDto;
import bg.softuni.warranty_tracker.model.dto.warrantyClaim.EditClaimRequest;
import bg.softuni.warranty_tracker.model.entity.warrantyClaim.Claim;
import bg.softuni.warranty_tracker.model.entity.warrantyClaim.ClaimStatus;
import bg.softuni.warranty_tracker.repository.warrantyClaim.ClaimRepository;
import lombok.extern.slf4j.Slf4j;
import bg.softuni.warranty_tracker.constant.ExceptionMessages;
import bg.softuni.warranty_tracker.mapper.product.ProductMapper;
import bg.softuni.warranty_tracker.mapper.warrantyClaim.ClaimMapper;

@Slf4j
@Service
public class ClaimService {

    private final ProductService productService;
    private final ClaimRepository claimRepository;
    private final ClaimMapper claimMapper;

    public ClaimService(ClaimRepository claimRepository, ProductMapper productMapper,
            ClaimMapper claimMapper, ProductService productService) {
        this.claimRepository = claimRepository;
        this.claimMapper = claimMapper;
        this.productService = productService;
    }

    public List<ClaimDto> getClaims(String productId, UserDto userDto) {
        ProductDto productDto = productService.getById(productId, userDto);
        productService.verifyProductUser(productDto.getId(), userDto.getId());
        List<Claim> claims = claimRepository.findByProductId(UUID.fromString(productId));
        List<ClaimDto> claimDtos = claims.stream().map(claimMapper::toClaimDto).collect(Collectors.toList());
        return claimDtos;
    }

    @Transactional
    public void addClaim(AddClaimRequest addClaimRequest, UserDto userDto) {
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

        // Product product = productRepository.findById(productId).orElse(null);
        ProductDto productDto = productService.getById(productId.toString(), userDto);
        if (productDto == null) {
            throw new RuntimeException(ExceptionMessages.PRODUCT_NOT_FOUND);
        }

        addClaimRequest.setProduct(productDto);
        Claim claim = claimMapper.toClaim(addClaimRequest);
        claimRepository.save(claim);
        log.info("Claim added successfully: {}", claim.getId());
    }

    public boolean hasActiveClaim(List<ClaimDto> claimDtos) {
        return claimDtos.stream().anyMatch(
                claimDto -> claimDto.getStatus() == ClaimStatus.PENDING || claimDto.getStatus() == ClaimStatus.ACTIVE);
    }

    public ClaimDto getById(String claimId, UserDto userDto) {
        Claim claim = claimRepository.findById(UUID.fromString(claimId))
                .orElseThrow(() -> new RuntimeException(ExceptionMessages.CLAIM_NOT_FOUND));
        verifyClaimUser(claim, userDto.getId());
        return claimMapper.toClaimDto(claim);
    }

    public EditClaimRequest getEditClaimRequest(ClaimDto claimDto) {
        return claimMapper.toEditClaimRequest(claimDto);
    }

    @Transactional
    public void updateClaim(EditClaimRequest editClaimRequest, UserDto userDto) {
        if (editClaimRequest == null || userDto == null) {
            throw new RuntimeException(ExceptionMessages.UPDATE_CLAIM_FAILED);
        }
        Claim claim = claimRepository.findById(editClaimRequest.getId())
                .orElseThrow(() -> new RuntimeException(ExceptionMessages.CLAIM_NOT_FOUND));
        verifyClaimUser(claim, userDto.getId());

        ClaimStatus currentStatus = claim.getStatus();
        if (INVALID_STATUS_TRANSITIONS.get(currentStatus).contains(editClaimRequest.getStatus())) {
            throw new RuntimeException(ExceptionMessages.INVALID_STATUS_TRANSITION);
        }

        claim.setStatus(editClaimRequest.getStatus());
        claim.setFaultDescription(editClaimRequest.getFaultDescription());
        if (editClaimRequest.getStatus() == ClaimStatus.PENDING && currentStatus != ClaimStatus.PENDING) {
            claim.setDateFiled(LocalDate.now());
        }
        claimRepository.save(claim);
        log.info("Claim updated successfully: {}", claim.getId());
    }

    private void verifyClaimUser(Claim claim, UUID userId) {

        if (claim == null || userId == null) {
            throw new RuntimeException(ExceptionMessages.CLAIM_NOT_FOUND);
        }
        productService.verifyProductUser(claim.getProduct(), userId);
    }

    private static final Map<ClaimStatus, Set<ClaimStatus>> INVALID_STATUS_TRANSITIONS = Map.of(
        ClaimStatus.PENDING, Set.of(ClaimStatus.RESOLVED),
        ClaimStatus.ACTIVE, Set.of(ClaimStatus.PENDING),
        ClaimStatus.REJECTED, Set.of(ClaimStatus.ACTIVE, ClaimStatus.RESOLVED),
        ClaimStatus.RESOLVED, Set.of(ClaimStatus.ACTIVE, ClaimStatus.REJECTED)
    );

    public Set<ClaimStatus> getValidStatusTransitions(ClaimStatus currentStatus) {
        Set<ClaimStatus> validStatusTransitions = new HashSet<>();
        validStatusTransitions.add(currentStatus);
        if (currentStatus == ClaimStatus.PENDING) {
            validStatusTransitions.add(ClaimStatus.REJECTED);
            validStatusTransitions.add(ClaimStatus.ACTIVE);
        } else if (currentStatus == ClaimStatus.ACTIVE) {
            validStatusTransitions.add(ClaimStatus.RESOLVED);
            validStatusTransitions.add(ClaimStatus.REJECTED);
        } else if (currentStatus == ClaimStatus.REJECTED) {
            validStatusTransitions.add(ClaimStatus.PENDING);
        } else if (currentStatus == ClaimStatus.RESOLVED) {
            validStatusTransitions.add(ClaimStatus.PENDING);
        }
        return validStatusTransitions;
    }
}
