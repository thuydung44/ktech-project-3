package com.example.ktech_project_3.config;

import com.example.ktech_project_3.jwt.JwtTokenFilter;
import com.example.ktech_project_3.jwt.JwtTokenUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
public class WebSecurityConfig {
    private final JwtTokenUtils jwtTokenUtils;
    private final UserDetailsService userDetailsService;
    public WebSecurityConfig(JwtTokenUtils jwtTokenUtils, UserDetailsService userDetailsService) {
        this.jwtTokenUtils = jwtTokenUtils;
        this.userDetailsService = userDetailsService;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/token/issue", "/error", "/token/issue-admin").permitAll();
                    auth.requestMatchers("/users/my-profile","/users/update", "/users/update/image").authenticated();
                    auth.requestMatchers("/users/register").anonymous();
                    auth.requestMatchers("/users/apply-business", "/shops/search", "/products/search").hasRole("USER");
                    auth.requestMatchers("/users/admin/**", "/shops/search", "/products/search").hasRole("ADMIN");
                    auth.requestMatchers("/shops/create","/shops/search", "/products/search").hasRole("BUSINESS");

                    auth.anyRequest().authenticated();

                })

                .addFilterBefore(
                        new JwtTokenFilter(jwtTokenUtils, userDetailsService),
                        AuthorizationFilter.class
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        ;
        return http.build();


    }
}
