package com.jk.chiti.service;

import com.jk.chiti.entity.ChitPlan;
import com.jk.chiti.entity.User;
import com.jk.chiti.repository.ChitPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public ChitPlan getChitPlanById(Long id) {
        return chitPlanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ChitPlan not found with id: " + id));
    }

    public ChitPlan createChitPlan(ChitPlan chitPlan) {
        return chitPlanRepository.save(chitPlan);
    }

    public ChitPlan addUserToChitPlan(Long chitPlanId, List<Long> userIds) {
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