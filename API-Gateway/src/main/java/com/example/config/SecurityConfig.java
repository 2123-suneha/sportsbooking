package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import com.example.filter.JwtAuthenticationFilter;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
	@Autowired
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Autowired
	public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	}
	
	@Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
        		.authorizeExchange(exchanges -> exchanges
        				.pathMatchers("/payment-gatway/paymentApi/createUserAccout").permitAll()
        				.pathMatchers("/sports-service/sports/allsports").permitAll()
        				.pathMatchers( "/user-service/auth/**").permitAll()
        				.pathMatchers("/sport-service/sports/add","/sport-service/sports/update","/sport-service/sports/delete**").hasRole("ROLE_admin")
        				.pathMatchers( "/booking-service/**").hasAnyRole("ROLE_user","ROLE_admin")
        				.anyExchange().authenticated()  
        		)
        		 .addFilterAt(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION) // Add JWT filter
                 .csrf().disable()  // Disable CSRF for stateless APIs
                 .build();
	}

} 
