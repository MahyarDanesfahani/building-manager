package com.buildingmanager.backend.service;

import com.buildingmanager.backend.dto.*;
import com.buildingmanager.backend.entity.*;
import com.buildingmanager.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MiscExpenseService {

    private final MiscExpenseRepository miscExpenseRepository;
    private final MiscExpenseShareRepository miscExpenseShareRepository;
    private final BuildingRepository buildingRepository;
    private final UnitRepository unitRepository;

    public MiscExpenseResponse createExpense(MiscExpenseCreateRequest request) {
        Building building = buildingRepository.findById(request.getBuildingId())
                .orElseThrow(() -> new RuntimeException("ساختمان پیدا نشد"));

        List<Unit> units = unitRepository.findAll().stream()
                .filter(u -> u.getBuilding().getId().equals(building.getId()))
                .collect(Collectors.toList());

        if (units.isEmpty()) {
            throw new RuntimeException("هیچ واحدی برای این ساختمان ثبت نشده");
        }

        MiscExpense expense = new MiscExpense();
        expense.setTitle(request.getTitle());
        expense.setDescription(request.getDescription());
        expense.setAmount(request.getAmount());
        expense.setExpenseDate(request.getExpenseDate());
        expense.setBuilding(building);
        expense = miscExpenseRepository.save(expense);

        BigDecimal sharePerUnit = request.getAmount()
                .divide(BigDecimal.valueOf(units.size()), 0, RoundingMode.HALF_UP);

        for (Unit unit : units) {
            MiscExpenseShare share = new MiscExpenseShare();
            share.setMiscExpense(expense);
            share.setUnit(unit);
            share.setShareAmount(sharePerUnit);
            share.setPaid(false);
            miscExpenseShareRepository.save(share);
        }

        return getExpenseResponse(expense.getId());
    }

    public List<MiscExpenseResponse> getExpensesForBuilding(Long buildingId) {
        return miscExpenseRepository.findByBuildingId(buildingId).stream()
                .map(e -> getExpenseResponse(e.getId()))
                .collect(Collectors.toList());
    }

    public MiscExpenseResponse getExpenseResponse(Long expenseId) {
        MiscExpense expense = miscExpenseRepository.findById(expenseId)
                .orElseThrow(() -> new RuntimeException("مورد جانبی پیدا نشد"));

        List<MiscExpenseShareResponse> shares = miscExpenseShareRepository.findByMiscExpenseId(expenseId).stream()
                .map(s -> new MiscExpenseShareResponse(
                        s.getId(), s.getUnit().getId(), s.getUnit().getUnitNumber(),
                        s.getShareAmount(), s.isPaid()))
                .collect(Collectors.toList());

        return new MiscExpenseResponse(expense.getId(), expense.getTitle(), expense.getDescription(),
                expense.getAmount(), expense.getExpenseDate(), shares);
    }

    public void markShareAsPaid(Long shareId) {
        MiscExpenseShare share = miscExpenseShareRepository.findById(shareId)
                .orElseThrow(() -> new RuntimeException("سهم پیدا نشد"));
        share.setPaid(true);
        miscExpenseShareRepository.save(share);
    }
}
