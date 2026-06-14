package bg.softuni.warranty_tracker.constant;

public class ExceptionMessages {
    public static final String USER_NOT_FOUND = "User was not found by UUID";
    public static final String REGISTER_PRODUCT_FAILED = "Register product failed - request or user dto null";
    public static final String VENDOR_NOT_FOUND = "Vendor not found by UUID";
    public static final String VENDOR_CREATION_FAILED = "Create request or vendor id null";
    public static final String VENDOR_AND_SESSION_USER_MISMATCH = "This vendor does not belong to the session user. Vednor id: %s ; User id: %s";
    public static final String FAILED_TO_PARSE_UUID = "Exception opcurred when parsing UUID";

}
