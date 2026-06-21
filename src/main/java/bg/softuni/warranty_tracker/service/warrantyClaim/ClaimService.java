package bg.softuni.warranty_tracker.service.warrantyClaim;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bg.softuni.warranty_tracker.model.dto.product.ProductDto;
import bg.softuni.warranty_tracker.model.dto.user.UserDto;
import bg.softuni.warranty_tracker.model.dto.warrantyClaim.ClaimDto;
import bg.softuni.warranty_tracker.model.entity.product.Product;
import bg.softuni.warranty_tracker.model.entity.warrantyClaim.Claim;
import bg.softuni.warranty_tracker.repository.warrantyClaim.ClaimRepository;
import bg.softuni.warranty_tracker.service.product.ProductService;
import bg.softuni.warranty_tracker.mapper.product.ProductMapper;
import bg.softuni.warranty_tracker.mapper.warrantyClaim.ClaimMapper;

@Service
@Transactional
public class ClaimService {

    private final ClaimRepository claimRepository;
    private final ProductService productService;
    private final ProductMapper productMapper;
    private final ClaimMapper claimMapper;

    public ClaimService(ClaimRepository claimRepository, ProductService productService, ProductMapper productMapper, ClaimMapper claimMapper) {
        this.claimRepository = claimRepository;
        this.productService = productService;
        this.productMapper = productMapper;
        this.claimMapper = claimMapper;
    }

    public List<ClaimDto> getClaims(String productId, UserDto userDto) {
        ProductDto productDto = productService.getById(productId, userDto);
        Product product = productMapper.toProduct(productDto);
        List<Claim> claims = claimRepository.findByProduct(product);
        List<ClaimDto> claimDtos = claims.stream().map(claimMapper::toClaimDto).collect(Collectors.toList());
        return claimDtos;
    }
}
