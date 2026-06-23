package bg.softuni.warranty_tracker.model.dto.product;

import java.time.LocalDate;
import java.util.UUID;

import bg.softuni.warranty_tracker.model.dto.user.UserDto;
import bg.softuni.warranty_tracker.model.dto.vendor.VendorDto;
import bg.softuni.warranty_tracker.mapper.product.WarrantyStatus;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProductDto {

    private UUID id;
    private String serialNumber;
    private String description;
    private LocalDate purchaseDate;
    private LocalDate warrantyStartDate;
    private LocalDate warrantyEndDate;
    private String physicalReceiptLocation;
    private VendorDto vendor;
    private UserDto user;
    private WarrantyStatus warrantyStatus;
}
