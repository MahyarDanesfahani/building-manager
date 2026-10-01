package com.buildingmanager.backend.repository;

import com.buildingmanager.backend.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UnitRepository extends JpaRepository<Unit, Long> {
    Optional<Unit> findByPostalCode(String postalCode);   // برای لاگین لازممون می‌شه
}