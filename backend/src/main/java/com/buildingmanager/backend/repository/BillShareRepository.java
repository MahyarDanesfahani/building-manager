package com.buildingmanager.backend.repository;

import com.buildingmanager.backend.entity.BillShare;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BillShareRepository extends JpaRepository<BillShare, Long> {
    List<BillShare> findByUnitId(Long unitId);
    List<BillShare> findByBillId(Long billId);   // این خط جدید اضافه شد
}
