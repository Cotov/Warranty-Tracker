package bg.softuni.warranty_tracker.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import static bg.softuni.warranty_tracker.constant.Constants.UNAUTHENTICATED_ENDPOINTS;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;

@Configuration
public class SecurityConfiguration implements WebMvcConfigurer {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(mathchers -> mathchers
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .permitAll()
                .requestMatchers(UNAUTHENTICATED_ENDPOINTS)
                .permitAll()
                .anyRequest()
                .authenticated())
                .formLogin(form -> form
                        .loginProcessingUrl("/users/login")
                        .loginPage("/users/login") // todo constants for paths
                        .defaultSuccessUrl("/dashboard")
                        .failureUrl("/users/login?error") // todo enough?
                        .permitAll())
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/users/logout", "POST"))
                        .logoutSuccessUrl("/users/login"));
        return http.build();
    }
}
