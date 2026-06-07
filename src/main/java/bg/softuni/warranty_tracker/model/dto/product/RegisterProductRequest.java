package bg.softuni.warranty_tracker.model.dto.product;

import java.time.LocalDate;

import bg.softuni.warranty_tracker.model.dto.vendor.RegisterVendorRequest;
import bg.softuni.warranty_tracker.validation.ValidateNewVendor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ValidateNewVendor
public class RegisterProductRequest {

    @NotBlank // todo messages
    private String productDescription;

    @NotBlank
    private String serialNumber;

    @NotNull
    private LocalDate purchaseDate;

    @NotNull
    private LocalDate warrantyStart;

    @NotNull
    private LocalDate warrantyEnd;

    @NotBlank
    private String receiptLocation;

    @NotBlank
    private String vendorId;

    private RegisterVendorRequest registerVendorRequest;
}
