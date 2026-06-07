package bg.softuni.warranty_tracker.mapper.product;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import bg.softuni.warranty_tracker.model.dto.product.ProductDto;
import bg.softuni.warranty_tracker.model.entity.product.Product;
import bg.softuni.warranty_tracker.mapper.vendor.VendorMapper;
import bg.softuni.warranty_tracker.mapper.user.UserMapper;

@Component
public class ProductMapper {

    private final VendorMapper vendorMapper;
    private final UserMapper userMapper;

    public ProductMapper(VendorMapper vendorMapper, UserMapper userMapper) {
        this.vendorMapper = vendorMapper;
        this.userMapper = userMapper;
    }

    public List<Product> toProducts(List<ProductDto> productDtos){

        List<Product> productEntities = new ArrayList<>();
        for (ProductDto productDto : productDtos) {
            productEntities.add(toProduct(productDto));
        }
        return productEntities;
    }

    public Product toProduct(ProductDto productDto) {
        return Product.builder()
        .id(productDto.getId())
        .serialNumber(productDto.getSerialNumber())
        .description(productDto.getDescription())
        .purchaseDate(productDto.getPurchaseDate())
        .warrantyStartDate(productDto.getWarrantyStartDate())
        .warrantyEndDate(productDto.getWarrantyEndDate())
        .physicalReceiptLocation(productDto.getPhysicalReceiptLocation())
        .vendor(vendorMapper.toVendor(productDto.getVendor()))
        .user(userMapper.toUser(productDto.getUser()))
        .build();
    }

    public List<ProductDto> toDtos(List<Product> products) {
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : products) {
            productDtos.add(toDto(product));
        }
        return productDtos;
    }

    public ProductDto toDto(Product product) {
        return ProductDto.builder()
        .id(product.getId())
        .serialNumber(product.getSerialNumber())
        .description(product.getDescription())
        .purchaseDate(product.getPurchaseDate())
        .warrantyStartDate(product.getWarrantyStartDate())
        .warrantyEndDate(product.getWarrantyEndDate())
        .physicalReceiptLocation(product.getPhysicalReceiptLocation())
        .vendor(vendorMapper.toVendorDto(product.getVendor()))
        .user(userMapper.toUserDto(product.getUser()))
        .build();
    }

}
