package com.example.ktech_project_3.jwt;

import com.example.ktech_project_3.jwt.dto.JwtRequestDto;
import com.example.ktech_project_3.jwt.dto.JwtResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
@RequestMapping("/token")
public class TokenController {
    private final JwtTokenUtils jwtTokenUtils;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public TokenController(JwtTokenUtils jwtTokenUtils, UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.jwtTokenUtils = jwtTokenUtils;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/issue")
    public JwtResponseDto issueJwt(
            @RequestBody
            JwtRequestDto dto
    ) {
        UserDetails userDetails;
        try {
            userDetails = userDetailsService.loadUserByUsername(dto.getUsername());
        } catch (UsernameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        if (!passwordEncoder.matches(
                dto.getPassword(), userDetails.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        String jwt = jwtTokenUtils.generateToken(userDetails);
        JwtResponseDto response = new JwtResponseDto();
        response.setToken(jwt);
        return response;

    }

    @PostMapping("/issue-admin")
    public JwtResponseDto issueJwtAdmin(
            @RequestBody
            JwtRequestDto dto
    ) {
        UserDetails userDetails;
        try {
            userDetails = userDetailsService.loadUserByUsername(dto.getUsername());
        } catch (UsernameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        if (!passwordEncoder.matches(
                dto.getPassword(), userDetails.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        String jwt = jwtTokenUtils.generateToken(userDetails);
        JwtResponseDto response = new JwtResponseDto();
        response.setToken(jwt);
        return response;

    }

}

