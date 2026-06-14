package bg.softuni.warranty_tracker.config;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import bg.softuni.warranty_tracker.security.SessionInterceptor;

public class WebMvcConfiguration implements WebMvcConfigurer {

    private final SessionInterceptor sessionInterceptor;

    public WebMvcConfiguration(SessionInterceptor sessionInterceptor) {
        this.sessionInterceptor = sessionInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**");
    }
}
