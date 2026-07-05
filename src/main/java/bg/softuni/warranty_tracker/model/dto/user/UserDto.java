package bg.softuni.warranty_tracker.model.dto.user;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

import bg.softuni.warranty_tracker.model.entity.user.UserRole;

@Builder
@Data
public class UserDto {

    private UUID id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDateTime createdAt;
    private UserRole role;
}
