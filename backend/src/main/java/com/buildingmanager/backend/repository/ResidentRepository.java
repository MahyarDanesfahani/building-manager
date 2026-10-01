package com.buildingmanager.backend.repository;

import com.buildingmanager.backend.entity.Resident;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ResidentRepository extends JpaRepository<Resident, Long> {
    List<Resident> findByUnitId(Long unitId);
}
