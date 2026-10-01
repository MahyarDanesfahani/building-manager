package com.buildingmanager.backend.controller;

import com.buildingmanager.backend.dto.UnitChargeStatus;
import com.buildingmanager.backend.service.ChargeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/charges")
@RequiredArgsConstructor
public class ChargeController {

    private final ChargeService chargeService;

    @GetMapping("/building/{buildingId}")
    public List<UnitChargeStatus> getBuildingChargeStatus(@PathVariable Long buildingId) {
        return chargeService.getChargeStatusForBuilding(buildingId);
    }

    @GetMapping("/unit/{unitId}")
    public UnitChargeStatus getUnitChargeStatus(@PathVariable Long unitId) {
        return chargeService.getChargeStatusForUnit(unitId);
    }
}