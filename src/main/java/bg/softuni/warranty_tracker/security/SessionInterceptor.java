package bg.softuni.warranty_tracker.security;

import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import bg.softuni.warranty_tracker.constant.Constants;
import bg.softuni.warranty_tracker.model.dto.user.UserDto;
import bg.softuni.warranty_tracker.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class SessionInterceptor implements HandlerInterceptor {
    private static final Set<String> UNAUTHENTICATED_ENDPOINTS = Set.of("/", "/users/login", "/users/register", // todo constants for those?
            "/error");

    private final UserService userService;

    public SessionInterceptor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String endpoint = request.getServletPath();
        if (UNAUTHENTICATED_ENDPOINTS.contains(endpoint)) {
            return true;
        }

        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect("/users/login");
        }

        UUID userId = UUID.fromString(String.valueOf(session.getAttribute(Constants.USER_ID_SESSION_ATTRIBUTE)));
        if (userId == null) {
            invalidateSession(session, response);
            return false;
        }

        UserDto userDto = userService.getById(userId);

        if (userDto == null) {
            invalidateSession(session, response);
            return false;
        }

        return true;
    }

    private void invalidateSession(HttpSession session, HttpServletResponse response) throws Exception {
        session.invalidate();
        response.sendRedirect("/users/login");
    }

}
