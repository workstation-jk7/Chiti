package com.jk.chiti.service;

import com.jk.chiti.dto.ApiResponse;
import com.jk.chiti.dto.ChitPlanSummaryDto;
import com.jk.chiti.entity.Auction;
import com.jk.chiti.entity.ChitPlan;
import com.jk.chiti.entity.User;
import com.jk.chiti.exception.ResourceNotFoundException;
import com.jk.chiti.repository.ChitPlanRepository;
import com.jk.chiti.utils.AmountConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class ChitPlanService  {

    private final ChitPlanRepository chitPlanRepository;
    private final UserService userService;

    @Autowired
    public ChitPlanService(ChitPlanRepository chitPlanRepository, UserService userService) {
        this.chitPlanRepository = chitPlanRepository;
        this.userService = userService;
    }

    public List<ChitPlan> getAllChitPlans() {
        return chitPlanRepository.findAll();
    }

    @Transactional(readOnly = true)
    public ChitPlan getChitPlanById(Long id) {
        ChitPlan chitPlan = chitPlanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ChitPlan not found with id: " + id));
        return chitPlan;
    }

    public ChitPlan createChitPlan(ChitPlan chitPlan) {
        return chitPlanRepository.save(chitPlan);
    }

    @Transactional
    public ApiResponse<ChitPlan> addUserToChitPlan(Long id, List<Long> userIds) {
        ChitPlan chitPlan = chitPlanRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ChitPlan with ID " + userIds.toString() + " not found"));
        StringBuilder message = new StringBuilder();

        for (Long userId : userIds) {
            User user = userService.getUserById(userId).orElseThrow(() -> new ResourceNotFoundException("User with ID " + userId  +" not found"));
            chitPlan.getUsers().add(user);
            message.append("User with ID ").append(userId).append(" added. ");
        }

        if (chitPlan.getUsers().size() == chitPlan.getNumCustomers()) {
            chitPlan.setStatus(ChitPlan.ChitPlanStatus.ACTIVE);
            message.append("ChitPlan status changed to ACTIVE.");
        }

        ChitPlan updatedChitPlan = chitPlanRepository.save(chitPlan);
        return new ApiResponse<>(updatedChitPlan, message.toString());
    }

    public ChitPlan addUserToChitPlan_(Long chitPlanId, List<Long> userIds) {
        ChitPlan chitPlan = chitPlanRepository.findById(chitPlanId).orElseThrow(() ->
                new RuntimeException("Chit plan not found with id: " + chitPlanId));

        List<User> usersToAdd = new ArrayList<>();
        for (Long userId : userIds) {
            User user = userService.getUserById(userId).orElseThrow(() ->
                    new RuntimeException("User not found with id: " + userId));
            if (!chitPlan.getUsers().contains(user)) {
                usersToAdd.add(user);
            }
        }

        chitPlan.getUsers().addAll(usersToAdd); // Add only new users to chit plan's list

        return chitPlanRepository.save(chitPlan); // Save the updated chit plan
    }

    public ChitPlan addUserToChitPlan1(Long chitPlanId, List<Long> userIds) {
        ChitPlan chitPlan = chitPlanRepository.findById(chitPlanId).orElseThrow();

        List<User> users = new ArrayList<>();
        for (Long userId : userIds) {
            User user = userService.getUserById(userId).orElseThrow(() ->
                    new RuntimeException("User not found with id: " + userId)); // Handle missing user
            users.add(user);
        }

        chitPlan.getUsers().addAll(users); // Add users to chit plan's list

        return chitPlanRepository.save(chitPlan); // Save the updated chit plan
    }

    public List<ChitPlanSummaryDto> getChitPlanSummaries(LocalDate date, String planType) {
        // Parse the month (e.g., "2024-08")
        int year = date.getYear();
        int month = date.getMonthValue();

        // Fetch all plans matching the conditions
        List<ChitPlan> chitPlans = chitPlanRepository.findAll()
                .stream()
                .filter(plan -> {
                    // Filter by startDate year and month
                    LocalDate startDate = plan.getStartDate();
                    System.out.println("S DATE : "+ startDate + "---" + startDate.getYear() + startDate.getMonthValue()+"A"+year+"B"+month);
                    boolean matchesMonthYear = startDate.getYear() == year && startDate.getMonthValue() == month;
                    System.out.println("Match Month : " + matchesMonthYear);
                    // Optionally filter by planType
                    if (plan.getPlanType() != null) {
                        return (planType == null || plan.getPlanType().equals(planType)) && matchesMonthYear;
                    } else {
                        return matchesMonthYear;
                    }
                })
                .toList();

        // Map plans to summary DTOs
        return chitPlans.stream()
                .map(plan -> {

                    int finishedAuctions = (int) plan.getAuctions().stream()
                            .filter(auction -> auction.getStatus() == Auction.AuctionStatus.COMPLETED)
                            .count();

                    return new ChitPlanSummaryDto(
                            plan.getStartDate().getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ENGLISH),
                            plan.getStartDate().getDayOfMonth(),
                            plan.getPeriodMonths(),
                            finishedAuctions,
                            plan.getId()
                    );
                })
                .toList();
    }

    public void deleteChitPlan(Long id) {
        chitPlanRepository.deleteById(id);
    }
}