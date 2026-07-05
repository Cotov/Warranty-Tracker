package bg.softuni.warranty_tracker.service.user;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import bg.softuni.warranty_tracker.repository.user.UserRepository;
import bg.softuni.warranty_tracker.security.UserPrincipal;
import bg.softuni.warranty_tracker.constant.ErrorMessages;
import bg.softuni.warranty_tracker.constant.ExceptionMessages;
import bg.softuni.warranty_tracker.model.dto.user.UserRegisterRequest;
import bg.softuni.warranty_tracker.model.dto.user.UserDto;
import bg.softuni.warranty_tracker.model.dto.user.UserLoginRequest;

import java.util.Optional;
import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import bg.softuni.warranty_tracker.mapper.user.UserMapper;
import bg.softuni.warranty_tracker.model.entity.user.User;
import lombok.extern.slf4j.Slf4j;
import bg.softuni.warranty_tracker.constant.LogMessages;
import bg.softuni.warranty_tracker.customExceptions.DuplicateEntityException;
import bg.softuni.warranty_tracker.customExceptions.ObjectNotFoundException;
import bg.softuni.warranty_tracker.customExceptions.UserException;

@Slf4j
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Transactional
    public UUID register(UserRegisterRequest userRegisterRequest) {

        userRepository.findByUsername(userRegisterRequest.getUsername()).ifPresent(user -> {
            throw new DuplicateEntityException(ErrorMessages.USERNAME_ALREADY_EXISTS);
        });

        userRepository.findByEmail(userRegisterRequest.getEmail()).ifPresent(user -> {
            throw new DuplicateEntityException(ErrorMessages.EMAIL_ALREADY_EXISTS);
        });

        String encodedPassword = passwordEncoder.encode(userRegisterRequest.getPassword());
        User user = userMapper.toUser(userRegisterRequest, encodedPassword);

        userRepository.save(user);
        log.info(LogMessages.USER_REGISTERED_SUCCESSFULLY, user.getUsername());
        return user.getId();
    }

    public UserDto getById(UUID uuid) {
        Optional<User> user = userRepository.findById(uuid);
        if (user.isEmpty()) {
            throw new ObjectNotFoundException(ExceptionMessages.USER_NOT_FOUND);
        }
        return userMapper.toUserDto(user.get());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return UserPrincipal.builder()
        .id(user.getId())
        .password(user.getPassword())
        .username(user.getUsername())
        .build();
    }
}
