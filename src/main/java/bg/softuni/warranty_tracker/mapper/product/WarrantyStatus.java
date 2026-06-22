package bg.softuni.warranty_tracker.mapper.product;

import java.time.LocalDate;

import bg.softuni.warranty_tracker.model.entity.product.Product;

public enum WarrantyStatus {
    ACTIVE,
    EXPIRED,
    EXPIRING_SOON;
    
    private static final int EXPIRING_SOON_DAYS = 60;

    public static WarrantyStatus getWarrantyStatus(Product product) {
        LocalDate today = LocalDate.now();
        LocalDate end = product.getWarrantyEndDate();
        if (end.isBefore(today)) {
            return EXPIRED;
        } else if (end.isBefore(today.plusDays(EXPIRING_SOON_DAYS))) {
            return EXPIRING_SOON;
        } else {
            return ACTIVE;
        }
    }

}
