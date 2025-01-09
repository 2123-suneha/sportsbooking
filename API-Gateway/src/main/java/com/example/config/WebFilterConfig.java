package com.example.config;
import org.springframework.context.annotation.Bean;
import org.springframework.web.server.WebFilter;

import com.example.filter.JwtAuthenticationFilter;


//@Configuration
public class WebFilterConfig {

    @Bean
    public WebFilter headerModificationFilter() {
        return new JwtAuthenticationFilter();
    }
}