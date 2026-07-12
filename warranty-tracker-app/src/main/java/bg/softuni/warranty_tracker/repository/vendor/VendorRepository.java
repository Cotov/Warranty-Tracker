package bg.softuni.warranty_tracker.repository.vendor;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import bg.softuni.warranty_tracker.model.entity.vendor.Vendor;
import java.util.UUID;
import java.util.List;
import bg.softuni.warranty_tracker.model.entity.user.User;


@Repository
public interface VendorRepository extends JpaRepository<Vendor, UUID> {
    List<Vendor> findByUser(User user);

}
