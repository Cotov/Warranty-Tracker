package bg.softuni.warranty_tracker.model.dto.contact;

import lombok.Builder;
import lombok.Data;
import java.util.UUID;

@Builder
@Data
public class ContactDto {

    private UUID id;
    private String email;
    private String phone;
    private String website;
}
