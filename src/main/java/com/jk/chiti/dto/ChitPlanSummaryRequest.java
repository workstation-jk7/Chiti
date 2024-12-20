package com.jk.chiti.dto;


import java.time.LocalDate;

public class ChitPlanSummaryRequest {

    private LocalDate date;  // Complete date (e.g., "2024-12-15")
    private String planType; // Optional, can be null

    // Getters and Setters
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }
}

