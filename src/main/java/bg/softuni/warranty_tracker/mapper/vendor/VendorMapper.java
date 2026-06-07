package bg.softuni.warranty_tracker.mapper.vendor;

import org.springframework.stereotype.Component;

import bg.softuni.warranty_tracker.model.dto.vendor.VendorDto;
import bg.softuni.warranty_tracker.model.entity.vendor.Vendor;
import bg.softuni.warranty_tracker.mapper.contact.ContactMapper;
import bg.softuni.warranty_tracker.mapper.user.UserMapper;

@Component
public class VendorMapper {

    private final ContactMapper contactMapper;
    private final UserMapper userMapper;

    public VendorMapper(ContactMapper contactMapper, UserMapper userMapper) {
        this.contactMapper = contactMapper;
        this.userMapper = userMapper;
    }

    public Vendor toVendor(VendorDto vendorDto) {
        return vendorDto == null ? null
                : Vendor.builder()
                        .id(vendorDto.getId())
                        .name(vendorDto.getName())
                        .contact(contactMapper.toContact(vendorDto.getContact()))
                        .user(userMapper.toUser(vendorDto.getUser()))
                        .build();
    }

    public VendorDto toVendorDto(Vendor vendor) {
        return vendor == null ? null : VendorDto.builder()
                .id(vendor.getId())
                .name(vendor.getName())
                .contact(contactMapper.toContactDto(vendor.getContact()))
                .user(userMapper.toUserDto(vendor.getUser()))
                .build();
    }

    

}
