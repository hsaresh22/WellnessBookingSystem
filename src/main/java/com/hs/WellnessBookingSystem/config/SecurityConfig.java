package com.hs.WellnessBookingSystem.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// Security configuration class to set up HTTP security and password encoding
@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**","/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html").permitAll()   // Only auth services and swagger allowed
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());  // You can later switch to JWT if needed

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner debugProperties(Environment env) {
        return args -> {
            System.out.println("### Effective spring.data.mongodb.uri = "
                    + env.getProperty("spring.data.mongodb.uri"));
        };
    }

}
