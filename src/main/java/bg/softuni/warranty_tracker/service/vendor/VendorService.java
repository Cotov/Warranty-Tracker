package bg.softuni.warranty_tracker.service.vendor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bg.softuni.warranty_tracker.constant.ExceptionMessages;
import bg.softuni.warranty_tracker.constant.LogMessages;
import bg.softuni.warranty_tracker.customExceptions.DataMapException;
import bg.softuni.warranty_tracker.customExceptions.InvalidSessionException;
import bg.softuni.warranty_tracker.customExceptions.ObjectNotFoundException;
import bg.softuni.warranty_tracker.mapper.user.UserMapper;
import bg.softuni.warranty_tracker.mapper.vendor.VendorMapper;
import bg.softuni.warranty_tracker.model.dto.user.UserDto;
import bg.softuni.warranty_tracker.model.dto.vendor.RegisterVendorRequest;
import bg.softuni.warranty_tracker.model.dto.vendor.VendorDto;
import bg.softuni.warranty_tracker.model.entity.user.User;
import bg.softuni.warranty_tracker.model.entity.vendor.Vendor;
import bg.softuni.warranty_tracker.repository.vendor.VendorRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
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

    public VendorDto getVendorByIdAndUserId(UUID vendorUuid, UserDto userDto) {
        if (vendorUuid == null) {
            return null;
        }

        Vendor vendor = vendorRepository.findById(vendorUuid).orElse(null);
        if (vendor == null) {
            throw new ObjectNotFoundException(ExceptionMessages.VENDOR_NOT_FOUND);
        }

        if (!vendor.getUser().getId().equals(userDto.getId())) {
            throw new InvalidSessionException(String.format(ExceptionMessages.VENDOR_AND_SESSION_USER_MISMATCH,
                    vendorUuid, userDto.getId()));
        }

        return vendorMapper.toVendorDto(vendor);
    }

    @Transactional
    public VendorDto createVendor(RegisterVendorRequest registerVendorRequest, UserDto userDto) {
        if (registerVendorRequest == null) {
            throw new DataMapException(ExceptionMessages.VENDOR_CREATION_FAILED);
        }

        Vendor vendor = vendorMapper.toVendor(registerVendorRequest, userDto);
        vendorRepository.save(vendor);
        log.info(LogMessages.VENDOR_REGISTERED_SUCCESSFULLY);
        return vendorMapper.toVendorDto(vendor);
    }

}
