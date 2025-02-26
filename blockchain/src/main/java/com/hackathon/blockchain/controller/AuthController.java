package com.hackathon.blockchain.controller;

import com.hackathon.blockchain.dto.CheckUserDto;
import com.hackathon.blockchain.dto.LoginResponseDto;
import com.hackathon.blockchain.dto.LoginUserDto;
import com.hackathon.blockchain.dto.RegisterUserDto;
import com.hackathon.blockchain.model.User;
import com.hackathon.blockchain.service.AuthService;
import com.hackathon.blockchain.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    private final AuthService authService;
    private final UserService userService;

    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterUserDto userDto, HttpServletRequest request,
                                HttpServletResponse response){
        User registeredUser = userService.register(
                userDto.getUsername(), userDto.getEmail(), userDto.getPassword());
        LoginUserDto loginUserDto = new LoginUserDto(userDto.getUsername(), userDto.getPassword());
        LoginResponseDto<Boolean> authResponse = authService.login(loginUserDto, request, response);
        return ResponseEntity.ok("{\"message\": \"User registered and logged in successfully\"}");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginUserDto userDto, HttpServletRequest request,
                                                               HttpServletResponse response) {

        LoginResponseDto<Boolean> authResponse = authService.login(userDto, request, response);
        return ResponseEntity.status(authResponse.httpStatus()).body("{\"message\": " + authResponse.message() + "}");
    }

    @GetMapping("/check-session")
    public ResponseEntity<CheckUserDto> checkSession(Authentication authentication){
        User user = (User) authentication.getPrincipal();
        CheckUserDto checkUserDto = new CheckUserDto(user.getUsername());
        return ResponseEntity.ok(checkUserDto);
    }
}

