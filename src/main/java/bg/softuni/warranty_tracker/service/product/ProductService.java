package bg.softuni.warranty_tracker.service.product;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bg.softuni.warranty_tracker.constant.Constants;
import bg.softuni.warranty_tracker.constant.LogMessages;
import bg.softuni.warranty_tracker.constant.ExceptionMessages;
import bg.softuni.warranty_tracker.mapper.product.ProductMapper;
import bg.softuni.warranty_tracker.mapper.user.UserMapper;
import bg.softuni.warranty_tracker.model.dto.product.EditProductRequest;
import bg.softuni.warranty_tracker.model.dto.product.ProductDto;
import bg.softuni.warranty_tracker.model.dto.product.RegisterProductRequest;
import bg.softuni.warranty_tracker.model.dto.user.UserDto;
import bg.softuni.warranty_tracker.model.dto.vendor.RegisterVendorRequest;
import bg.softuni.warranty_tracker.model.dto.vendor.VendorDto;
import bg.softuni.warranty_tracker.model.dto.warrantyClaim.ClaimDto;
import bg.softuni.warranty_tracker.model.entity.product.Product;
import bg.softuni.warranty_tracker.model.entity.user.User;
import bg.softuni.warranty_tracker.repository.product.ProductRepository;
import bg.softuni.warranty_tracker.service.vendor.VendorService;
import lombok.extern.slf4j.Slf4j;
import bg.softuni.warranty_tracker.service.warrantyClaim.ClaimService;

@Slf4j
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final UserMapper userMapper;
    private final ProductMapper productMapper;
    private final VendorService vendorService;
    private final ClaimService claimService;

    public ProductService(ProductRepository productRepository, UserMapper userMapper, ProductMapper productMapper,
            VendorService vendorService, @org.springframework.context.annotation.Lazy ClaimService claimService) {
        this.productRepository = productRepository;
        this.userMapper = userMapper;
        this.productMapper = productMapper;
        this.vendorService = vendorService;
        this.claimService = claimService;
    }

    public List<ProductDto> getAllProducts(UserDto userDto) {
        User userEntity = userMapper.toUser(userDto);
        List<Product> productEntities = productRepository.findByUser(userEntity);
        return productMapper.toDtos(productEntities);
    }

    @Transactional
    public ProductDto registerProduct(RegisterProductRequest registerProductRequest, UserDto userDto) {

        if (registerProductRequest == null || userDto == null) {
            throw new RuntimeException(ExceptionMessages.REGISTER_PRODUCT_FAILED);
        }

        if (productRepository.findBySerialNumber(registerProductRequest.getSerialNumber()).isPresent()) {
            throw new RuntimeException(ExceptionMessages.PRODUCT_ALREADY_EXISTS);
        }
        VendorDto vendorDto = resolveVendor(registerProductRequest, userDto);
        Product product = productMapper.toProduct(registerProductRequest, vendorDto, userDto);
        productRepository.save(product);
        log.info(LogMessages.PRODUCT_REGISTERED_SUCCESSFULLY);
        return productMapper.toDto(product);

    }

    @Transactional
    public void updateProduct(EditProductRequest editProductRequest, UserDto userDto) {
        if (editProductRequest == null || userDto == null) {
            throw new RuntimeException(ExceptionMessages.UPDATE_PRODUCT_FAILED);
        }
        Product existingProduct = productRepository.findById(editProductRequest.getId())
                .orElseThrow(() -> new RuntimeException(ExceptionMessages.PRODUCT_NOT_FOUND));

        if (productRepository
                .findBySerialNumberAndIdNot(editProductRequest.getSerialNumber(), editProductRequest.getId())
                .isPresent()) {
            throw new RuntimeException(ExceptionMessages.PRODUCT_ALREADY_EXISTS);
        }

        verifyProductUser(existingProduct, userDto.getId());

        VendorDto vendorDto = resolveVendor(editProductRequest, userDto);
        Product product = productMapper.toProduct(editProductRequest, vendorDto, userDto);
        productRepository.save(product);
        log.info(LogMessages.PRODUCT_UPDATED_SUCCESSFULLY, product.getId());
    }

    @Transactional
    public void deleteProductById(String id, UserDto userDto) {

        Product product = productRepository.findById(UUID.fromString(id)).orElse(null);

        verifyProductUser(product, userDto.getId());
        List<ClaimDto> claims = claimService.getClaims(product.getId().toString(), userDto);
        if (claimService.hasActiveClaim(claims)) {
            throw new RuntimeException(ExceptionMessages.PRODUCT_HAS_ACTIVE_CLAIM);
        } else if (claims.size() > 0) {
            claims.forEach(claim -> claimService.deleteClaimById(claim.getId().toString(), userDto));
        }

        productRepository.delete(product);
        log.info("Product deleted: " + product.getId());
    }

    public ProductDto getById(String productId, UserDto userDto) {
        Product product = productRepository.findById(UUID.fromString(productId)).orElse(null);
        verifyProductUser(product, userDto.getId());
        return productMapper.toDto(product);
    }

    private VendorDto resolveVendor(RegisterProductRequest registerProductRequest, UserDto userDto) {
        VendorDto vendorDto;
        if (registerProductRequest == null || userDto == null) {
            throw new RuntimeException(ExceptionMessages.VENDOR_RESOLUTION_FAILED);
        }
        if (registerProductRequest.getVendorId().equals(Constants.CREATE_VENDOR_FLAG)) {
            vendorDto = vendorService.createVendor(registerProductRequest.getRegisterVendorRequest(), userDto);
        } else {
            UUID vendorUuid;
            try {
                vendorUuid = UUID.fromString(registerProductRequest.getVendorId());
            } catch (Exception e) {
                throw new RuntimeException(ExceptionMessages.FAILED_TO_PARSE_UUID);
            }
            vendorDto = vendorService.getVendorByIdAndUserId(vendorUuid, userDto);
        }
        return vendorDto;
    }

    private VendorDto resolveVendor(EditProductRequest editProductRequest, UserDto userDto) {
        VendorDto vendorDto;

        if (editProductRequest.getVendorId() == null || userDto == null) {
            throw new RuntimeException(ExceptionMessages.VENDOR_RESOLUTION_FAILED);
        }
        if (editProductRequest.getVendorId().equals(Constants.CREATE_VENDOR_FLAG)) {
            vendorDto = vendorService.createVendor(editProductRequest.getRegisterVendorRequest(), userDto);
        } else {
            UUID vendorUuid;
            try {
                vendorUuid = UUID.fromString(editProductRequest.getVendorId());
            } catch (Exception e) {
                throw new RuntimeException(ExceptionMessages.FAILED_TO_PARSE_UUID);
            }
            vendorDto = vendorService.getVendorByIdAndUserId(vendorUuid, userDto);
        }
        return vendorDto;
    }

    // helpers
    // todo refactor to use UUID
    public void verifyProductUser(Product product, UUID userId) {
        if (product == null) {
            throw new RuntimeException(ExceptionMessages.PRODUCT_NOT_FOUND);
        } else if (!userId.equals(product.getUser().getId())) {
            throw new RuntimeException(ExceptionMessages.SESSION_AND_USER_MISMATCH);
        }
    }

    public void verifyProductUser(UUID productId, UUID userId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException(ExceptionMessages.PRODUCT_NOT_FOUND));
        verifyProductUser(product, userId);
    }

    public List<VendorDto> getVendorsForEditProduct(String productId, UserDto userDto) {
        ProductDto productDto = getById(productId, userDto);
        List<VendorDto> vendors = new ArrayList<>(vendorService.getAllByUser(userDto));
        vendors.removeIf(vendor -> vendor.getId().equals(productDto.getVendor().getId()));
        return vendors;
    }

    public EditProductRequest getEditProductRequest(ProductDto productDto) {
        EditProductRequest editProductRequest = productMapper.toEditProductRequest(productDto);
        if (editProductRequest == null) {
            throw new RuntimeException(ExceptionMessages.FAILED_TO_MAP_PRODUCT_TO_EDIT_REQUEST);
        }
        editProductRequest.setRegisterVendorRequest(new RegisterVendorRequest());
        return editProductRequest;
    }
}
