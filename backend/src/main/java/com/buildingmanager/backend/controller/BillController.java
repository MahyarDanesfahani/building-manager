package com.buildingmanager.backend.controller;

import com.buildingmanager.backend.dto.BillCreateRequest;
import com.buildingmanager.backend.dto.BillResponse;
import com.buildingmanager.backend.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bills")
@RequiredArgsConstructor
public class BillController {

    private final BillService billService;

    @PostMapping
    public BillResponse create(@RequestBody BillCreateRequest request) {
        return billService.createBill(request);
    }

    @GetMapping("/building/{buildingId}")
    public List<BillResponse> getBills(@PathVariable Long buildingId) {
        return billService.getBillsForBuilding(buildingId);
    }

    @PutMapping("/share/{shareId}/pay")
    public void markPaid(@PathVariable Long shareId) {
        billService.markShareAsPaid(shareId);
    }
}
