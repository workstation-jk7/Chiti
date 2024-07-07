package com.jk.chiti.repository;

import com.jk.chiti.entity.ChitPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChitPlanRepository extends JpaRepository<ChitPlan, Long> {
}