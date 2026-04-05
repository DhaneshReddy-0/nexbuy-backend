package com.nexbuy.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @Email
    @NotBlank
    @Column(unique = true)
    private String email;

    @NotBlank
    private String password; // BCrypt hashed

    private String phone;
    private String role = "USER"; // USER, ADMIN

    // NEW: NexBuy Membership tier (FREE, PLUS, PRO)
    private String membershipTier = "FREE";

    // NEW: Wallet balance for instant checkout
    private double walletBalance = 0.0;

    // NEW: Loyalty points earned from purchases
    private int loyaltyPoints = 0;

    // NEW: Preferred delivery slot
    private String preferredDeliverySlot;

    private boolean active = true;
    private LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;

    // NEW: Saved addresses
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private List<Address> addresses = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
