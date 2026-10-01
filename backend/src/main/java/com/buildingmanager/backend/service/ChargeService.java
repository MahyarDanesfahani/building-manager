package com.buildingmanager.backend.service;

import com.buildingmanager.backend.dto.UnitChargeStatus;
import com.buildingmanager.backend.entity.Unit;
import com.buildingmanager.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChargeService {

    private final UnitRepository unitRepository;
    private final ChargeDepositRepository chargeDepositRepository;
    private final BillShareRepository billShareRepository;
    private final MiscExpenseShareRepository miscExpenseShareRepository;

    public List<UnitChargeStatus> getChargeStatusForBuilding(Long buildingId) {
        List<Unit> units = unitRepository.findAll().stream()
                .filter(u -> u.getBuilding().getId().equals(buildingId))
                .collect(Collectors.toList());

        return units.stream().map(this::buildStatus).collect(Collectors.toList());
    }

    public UnitChargeStatus getChargeStatusForUnit(Long unitId) {
        Unit unit = unitRepository.findById(unitId)
                .orElseThrow(() -> new RuntimeException("واحد پیدا نشد"));
        return buildStatus(unit);
    }

    private UnitChargeStatus buildStatus(Unit unit) {
        BigDecimal deposited = chargeDepositRepository.findByUnitId(unit.getId()).stream()
                .map(d -> d.getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal billsSpent = billShareRepository.findByUnitId(unit.getId()).stream()
                .filter(bs -> bs.isPaid())
                .map(bs -> bs.getShareAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal miscSpent = miscExpenseShareRepository.findByUnitId(unit.getId()).stream()
                .filter(ms -> ms.isPaid())
                .map(ms -> ms.getShareAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalSpent = billsSpent.add(miscSpent);
        BigDecimal remaining = deposited.subtract(totalSpent);

        return new UnitChargeStatus(unit.getId(), unit.getUnitNumber(), deposited, totalSpent, remaining);
    }
}
