package bg.softuni.warranty_tracker.repository.warrantyClaim;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import bg.softuni.warranty_tracker.model.entity.warrantyClaim.Claim;
import java.util.UUID;
import java.util.List;
import bg.softuni.warranty_tracker.model.entity.product.Product;


@Repository
public interface ClaimRepository extends JpaRepository<Claim, UUID> {

    List<Claim> findByProduct(Product product);

}
