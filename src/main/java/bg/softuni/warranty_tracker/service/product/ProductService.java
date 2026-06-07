package bg.softuni.warranty_tracker.service.product;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bg.softuni.warranty_tracker.mapper.product.ProductMapper;
import bg.softuni.warranty_tracker.mapper.user.UserMapper;
import bg.softuni.warranty_tracker.model.dto.product.ProductDto;
import bg.softuni.warranty_tracker.model.dto.user.UserDto;
import bg.softuni.warranty_tracker.model.entity.product.Product;
import bg.softuni.warranty_tracker.model.entity.user.User;
import bg.softuni.warranty_tracker.repository.product.ProductRepository;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final UserMapper userMapper;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, UserMapper userMapper, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.userMapper = userMapper;
        this.productMapper = productMapper;
    }

    public List<ProductDto> getAllProducts(UserDto userDto) {
        User userEntity = userMapper.toUser(userDto);
        List<Product> productEntities = productRepository.findByUser(userEntity);
        return productMapper.toDtos(productEntities);
    }

}
