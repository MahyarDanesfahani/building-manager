package com.buildingmanager.backend.repository;

import com.buildingmanager.backend.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BillRepository extends JpaRepository<Bill, Long> {
    List<Bill> findByBuildingId(Long buildingId);
}
