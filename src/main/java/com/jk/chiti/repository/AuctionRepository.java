package com.jk.chiti.repository;

import com.jk.chiti.entity.Auction;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long> {
}
