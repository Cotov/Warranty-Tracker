package bg.softuni.warranty_tracker.service.product;

import java.util.List;
import java.util.UUID;

import javax.management.RuntimeErrorException;

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
import bg.softuni.warranty_tracker.model.dto.vendor.VendorDto;
import bg.softuni.warranty_tracker.model.entity.product.Product;
import bg.softuni.warranty_tracker.model.entity.user.User;
import bg.softuni.warranty_tracker.repository.product.ProductRepository;
import bg.softuni.warranty_tracker.service.vendor.VendorService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final UserMapper userMapper;
    private final ProductMapper productMapper;
    private final VendorService vendorService;

    public ProductService(ProductRepository productRepository, UserMapper userMapper, ProductMapper productMapper,
            VendorService vendorService) {
        this.productRepository = productRepository;
        this.userMapper = userMapper;
        this.productMapper = productMapper;
        this.vendorService = vendorService;
    }

    public List<ProductDto> getAllProducts(UserDto userDto) {
        User userEntity = userMapper.toUser(userDto);
        List<Product> productEntities = productRepository.findByUser(userEntity);
        return productMapper.toDtos(productEntities);
    }

    public ProductDto registerProduct(RegisterProductRequest registerProductRequest, UserDto userDto) {

        if (registerProductRequest == null || userDto == null) {
            throw new RuntimeException(ExceptionMessages.REGISTER_PRODUCT_FAILED);
        }

        VendorDto vendorDto;
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

        Product product = productMapper.toProduct(registerProductRequest, vendorDto, userDto);
        productRepository.save(product);
        log.info(LogMessages.PRODUCT_REGISTERED_SUCCESSFULLY);
        return productMapper.toDto(product);

    }

    public void deleteProductById(String id, UserDto userDto) {

        Product product = productRepository.findById(UUID.fromString(id)).orElse(null);

        verifyProductUser(product, userDto.getId());

        productRepository.delete(product);
        log.info("Product deleted: " + product.getId());
    }

    public ProductDto getById(String id, UserDto userDto) {
        Product product = productRepository.findById(UUID.fromString(id)).orElse(null);
        verifyProductUser(product, userDto.getId());
        return productMapper.toDto(product);
    }

    public EditProductRequest mapToEditProductRequest(ProductDto productDto) {
        

    }


    //helpers
    private void verifyProductUser(Product product, UUID userId) {
        if (product == null) {
            throw new RuntimeException(ExceptionMessages.PRODUCT_NOT_FOUND);
        } else if (!userId.equals(product.getUser().getId())) {
            throw new RuntimeException(ExceptionMessages.SESSION_AND_USER_MISMATCH);
        }
    }

}
