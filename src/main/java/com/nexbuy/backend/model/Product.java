package com.nexbuy.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String brand;

    @Column(length = 2000)
    private String description;

    private double price;
    private double originalPrice;
    private String imageUrl;
    private int quantity;
    private String category;
    private String subCategory;   // NEW: sub-category for better filtering

    private double rating;
    private int reviewCount;

    // Membership / Deal flags
    private boolean nexPlus;      // NexBuy+ exclusive (replaces "prime")
    private int dealPercent;
    private String badge;         // new, sale, trending, limited

    // NEW: Product tags for smart recommendations
    private String tags;          // comma-separated: "wireless,gaming,rgb"

    // NEW: Estimated delivery days
    private int deliveryDays = 3;

    // NEW: Stock status
    private String stockStatus = "IN_STOCK"; // IN_STOCK, LOW_STOCK, OUT_OF_STOCK

    // NEW: Points awarded on purchase
    private int loyaltyPointsReward = 0;

    // NEW: Product reviews
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private List<Review> reviews = new ArrayList<>();
}
