package com.example.jwtAuthentication.service;

import com.example.jwtAuthentication.entity.User;
import com.example.jwtAuthentication.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//@RequiredArgsConstructor
public class AuthService {

    public final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    // use with manual create constructor
    public User createUser(User userData) {
        User newUser = new User(
                userData.getUsername(),
                userData.getEmail(),
                passwordEncoder.encode(userData.getPassword()),
                userData.getName());
//                userData.getCreatedAt());
        return userRepository.save(newUser);
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
}
