package com.hackathon.blockchain.service;

import com.hackathon.blockchain.dto.LoginResponseDto;
import com.hackathon.blockchain.dto.LoginUserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;
    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();

    public AuthService(AuthenticationManager authenticationManager,
                       SecurityContextRepository securityContextRepository) {
        this.authenticationManager = authenticationManager;
        this.securityContextRepository = securityContextRepository;
    }

    public LoginResponseDto<Boolean> login(LoginUserDto userDto, HttpServletRequest request, HttpServletResponse response){
        LoginResponseDto<Boolean> authResponse = null;
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    UsernamePasswordAuthenticationToken.unauthenticated(userDto.getUsername(), userDto.getPassword());
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            if(authentication.isAuthenticated()){
                SecurityContext context = securityContextHolderStrategy.createEmptyContext();
                context.setAuthentication(authentication);
                securityContextHolderStrategy.setContext(context);
                securityContextRepository.saveContext(context, request, response);
                authResponse = new LoginResponseDto<>(HttpStatus.OK.value(), "Login successful", true );
            }
        } catch (Exception e) {
            throw new BadCredentialsException(e.getMessage());
        }
        return authResponse;
    }
}
