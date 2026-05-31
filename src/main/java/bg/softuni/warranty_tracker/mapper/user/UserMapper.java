package bg.softuni.warranty_tracker.mapper.user;

import org.springframework.stereotype.Component;

import bg.softuni.warranty_tracker.model.dto.user.UserRegisterRequest;
import bg.softuni.warranty_tracker.model.entity.user.User;
import java.time.LocalDateTime;
import bg.softuni.warranty_tracker.model.dto.user.UserDto;

@Component
public class UserMapper {

    public User toUser(UserRegisterRequest userRegisterRequest, String encodedPassword) {
        return User.builder()
                .username(userRegisterRequest.getUsername())
                .password(encodedPassword)
                .email(userRegisterRequest.getEmail())
                .firstName(userRegisterRequest.getFirstName())
                .lastName(userRegisterRequest.getLastName())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public UserDto toUserDto(User user) {
        return user == null ? null : 
         UserDto.builder()
            .id(user.getId())
            .username(user.getUsername())
            .email(user.getEmail())
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .createdAt(user.getCreatedAt()) // needed? 
            .build();
    }
}
