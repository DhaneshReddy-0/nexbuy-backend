package com.nexbuy.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "price_alerts")
public class PriceAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long productId;
    private String productName;
    private double currentPrice;
    private double targetPrice;   // Alert when price drops to this
    private boolean triggered;
    private LocalDateTime createdAt;
    private LocalDateTime triggeredAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.triggered = false;
    }
}
