package com.nexbuy.backend.dto;

import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AuthDTO {

    @Data
    public static class RegisterRequest {
        @NotBlank private String name;
        @Email @NotBlank private String email;
        @NotBlank @Size(min = 6) private String password;
        private String phone;
    }

    @Data
    public static class LoginRequest {
        @Email @NotBlank private String email;
        @NotBlank private String password;
    }

    @Data
    public static class AuthResponse {
        private String token;
        private Long id;
        private String name;
        private String email;
        private String role;
        private String membershipTier;
        private double walletBalance;
        private int loyaltyPoints;

        public AuthResponse(String token, Long id, String name, String email,
                            String role, String membershipTier, double walletBalance, int loyaltyPoints) {
            this.token = token;
            this.id = id;
            this.name = name;
            this.email = email;
            this.role = role;
            this.membershipTier = membershipTier;
            this.walletBalance = walletBalance;
            this.loyaltyPoints = loyaltyPoints;
        }
    }
}
