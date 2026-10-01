package com.buildingmanager.backend.controller;

import com.buildingmanager.backend.dto.UnitWithResidentsResponse;
import com.buildingmanager.backend.service.UnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/units")
@RequiredArgsConstructor
public class UnitController {

    private final UnitService unitService;

    @GetMapping("/building/{buildingId}")
    public List<UnitWithResidentsResponse> getUnits(@PathVariable Long buildingId) {
        return unitService.getUnitsForBuilding(buildingId);
    }
}