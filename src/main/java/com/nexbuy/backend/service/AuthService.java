package com.nexbuy.backend.service;

import com.nexbuy.backend.config.JwtUtil;
import com.nexbuy.backend.dto.AuthDTO.*;
import com.nexbuy.backend.model.User;
import com.nexbuy.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtUtil jwtUtil;

    public AuthResponse register(RegisterRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new RuntimeException("Email already registered");
        }
        User user = new User();
        user.setName(req.getName());
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setPhone(req.getPhone());
        user.setWalletBalance(100.0);    // Welcome bonus ₹100
        user.setLoyaltyPoints(50);       // Welcome 50 points
        User saved = userRepository.save(user);

        String token = jwtUtil.generateToken(saved.getEmail(), saved.getId());
        return new AuthResponse(token, saved.getId(), saved.getName(), saved.getEmail(),
                saved.getRole(), saved.getMembershipTier(), saved.getWalletBalance(), saved.getLoyaltyPoints());
    }

    public AuthResponse login(LoginRequest req) {
        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }
        String token = jwtUtil.generateToken(user.getEmail(), user.getId());
        return new AuthResponse(token, user.getId(), user.getName(), user.getEmail(),
                user.getRole(), user.getMembershipTier(), user.getWalletBalance(), user.getLoyaltyPoints());
    }
}
