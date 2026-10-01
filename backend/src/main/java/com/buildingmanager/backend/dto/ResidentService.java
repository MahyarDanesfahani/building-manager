package com.buildingmanager.backend.dto;

import com.buildingmanager.backend.entity.Resident;
import com.buildingmanager.backend.entity.Role;
import com.buildingmanager.backend.entity.Unit;
import com.buildingmanager.backend.repository.ResidentRepository;
import com.buildingmanager.backend.repository.UnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.buildingmanager.backend.dto.ResidentUpdateRequest;
import com.buildingmanager.backend.entity.Role;

@Service
@RequiredArgsConstructor
public class ResidentService {

    private final ResidentRepository residentRepository;
    private final UnitRepository unitRepository;
    private final PasswordEncoder passwordEncoder;

    public Resident createResident(ResidentCreateRequest request) {
        Unit unit = unitRepository.findById(request.getUnitId())
                .orElseThrow(() -> new RuntimeException("واحد پیدا نشد"));

        Resident resident = new Resident();
        resident.setFullName(request.getFullName());
        resident.setNationalCode(passwordEncoder.encode(request.getNationalCode())); // هش می‌شه
        resident.setPhoneNumber(request.getPhoneNumber());
        resident.setRole(Role.valueOf(request.getRole()));
        resident.setOwner(request.isOwner());
        resident.setResidencyStartDate(request.getResidencyStartDate());
        resident.setResidencyEndDate(request.getResidencyEndDate());
        resident.setUnit(unit);

        return residentRepository.save(resident);
    }

    public Resident updateResident(Long id, ResidentUpdateRequest request) {
        Resident resident = residentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ساکن پیدا نشد"));

        resident.setFullName(request.getFullName());
        resident.setPhoneNumber(request.getPhoneNumber());
        resident.setRole(Role.valueOf(request.getRole()));
        resident.setOwner(request.isOwner());
        resident.setResidencyStartDate(request.getResidencyStartDate());
        resident.setResidencyEndDate(request.getResidencyEndDate());

        return residentRepository.save(resident);
    }
}