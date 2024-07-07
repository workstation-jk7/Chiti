package com.jk.chiti.controller;

import com.jk.chiti.entity.ChitPlan;
import com.jk.chiti.service.ChitPlanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/chit-plans")
public class ChitPlanController {

    private final ChitPlanService chitPlanService;

    @Autowired
    public ChitPlanController(ChitPlanService chitPlanService) {
        this.chitPlanService = chitPlanService;
    }

    @GetMapping
    public ResponseEntity<List<ChitPlan>> getAllChitPlans() {
        List<ChitPlan> chitPlans = chitPlanService.getAllChitPlans();
        return ResponseEntity.ok(chitPlans);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChitPlan> getChitPlanById(@PathVariable Long id) {
        ChitPlan chitPlan = chitPlanService.getChitPlanById(id);
        return ResponseEntity.ok(chitPlan);
    }

    @PostMapping
    public ResponseEntity<ChitPlan> createChitPlan(@Valid @RequestBody ChitPlan chitPlan) {
        ChitPlan createdChitPlan = chitPlanService.createChitPlan(chitPlan);
        return new ResponseEntity<>(createdChitPlan, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChitPlan> updateChitPlan(@PathVariable Long id, @Valid @RequestBody List<Long> userIds) {
        ChitPlan updatedChitPlan = chitPlanService.addUserToChitPlan(id, userIds);
        return ResponseEntity.ok(updatedChitPlan);
    }

    @PutMapping("/{id}/add-users")
    public ResponseEntity<ChitPlan> addUserToChitPlan(@PathVariable Long id, @RequestBody List<Long> userIds) {
        ChitPlan chitPlan = chitPlanService.addUserToChitPlan(id, userIds);
        return ResponseEntity.ok(chitPlan);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChitPlan(@PathVariable Long id) {
        chitPlanService.deleteChitPlan(id);
        return ResponseEntity.noContent().build();
    }
}