package com.jk.chiti.service;

import com.jk.chiti.dto.AuctionCreationDTO;
import com.jk.chiti.entity.Auction;
import com.jk.chiti.entity.ChitPlan;
import com.jk.chiti.entity.User;
import com.jk.chiti.repository.AuctionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

import java.util.Optional;

@Service
public class AuctionService {

    private final AuctionRepository auctionRepository;
    private final ChitPlanService chitPlanService;
    private final UserService userService;

    @Autowired
    public AuctionService(AuctionRepository auctionRepository, ChitPlanService chitPlanService, UserService userService) {
        this.auctionRepository = auctionRepository;
        this.chitPlanService = chitPlanService;
        this.userService = userService;
    }

    public List<Auction> getAllAuctions()
    {
        return auctionRepository.findAll();
    }

    public Auction getAuctionById(Long id) {
        return auctionRepository.findById(id).orElseThrow(()
                -> new RuntimeException("Auction not found"));
    }

    public Auction createAuction(AuctionCreationDTO auctionCreationDTO) {
        Auction auction = new Auction();
        ChitPlan chitPlan = chitPlanService.getChitPlanById(auctionCreationDTO.getChitPlanId());
        User winner = userService.getUserById(auctionCreationDTO.getWinnerUserId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        auction.setChitPlan(chitPlan);
        auction.setAuctionDate(auctionCreationDTO.getAuctionDate());
        auction.setWinningAmount(auctionCreationDTO.getWinningAmount());
        auction.setAmountToBePaid(auctionCreationDTO.getAmountToBePaid());
        auction.setStatus(Auction.AuctionStatus.OPEN);
        auction.setWinner(winner);

        return auctionRepository.save(auction);
    }

    public Auction updateAuction(Long id, Auction auction)
    {
        Auction existingAuction = auctionRepository.findById(id).orElseThrow();
        // Update existingAuction properties with values from auction
        return auctionRepository.save(existingAuction);
    }

    public void deleteAuction(Long id) {
        auctionRepository.deleteById(id);
    }

    // Additional methods for bidding, winning, etc.
}