package com.jk.chiti.repository;

import com.jk.chiti.entity.ChitPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChitPlanRepository extends JpaRepository<ChitPlan, Long> {
    @Query("SELECT c FROM ChitPlan c WHERE MONTH(c.startDate) = :month AND (:planType IS NULL OR c.planType = :planType)")
    List<ChitPlan> findByMonthAndPlanType(@Param("month") int month, @Param("planType") String planType);
}