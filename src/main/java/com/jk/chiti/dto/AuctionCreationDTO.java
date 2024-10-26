package com.jk.chiti.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Data
public class AuctionCreationDTO {
    private Long chitPlanId;
    private LocalDate auctionDate;
    private double winningAmount;
    private double amountToBePaid;
    private String auctionStatus;
    private Long winnerUserId;
}