package bg.softuni.warranty_tracker.mapper.user;

import org.springframework.stereotype.Component;

import bg.softuni.warranty_tracker.model.dto.user.UserRegisterRequest;
import bg.softuni.warranty_tracker.model.entity.user.User;
import bg.softuni.warranty_tracker.model.entity.user.UserRole;

import java.time.LocalDateTime;
import bg.softuni.warranty_tracker.model.dto.user.UserDto;
import bg.softuni.warranty_tracker.model.dto.user.UserProfileEditRequest;

@Component
public class UserMapper {

    public User toUser(UserRegisterRequest userRegisterRequest, String encodedPassword) {
        return userRegisterRequest == null ? null
                : User.builder()
                        .username(userRegisterRequest.getUsername())
                        .password(encodedPassword)
                        .email(userRegisterRequest.getEmail())
                        .firstName(userRegisterRequest.getFirstName())
                        .lastName(userRegisterRequest.getLastName())
                        .createdAt(LocalDateTime.now())
                        .role(UserRole.USER)
                        .build();
    }

    public UserDto toUserDto(User user) {
        return user == null ? null
                : UserDto.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .createdAt(user.getCreatedAt())
                        .role(user.getRole())
                        .build();
    }

    public User toUser(UserDto userDto) {
        return userDto == null ? null
                : User.builder()
                        .id(userDto.getId())
                        .username(userDto.getUsername())
                        .email(userDto.getEmail())
                        .firstName(userDto.getFirstName())
                        .lastName(userDto.getLastName())
                        .createdAt(userDto.getCreatedAt())
                        .role(userDto.getRole())
                        .build();
    }

    public UserProfileEditRequest toUserProfileEditRequest(UserDto userDto) {
        return userDto == null ? null
                : UserProfileEditRequest.builder()
                        .email(userDto.getEmail())
                        .firstName(userDto.getFirstName())
                        .lastName(userDto.getLastName())
                        .build();
    }
}
