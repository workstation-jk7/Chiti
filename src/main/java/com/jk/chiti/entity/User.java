package com.jk.chiti.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "\\d{10}", message = "Phone number must be exactly 10 digits")
    @Column(unique = true)
    private String phoneNumber;

    @NotBlank(message = "Name is required")
    @Column(nullable = false)
    private String name;

    @Embedded
    private Address address;
}

@Embeddable
@Data
class Address {

    private String doorNo;

    private String areaLocality;

    private String landmark;

    private String district;

    private String state;

    private String country;
}

