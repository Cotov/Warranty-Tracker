package bg.softuni.warranty_tracker.security;

import java.util.UUID;

import bg.softuni.warranty_tracker.constant.Constants;
import jakarta.servlet.http.HttpSession;

public final class SessionUtils {

    public static void setUserId(HttpSession session, UUID userId) {
        session.setAttribute(Constants.USER_ID_SESSION_ATTRIBUTE, userId);
    }

    public static UUID getUserId(HttpSession session) {
        return (UUID) session.getAttribute(Constants.USER_ID_SESSION_ATTRIBUTE);
    }

}
