package com.jk.chiti.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "chit_plans")
public class ChitPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(value = 0, message = "Amount must be positive")
    @Column(nullable = false)
    private double amount;

    @Min(value = 1, message = "Number of customers must be at least 1")
    @Column(nullable = false, name = "num_customers")
    private int numCustomers;

    @Min(value = 1, message = "Period must be at least 1 month")
    @Column(nullable = false, name = "period_months")
    private int periodMonths;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "payment_frequency")
    private PaymentFrequency paymentFrequency;

    @NotNull(message = "Start date cannot be null")
    @Column(nullable = false, name = "start_date")
    private LocalDate startDate;

    @Min(value = 0, message = "Commission percentage must be non-negative")
    @Column(nullable = false, name = "commission_percentage")
    private double commissionPercentage;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "chit_plan_users",
            joinColumns = @JoinColumn(name = "chit_plan_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @JsonManagedReference
    private Set<User> users = new HashSet<>();

    public enum PaymentFrequency {
        WEEKLY,
        MONTHLY,
        QUARTERLY
    }
}