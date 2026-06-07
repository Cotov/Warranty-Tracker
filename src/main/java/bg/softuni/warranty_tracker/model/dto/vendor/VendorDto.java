package bg.softuni.warranty_tracker.model.dto.vendor;

import lombok.Builder;
import lombok.Data;
import java.util.UUID;

import bg.softuni.warranty_tracker.model.dto.contact.ContactDto;

@Builder
@Data
public class VendorDto {
    private UUID id;
    private String name;
    private ContactDto contact;
}
