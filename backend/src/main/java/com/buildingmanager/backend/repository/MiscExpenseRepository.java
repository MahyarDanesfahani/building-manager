package com.buildingmanager.backend.repository;

import com.buildingmanager.backend.entity.MiscExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MiscExpenseRepository extends JpaRepository<MiscExpense, Long> {
    List<MiscExpense> findByBuildingId(Long buildingId);
}
