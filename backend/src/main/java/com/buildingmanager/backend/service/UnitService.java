package com.buildingmanager.backend.service;

import com.buildingmanager.backend.dto.ResidentResponse;
import com.buildingmanager.backend.dto.UnitWithResidentsResponse;
import com.buildingmanager.backend.entity.Resident;
import com.buildingmanager.backend.entity.Unit;
import com.buildingmanager.backend.repository.ResidentRepository;
import com.buildingmanager.backend.repository.UnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UnitService {

    private final UnitRepository unitRepository;
    private final ResidentRepository residentRepository;

    public List<UnitWithResidentsResponse> getUnitsForBuilding(Long buildingId) {
        List<Unit> units = unitRepository.findAll().stream()
                .filter(u -> u.getBuilding().getId().equals(buildingId))
                .collect(Collectors.toList());

        return units.stream().map(this::toResponse).collect(Collectors.toList());
    }

    private UnitWithResidentsResponse toResponse(Unit unit) {
        List<ResidentResponse> residents = residentRepository.findByUnitId(unit.getId()).stream()
                .map(r -> new ResidentResponse(
                        r.getId(), r.getFullName(), r.getPhoneNumber(), r.getRole().name(),
                        r.isOwner(), r.getResidencyStartDate(), r.getResidencyEndDate()))
                .collect(Collectors.toList());

        return new UnitWithResidentsResponse(
                unit.getId(), unit.getUnitNumber(), unit.getPostalCode(), unit.getUnitPhone(), residents);
    }
}
