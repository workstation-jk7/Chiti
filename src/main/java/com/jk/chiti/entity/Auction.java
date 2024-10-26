package com.jk.chiti.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "auctions")
@JsonIgnoreProperties({"chitPlan"})
public class Auction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chit_plan_id")
    private ChitPlan chitPlan;

    private LocalDate auctionDate;

    @ManyToOne
    @JoinColumn(name = "winner_id")
    private User winner;

    private double winningAmount;
    private double amountToBePaid;

    @Enumerated(EnumType.STRING)
    private AuctionStatus status;

    public enum AuctionStatus {
        OPEN,
        COMPLETED
    }

    // Add getters and setters for chitPlanId and winnerId
    public Long getChitPlanId() {
        return chitPlan.getId();
    }

    public Long getWinnerId() {
        return winner != null ? winner.getId() : null;
    }

}

