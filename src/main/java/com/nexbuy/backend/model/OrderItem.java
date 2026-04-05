package com.nexbuy.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;
    private String productName;
    private String productBrand;
    private String productImage;
    private double price;
    private int quantity;
    private boolean giftWrap;
    private String giftMessage;
}
