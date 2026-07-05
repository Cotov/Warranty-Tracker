package bg.softuni.warranty_tracker.constant;

public class ExceptionMessages {
    public static final String SOMETHING_WENT_WRONG = "Something went wrong";
    public static final String USER_NOT_FOUND = "User not found";
    public static final String PRODUCT_ALREADY_EXISTS = "Product with this serial number already exists";
    public static final String REGISTER_PRODUCT_FAILED = "Register product failed";
    public static final String UPDATE_PRODUCT_FAILED = "Update product failed";
    public static final String VENDOR_NOT_FOUND = "Vendor not found";
    public static final String VENDOR_CREATION_FAILED = "Create vendor failed";
    public static final String VENDOR_RESOLUTION_FAILED = "Vendor resolution failed";
    public static final String VENDOR_AND_SESSION_USER_MISMATCH = "This vendor does not belong to the session user. Vednor id: %s ; User id: %s";
    public static final String SESSION_AND_USER_MISMATCH = "Invalid user session";
    public static final String PRODUCT_NOT_FOUND = "Product not found";
    public static final String PRODUCT_FORM_NULL = "ProductForm object was null";
    public static final String FAILED_TO_MAP_PRODUCT_TO_EDIT_REQUEST = "Product mapping error";
    public static final String ADD_CLAIM_FAILED = "Add claim failed";
    public static final String ACTIVE_CLAIM_EXISTS = "Active claim exists for this product";
    public static final String CLAIM_NOT_FOUND = "Claim not found";
    public static final String UPDATE_CLAIM_FAILED = "Update claim failed";
    public static final String INVALID_STATUS_TRANSITION = "Invalid status transition";
    public static final String PRODUCT_HAS_ACTIVE_CLAIM = "Product has active or pending claim";
    public static final String CANNOT_CHANGE_OWN_ROLE = "Cannot change your own role";
}
