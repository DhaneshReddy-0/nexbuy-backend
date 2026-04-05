package com.nexbuy.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String label;       // Home, Work, Other
    private String fullName;
    private String phone;
    private String street;
    private String city;
    private String state;
    private String pincode;
    private boolean isDefault;
}
