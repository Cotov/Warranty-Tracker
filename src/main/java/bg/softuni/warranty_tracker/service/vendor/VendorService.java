package bg.softuni.warranty_tracker.service.vendor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bg.softuni.warranty_tracker.constant.ExceptionMessages;
import bg.softuni.warranty_tracker.mapper.product.ProductMapper;
import bg.softuni.warranty_tracker.mapper.user.UserMapper;
import bg.softuni.warranty_tracker.mapper.vendor.VendorMapper;
import bg.softuni.warranty_tracker.model.dto.product.RegisterProductRequest;
import bg.softuni.warranty_tracker.model.dto.user.UserDto;
import bg.softuni.warranty_tracker.model.dto.vendor.RegisterVendorRequest;
import bg.softuni.warranty_tracker.model.dto.vendor.VendorDto;
import bg.softuni.warranty_tracker.model.entity.user.User;
import bg.softuni.warranty_tracker.model.entity.vendor.Vendor;
import bg.softuni.warranty_tracker.repository.vendor.VendorRepository;

@Service
@Transactional
public class VendorService {

    private final VendorRepository vendorRepository;
    private final UserMapper userMapper;
    private final VendorMapper vendorMapper;


    public VendorService(VendorRepository vendorRepository, UserMapper userMapper, VendorMapper vendorMapper) {
        this.vendorRepository = vendorRepository;
        this.userMapper = userMapper;
        this.vendorMapper = vendorMapper;
    }

    public List<VendorDto> getAllByUser(UserDto userDto) {
        List<VendorDto> vendorDtos = new ArrayList<>();
        if (userDto == null) {
            return vendorDtos;
        }

        User user = userMapper.toUser(userDto);
        List<Vendor> vendors = vendorRepository.findByUser(user);
        return vendors.stream().map(vendorMapper::toVendorDto).toList();
    }

    public VendorDto getVendorById(UUID uuid) {
        if (uuid == null) {
            return null;
        }
        Vendor vendor = vendorRepository.findById(uuid).orElse(null);

        if (vendor == null) {
            throw new RuntimeException(ExceptionMessages.VENDOR_NOT_FOUND);
        }
        return vendorMapper.toVendorDto(vendor);
    }

    public VendorDto createVendor(RegisterVendorRequest registerVendorRequest){
        if (registerVendorRequest == null) {
            throw new RuntimeException(ExceptionMessages.VENDOR_CREATION_FAILED);
        }

        Vendor vendor = vendorMapper.toVendor()
    }

}
