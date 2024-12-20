package com.jk.chiti.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChitPlanSummaryDto {
    private String dayStr;        // Day as a string (e.g., Mon, Tue)
    private int day;              // Day as an integer (1 to 31)
    private int totalAuctions;    // Total auctions count
    private int finishedAuctions; // Finished auctions count
    private Long planId;
}

