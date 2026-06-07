package bg.softuni.warranty_tracker.constant;
// todo remove duplicate calls from entity
public class ValidationMessages {
    //todo code duplication
    public static final String NAME_REQUIRED = "Name is required";

    // User
    public static final String USERNAME_REQUIRED = "Username is required";
    public static final String PASSWORD_REQUIRED = "Password is required";
    //todo constraint numbers in constants
    public static final String USERNAME_LENGTH = "Username must be between 6 and 20 characters";
    public static final String PASSWORD_LENGTH = "Password must be between 6 and 20 characters";
    public static final String FIRST_NAME_REQUIRED = "First name is required";
    public static final String FIRST_NAME_LENGTH = "First name must be between 2 and 20 characters";
    public static final String LAST_NAME_REQUIRED = "Last name is required";
    public static final String LAST_NAME_LENGTH = "Last name must be between 2 and 20 characters";

    // Contact
    public static final String EMAIL_REQUIRED = "Email is required";
    public static final String EMAIL_INVALID = "Invalid email format";
    public static final String PHONE_REQUIRED = "Phone is required";
    public static final String WEBSITE_REQUIRED = "Website is required";
    public static final String WEBSITE_INVALID = "Invalid website URL";

    // Warranty Claim
    public static final String STATUS_REQUIRED = "Status is required";
    public static final String DATE_SENT_REQUIRED = "Date sent is required";
    public static final String NOTES_REQUIRED = "Notes are required";

    // Product
    public static final String SERIAL_NUMBER_REQUIRED = "Serial number is required";
    public static final String DESCRIPTION_REQUIRED = "Description is required";
    public static final String PURCHASE_DATE_REQUIRED = "Purchase date is required";
    public static final String WARRANTY_START_DATE_REQUIRED = "Warranty start date is required";
    public static final String WARRANTY_END_DATE_REQUIRED = "Warranty end date is required";
    public static final String PHYSICAL_RECEIPT_LOCATION_REQUIRED = "Physical receipt location is required";

    //Vendor
    public static final String VENDOR_REQUIRED = "Vendor name is required";
    public static final String INVALID_VENDOR_DETAILS = "Vendor details are invalid";
}
