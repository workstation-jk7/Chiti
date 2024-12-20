package com.jk.chiti.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "chit_plans")
public class ChitPlan {

    public ChitPlan() {
        this.status = ChitPlanStatus.CREATED;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Group name cannot be null")
    @Column(nullable = false)
    private String planName;

    @Min(value = 0, message = "Amount must be positive")
    @Column(nullable = false)
    private double amount;

    @NotNull(message = "Plan Type cannot be null")
    @Column(nullable = false, name = "plan_type")
    private String planType;

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

    @ManyToMany(cascade = { CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinTable(name = "chit_plan_users",
            joinColumns = @JoinColumn(name = "chit_plan_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    //@JsonIgnore
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "chitPlan", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Auction> auctions = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private ChitPlanStatus status;

    public enum PaymentFrequency {
        WEEKLY,
        MONTHLY,
        QUARTERLY
    }

    public enum ChitPlanStatus {
        CREATED,
        ACTIVE,
        COMPLETED,
        INACTIVE,
        SUSPENDED,
        CANCELLED
    }
}

