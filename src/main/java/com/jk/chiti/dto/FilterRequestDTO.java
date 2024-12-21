package com.jk.chiti.dto;

import lombok.Data;

@Data
public class FilterRequestDTO {
    private Long id;
    private Long auctionId;
    private String planType;
}
