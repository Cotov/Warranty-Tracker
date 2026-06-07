package bg.softuni.warranty_tracker.mapper.vendor;

import org.springframework.stereotype.Component;

import bg.softuni.warranty_tracker.model.dto.vendor.VendorDto;
import bg.softuni.warranty_tracker.model.entity.vendor.Vendor;
import bg.softuni.warranty_tracker.mapper.contact.ContactMapper;

@Component
public class VendorMapper {

    private final ContactMapper contactMapper;

    public VendorMapper(ContactMapper contactMapper) {
        this.contactMapper = contactMapper;
    }

    public Vendor toVendor(VendorDto vendorDto) {
        return Vendor.builder()
        .id(vendorDto.getId())
        .name(vendorDto.getName())
        .contact(contactMapper.toContact(vendorDto.getContact()))
        .build();
    }

    public VendorDto toVendorDto(Vendor vendor) {
        return VendorDto.builder()
        .id(vendor.getId())
        .name(vendor.getName())
        .contact(contactMapper.toContactDto(vendor.getContact()))
        .build();
    }

}
