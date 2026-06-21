package bg.softuni.warranty_tracker.model.dto.product;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import bg.softuni.warranty_tracker.constant.ValidationMessages;
import bg.softuni.warranty_tracker.model.dto.vendor.RegisterVendorRequest;
import bg.softuni.warranty_tracker.validation.ValidateNewVendor;
import bg.softuni.warranty_tracker.validation.ValidateProductDates;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ValidateNewVendor
@ValidateProductDates
@Builder
public class ProductFormRequest {

    @NotBlank(message = ValidationMessages.DESCRIPTION_REQUIRED)
    private String productDescription;

    @NotBlank(message = ValidationMessages.SERIAL_NUMBER_REQUIRED)
    private String serialNumber;

    @NotNull(message = ValidationMessages.PURCHASE_DATE_REQUIRED)
    @PastOrPresent(message = ValidationMessages.PURCHASE_DATE_PAST_OR_PRESENT)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate purchaseDate;

    @NotNull(message = ValidationMessages.WARRANTY_START_DATE_REQUIRED)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate warrantyStart;

    @NotNull(message = ValidationMessages.WARRANTY_END_DATE_REQUIRED)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate warrantyEnd;

    @NotBlank(message = ValidationMessages.PHYSICAL_RECEIPT_LOCATION_REQUIRED)
    private String receiptLocation;

    private String vendorId;

    private RegisterVendorRequest registerVendorRequest;
}
