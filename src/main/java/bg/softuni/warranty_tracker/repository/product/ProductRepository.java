package bg.softuni.warranty_tracker.repository.product;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import bg.softuni.warranty_tracker.model.entity.product.Product;

import java.util.List;
import java.util.UUID;
import java.util.Optional;
import bg.softuni.warranty_tracker.model.entity.user.User;


@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    List<Product> findByUser(User user);
    Optional<Product> findBySerialNumberAndUserId(String serialNumber, UUID userId);
    Optional<Product> findBySerialNumberAndUserIdAndIdNot(String serialNumber, UUID productId, UUID userId);
}
