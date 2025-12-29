package com.jalal.evently.auth.service;

import com.jalal.evently.auth.dto.AuthResponse;
import com.jalal.evently.auth.dto.LoginRequest;
import com.jalal.evently.auth.dto.RegisterRequest;
import com.jalal.evently.auth.entity.User;
import com.jalal.evently.auth.repository.UserRepository;
import com.jalal.evently.auth.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public void register(RegisterRequest request) {
        String email = request.getEmail().toLowerCase().trim();

        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already in use");
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);
    }

    public AuthResponse login(LoginRequest request) {
        String email = request.getEmail().toLowerCase().trim();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        // IMPORTANT: subject should match what your JwtAuthFilter extracts (usually username/email)
        String token = jwtService.generateToken(user);

        return new AuthResponse(token, user.getEmail());
    }
}
