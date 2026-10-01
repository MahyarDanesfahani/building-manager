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
public class BillService {

    private final BillRepository billRepository;
    private final BillShareRepository billShareRepository;
    private final BuildingRepository buildingRepository;
    private final UnitRepository unitRepository;

    public BillResponse createBill(BillCreateRequest request) {
        Building building = buildingRepository.findById(request.getBuildingId())
                .orElseThrow(() -> new RuntimeException("ساختمان پیدا نشد"));

        List<Unit> units = unitRepository.findAll().stream()
                .filter(u -> u.getBuilding().getId().equals(building.getId()))
                .collect(Collectors.toList());

        if (units.isEmpty()) {
            throw new RuntimeException("هیچ واحدی برای این ساختمان ثبت نشده");
        }

        Bill bill = new Bill();
        bill.setTitle(request.getTitle());
        bill.setTotalAmount(request.getTotalAmount());
        bill.setIssueDate(request.getIssueDate());
        bill.setDueDate(request.getDueDate());
        bill.setBuilding(building);
        bill = billRepository.save(bill);

        BigDecimal sharePerUnit = request.getTotalAmount()
                .divide(BigDecimal.valueOf(units.size()), 0, RoundingMode.HALF_UP);

        for (Unit unit : units) {
            BillShare share = new BillShare();
            share.setBill(bill);
            share.setUnit(unit);
            share.setShareAmount(sharePerUnit);
            share.setPaid(false);
            billShareRepository.save(share);
        }

        return getBillResponse(bill.getId());
    }

    public List<BillResponse> getBillsForBuilding(Long buildingId) {
        return billRepository.findByBuildingId(buildingId).stream()
                .map(b -> getBillResponse(b.getId()))
                .collect(Collectors.toList());
    }

    public BillResponse getBillResponse(Long billId) {
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new RuntimeException("قبض پیدا نشد"));

        List<BillShareResponse> shares = billShareRepository.findByBillId(billId).stream()
                .map(s -> new BillShareResponse(
                        s.getId(), s.getUnit().getId(), s.getUnit().getUnitNumber(),
                        s.getShareAmount(), s.isPaid()))
                .collect(Collectors.toList());

        return new BillResponse(bill.getId(), bill.getTitle(), bill.getTotalAmount(),
                bill.getIssueDate(), bill.getDueDate(), shares);
    }

    public void markShareAsPaid(Long shareId) {
        BillShare share = billShareRepository.findById(shareId)
                .orElseThrow(() -> new RuntimeException("سهم پیدا نشد"));
        share.setPaid(true);
        billShareRepository.save(share);
    }
}