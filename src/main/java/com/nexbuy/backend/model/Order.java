package com.nexbuy.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String customerName;
    private String customerEmail;
    private String deliveryAddress;
    private double totalAmount;
    private double originalAmount;

    // NEW: Status with more detail
    private String status = "PLACED"; // PLACED, CONFIRMED, PACKED, SHIPPED, OUT_FOR_DELIVERY, DELIVERED, CANCELLED, RETURNED

    // NEW: Payment method tracking
    private String paymentMethod;     // WALLET, COD, UPI, CARD
    private String paymentStatus = "PENDING"; // PENDING, PAID, REFUNDED

    // NEW: NexBuy+ delivery
    private boolean nexPlusDelivery;
    private String deliverySlot;      // MORNING, AFTERNOON, EVENING, EXPRESS

    // Coupon & discounts
    private String couponApplied;
    private double discount;

    // NEW: Loyalty points used / earned
    private int pointsUsed;
    private int pointsEarned;

    // NEW: Wallet amount used
    private double walletAmountUsed;

    // NEW: Tracking info
    private String trackingId;
    private LocalDateTime expectedDelivery;

    private LocalDateTime placedAt;
    private LocalDateTime updatedAt;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private List<OrderItem> items;

    @PrePersist
    public void prePersist() {
        this.placedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.trackingId = "NB" + System.currentTimeMillis();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
