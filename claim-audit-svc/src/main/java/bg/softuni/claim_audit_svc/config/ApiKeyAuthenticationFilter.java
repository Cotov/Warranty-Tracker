package bg.softuni.claim_audit_svc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import jakarta.servlet.FilterChain;

@Component
public class ApiKeyAuthenticationFilter extends OncePerRequestFilter {

    private static final String X_API_KEY_HEADER = "X-API-KEY";

    @Value("${api-key}")
    private String apiKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String providedApiKey = request.getHeader(X_API_KEY_HEADER);
            if (providedApiKey == null || !providedApiKey.equals(apiKey)) {
                throw new BadCredentialsException("Invalid API key");
            }

            Authentication authentication = new ApiKeyAuthentication();
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);

        } catch (BadCredentialsException e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write(e.getMessage());
            return;
        }
    }
}
