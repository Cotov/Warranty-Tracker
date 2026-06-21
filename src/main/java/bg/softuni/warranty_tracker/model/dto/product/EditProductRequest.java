package bg.softuni.warranty_tracker.model.dto.product;

import java.time.LocalDate;
import java.util.UUID;

import bg.softuni.warranty_tracker.model.dto.vendor.RegisterVendorRequest;
import bg.softuni.warranty_tracker.validation.ValidateNewVendor;
import bg.softuni.warranty_tracker.validation.ValidateProductDates;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@ValidateNewVendor
@ValidateProductDates
public class EditProductRequest extends ProductFormRequest {

    private String vendorName;
    private UUID id;

    public EditProductRequest(UUID id, String productDescription, String serialNumber, LocalDate purchaseDate,
            LocalDate warrantyStart, LocalDate warrantyEnd, String receiptLocation, String vendorId,
            RegisterVendorRequest registerVendorRequest, String vendorName) {
        super(productDescription, serialNumber, purchaseDate, warrantyStart, warrantyEnd, receiptLocation, vendorId,
                registerVendorRequest);
        this.vendorName = vendorName;
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }
}
