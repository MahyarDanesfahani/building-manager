package com.buildingmanager.backend.repository;

import com.buildingmanager.backend.entity.ChargeDeposit;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ChargeDepositRepository extends JpaRepository<ChargeDeposit, Long> {
    List<ChargeDeposit> findByUnitId(Long unitId);
}
