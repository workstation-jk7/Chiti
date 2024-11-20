package com.jk.chiti.controller;

import com.jk.chiti.dto.ApiResponse;
import com.jk.chiti.entity.Auction;
import com.jk.chiti.entity.ChitPlan;
import com.jk.chiti.entity.User;
import com.jk.chiti.service.ChitPlanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/chit-plans")
public class ChitPlanController {


    private final ChitPlanService chitPlanService;

    @Autowired
    public ChitPlanController(ChitPlanService chitPlanService) {
        this.chitPlanService = chitPlanService;
    }

    @GetMapping("/mock")
    public ResponseEntity<String> getAMockChitPlans() {
        String jsonResponse = "["
                + "{\"id\":1,\"amount\":100000,\"numCustomers\":10,\"periodMonths\":12,\"paymentFrequency\":\"MONTHLY\",\"startDate\":\"2024-12-01\",\"commissionPercentage\":5.0,\"status\":\"CREATED\","
                + "\"users\":[{\"id\":1,\"name\":\"John Doe\",\"phoneNumber\":\"9876543210\",\"address\":{\"doorNo\":\"123\",\"areaLocality\":\"Main Street\",\"landmark\":\"Near Park\",\"district\":\"Chennai\",\"state\":\"Tamil Nadu\",\"country\":\"India\"}},"
                + "{\"id\":2,\"name\":\"Jane Smith\",\"phoneNumber\":\"9876543211\",\"address\":{\"doorNo\":\"456\",\"areaLocality\":\"Highway Road\",\"landmark\":\"Opposite Mall\",\"district\":\"Mumbai\",\"state\":\"Maharashtra\",\"country\":\"India\"}}],"
                + "\"auctions\":[{\"id\":1,\"date\":\"2024-12-15\",\"winningAmount\":95000,\"winner\":{\"id\":1,\"name\":\"John Doe\",\"phoneNumber\":\"9876543210\"}},"
                + "{\"id\":2,\"date\":\"2025-01-15\",\"winningAmount\":94000,\"winner\":{\"id\":2,\"name\":\"Jane Smith\",\"phoneNumber\":\"9876543211\"}}]},"
                + "{\"id\":2,\"amount\":200000,\"numCustomers\":15,\"periodMonths\":24,\"paymentFrequency\":\"WEEKLY\",\"startDate\":\"2025-01-01\",\"commissionPercentage\":6.0,\"status\":\"ACTIVE\","
                + "\"users\":[{\"id\":3,\"name\":\"Elon Musk\",\"phoneNumber\":\"9876543212\",\"address\":{\"doorNo\":\"789\",\"areaLocality\":\"Space Street\",\"landmark\":\"Near Rocket Park\",\"district\":\"Bengaluru\",\"state\":\"Karnataka\",\"country\":\"India\"}},"
                + "{\"id\":4,\"name\":\"Dunny Duncan\",\"phoneNumber\":\"9876543213\",\"address\":{\"doorNo\":\"101\",\"areaLocality\":\"Tech Valley\",\"landmark\":\"Opposite Hub\",\"district\":\"Hyderabad\",\"state\":\"Telangana\",\"country\":\"India\"}}],"
                + "\"auctions\":[{\"id\":3,\"date\":\"2025-01-15\",\"winningAmount\":190000,\"winner\":{\"id\":3,\"name\":\"Elon Musk\",\"phoneNumber\":\"9876543212\"}},"
                + "{\"id\":4,\"date\":\"2025-02-15\",\"winningAmount\":185000,\"winner\":{\"id\":4,\"name\":\"Dunny Duncan\",\"phoneNumber\":\"9876543213\"}}]}]";

        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(jsonResponse);
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
    public ResponseEntity<ApiResponse<ChitPlan>> updateChitPlan(@PathVariable Long id, @Valid @RequestBody List<Long> userIds) {
        ApiResponse<ChitPlan> updatedChitPlan = chitPlanService.addUserToChitPlan(id, userIds);
        return ResponseEntity.ok(updatedChitPlan);
    }

    @PutMapping("/{id}/add-users")
    public ResponseEntity<ApiResponse<ChitPlan>> addUserToChitPlan(@PathVariable Long id, @RequestBody List<Long> userIds) {
        ApiResponse<ChitPlan> chitPlan = chitPlanService.addUserToChitPlan(id, userIds);
        return ResponseEntity.ok(chitPlan);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChitPlan(@PathVariable Long id) {
        chitPlanService.deleteChitPlan(id);
        return ResponseEntity.noContent().build();
    }
}