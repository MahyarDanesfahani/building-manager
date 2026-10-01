package com.buildingmanager.backend.service;

import com.buildingmanager.backend.dto.LoginRequest;
import com.buildingmanager.backend.dto.LoginResponse;
import com.buildingmanager.backend.entity.Resident;
import com.buildingmanager.backend.entity.Unit;
import com.buildingmanager.backend.repository.UnitRepository;
import com.buildingmanager.backend.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UnitRepository unitRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public LoginResponse login(LoginRequest request) {
        Unit unit = unitRepository.findByPostalCode(request.getPostalCode())
                .orElseThrow(() -> new RuntimeException("کدپستی یا کد ملی اشتباه است"));

        Resident matchedResident = unit.getResidents().stream()
                .filter(r -> passwordEncoder.matches(request.getNationalCode(), r.getNationalCode()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("کدپستی یا کد ملی اشتباه است"));

        String token = jwtUtil.generateToken(
                matchedResident.getId(),
                matchedResident.getRole().name(),
                unit.getId()
        );

        return new LoginResponse(
                token,
                matchedResident.getFullName(),
                matchedResident.getRole().name(),
                unit.getId(),
                unit.getBuilding().getId(),
                matchedResident.getId()
        );
    }
}
