package com.jk.chiti.controller;

import com.jk.chiti.dto.AuctionCreationDTO;
import com.jk.chiti.entity.Auction;
import com.jk.chiti.service.AuctionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/auctions")
public class AuctionController {

    private final AuctionService auctionService;

    @Autowired
    public AuctionController(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @GetMapping
    public ResponseEntity<List<Auction>> getAllAuctions() {
        List<Auction> auctions = auctionService.getAllAuctions();
        return ResponseEntity.ok(auctions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Auction> getAuctionById(@PathVariable Long id) {
        Auction auction = auctionService.getAuctionById(id);
        return ResponseEntity.ok(auction);
    }

    @PostMapping
    public ResponseEntity<Auction> createAuction(@Valid @RequestBody AuctionCreationDTO auctionCreationDTO) {
        System.out.println(auctionCreationDTO.toString());
        Auction createdAuction = auctionService.createAuction(auctionCreationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAuction);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Auction> updateAuction(@PathVariable Long id, @Valid @RequestBody Auction auction) {
        Auction updatedAuction = auctionService.updateAuction(id, auction);
        return ResponseEntity.ok(updatedAuction);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuction(@PathVariable Long id) {
        auctionService.deleteAuction(id);
        return ResponseEntity.noContent().build();
    }

    // Additional methods for bidding, winning, etc. (implemented in AuctionService)
}
