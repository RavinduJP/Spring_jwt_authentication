package com.example.jwtAuthentication.service;

import com.example.jwtAuthentication.dto.LoginRequestDto;
import com.example.jwtAuthentication.dto.LoginResponseDto;
import com.example.jwtAuthentication.entity.AppUser;
import com.example.jwtAuthentication.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
//@RequiredArgsConstructor
public class AuthService {

    public final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public List<AppUser> getAllUsers() {
        return userRepository.findAll();
    }


    // use with manual create constructor
    public AppUser createUser(AppUser userData) {
        AppUser newUser = new AppUser(
                userData.getUsername(),
                userData.getEmail(),
                passwordEncoder.encode(userData.getPassword()),
                userData.getName());
//                userData.getCreatedAt());
        return userRepository.save(newUser);
    }

    public LoginResponseDto login(LoginRequestDto loginData) {
//        Boolean userPresent = isUserEnable(loginData.getUsername());
//        if (!userPresent) return new LoginResponseDto(null, null, "user not found", "error");

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginData.getUsername(), loginData.getPassword()));
        } catch (Exception e) {
            return new LoginResponseDto(null, null, "user not found", "error");
        }

        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put("role","User");
        claims.put("email","company@gmail.com");

        String token = jwtService.getJwtToken(loginData.getUsername(), claims);

        return new LoginResponseDto("token", LocalDateTime.now(), null, "token generate success");
    }
        private Boolean isUserEnable (String username){
            return userRepository.findByUsername(username).isPresent();
        }
    }

        // use with builder annotation
//    public User createUser(User userData) {
//        User newUser = User.builder()
//                .username(userData.getUsername())
//                .email(userData.getEmail())
//                .password(passwordEncoder.encode(userData.getPassword()))
//                .name(userData.getName())
//                .createdAt(userData.getCreatedAt())
//                .build();
//        return userRepository.save(newUser);
//    }

