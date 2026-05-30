package bg.softuni.warranty_tracker.repository.user;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import bg.softuni.warranty_tracker.model.entity.user.User;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {



}
