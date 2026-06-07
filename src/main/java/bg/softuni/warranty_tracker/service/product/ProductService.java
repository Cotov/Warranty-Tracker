package bg.softuni.warranty_tracker.service.product;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bg.softuni.warranty_tracker.constant.Constants;
import bg.softuni.warranty_tracker.constant.ExceptionMessages;
import bg.softuni.warranty_tracker.mapper.product.ProductMapper;
import bg.softuni.warranty_tracker.mapper.user.UserMapper;
import bg.softuni.warranty_tracker.model.dto.product.ProductDto;
import bg.softuni.warranty_tracker.model.dto.product.RegisterProductRequest;
import bg.softuni.warranty_tracker.model.dto.user.UserDto;
import bg.softuni.warranty_tracker.model.dto.vendor.VendorDto;
import bg.softuni.warranty_tracker.model.entity.product.Product;
import bg.softuni.warranty_tracker.model.entity.user.User;
import bg.softuni.warranty_tracker.repository.product.ProductRepository;
import bg.softuni.warranty_tracker.service.vendor.VendorService;

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

        if (registerProductRequest.getVendorId().equals(Constants.CREATE_VENDOR_FLAG)) {
            VendorDto vendorDto = vendorService.createVendor(registerProductRequest.getRegisterVendorRequest());
        }
        //else

        UUID vendorUuid = UUID.fromString(registerProductRequest.getVendorId());
        VendorDto vendorDto = vendorService.getVendorById(vendorUuid);

        Product product = productMapper.toProduct(registerProductRequest, vendorDto, userDto);
        productRepository.save(product);
        return productMapper.toDto(product);
    }

}
