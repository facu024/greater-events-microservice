package ar.edu.unnoba.pdyc2025.artist.config;

import ar.edu.unnoba.pdyc2025.artist.util.JwtAuthorizationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {
    @Bean
    public FilterRegistrationBean<JwtAuthorizationFilter> jwtFilter(JwtAuthorizationFilter filter) {
        FilterRegistrationBean<JwtAuthorizationFilter> reg = new FilterRegistrationBean<>();
        reg.setFilter(filter);
        reg.addUrlPatterns("/admin/*"); // Solo endpoints admin
        reg.setOrder(1);
        return reg;
    }
}