package com.buildingmanager.backend.controller;

import com.buildingmanager.backend.dto.ResidentCreateRequest;
import com.buildingmanager.backend.entity.Resident;
import com.buildingmanager.backend.dto.ResidentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.buildingmanager.backend.dto.ResidentUpdateRequest;

@RestController
@RequestMapping("/api/residents")
@RequiredArgsConstructor
public class ResidentController {

    private final ResidentService residentService;

    @PostMapping
    public Resident create(@RequestBody ResidentCreateRequest request) {
        return residentService.createResident(request);
    }
    @PutMapping("/{id}")
    public Resident update(@PathVariable Long id, @RequestBody ResidentUpdateRequest request) {
        return residentService.updateResident(id, request);
    }
}