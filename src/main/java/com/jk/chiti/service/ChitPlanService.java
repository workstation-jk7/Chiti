package com.jk.chiti.service;

import com.jk.chiti.dto.ApiResponse;
import com.jk.chiti.entity.ChitPlan;
import com.jk.chiti.entity.User;
import com.jk.chiti.exception.ResourceNotFoundException;
import com.jk.chiti.repository.ChitPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

    public void deleteChitPlan(Long id) {
        chitPlanRepository.deleteById(id);
    }
}