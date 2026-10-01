package com.buildingmanager.backend.repository;

import com.buildingmanager.backend.entity.MiscExpenseShare;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MiscExpenseShareRepository extends JpaRepository<MiscExpenseShare, Long> {
    List<MiscExpenseShare> findByUnitId(Long unitId);
    List<MiscExpenseShare> findByMiscExpenseId(Long miscExpenseId);
}
