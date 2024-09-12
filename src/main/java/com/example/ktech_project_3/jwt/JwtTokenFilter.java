package com.example.ktech_project_3.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {
    private final JwtTokenUtils jwtTokenUtils;
    private final UserDetailsService userService;

    public JwtTokenFilter(JwtTokenUtils jwtTokenUtils, UserDetailsService userService) {
        this.jwtTokenUtils = jwtTokenUtils;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null) {
            filterChain.doFilter(request, response);
            return;
        }
        String[] headerSplit = authHeader.split(" ");
        if (!(headerSplit.length == 2 || headerSplit[0].equalsIgnoreCase("Bearer"))) {
            filterChain.doFilter(request, response);
            return;
        }
        String jwt = headerSplit[1];
        if(!jwtTokenUtils.validate(jwt)) {
            filterChain.doFilter(request, response);
            return;
        }
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        String username = jwtTokenUtils.parseClaims(jwt).getSubject();
        UserDetails userDetails = userService.loadUserByUsername(username);
        log.info(userDetails.getAuthorities().toString());
        AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),userDetails.getAuthorities());
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
        filterChain.doFilter(request, response);
    }
}

