package com.prince.ToolEntrySystem.controller;

import com.prince.ToolEntrySystem.dto.LoginDto;
import com.prince.ToolEntrySystem.dto.LoginResponseDto;
import com.prince.ToolEntrySystem.dto.SignUpDto;
import com.prince.ToolEntrySystem.dto.UserDto;
import com.prince.ToolEntrySystem.service.AuthService;
import com.prince.ToolEntrySystem.service.JwtService;
import com.prince.ToolEntrySystem.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/auth") 
public class AuthController {
    
    private final UserService userService;
    
    private final AuthService authService;
    
    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpDto signUpDto){
        UserDto userDto = userService.signUp(signUpDto);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
    
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginDto loginDto, 
                                                  HttpServletRequest request, HttpServletResponse response){
        LoginResponseDto loginResponseDto = authService.login(loginDto);
        Cookie cookie = new Cookie("refreshToken", loginResponseDto.getRefreshToken());
        cookie.setHttpOnly(true);
//        cookie.setSecure("production".equals(deployEnv));
        response.addCookie(cookie);
        return new ResponseEntity<>(loginResponseDto, HttpStatus.OK);
    }
    
    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDto> refresh(HttpServletRequest request){
        String refreshToken = Arrays.stream(request.getCookies())
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new AuthenticationServiceException("Refresh Token not found inside the cookie"));
        LoginResponseDto loginResponseDto = authService.refreshToken(refreshToken);
        
        return new ResponseEntity<>(loginResponseDto, HttpStatus.OK);
    }
}
