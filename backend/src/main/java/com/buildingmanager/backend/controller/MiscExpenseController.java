package com.buildingmanager.backend.controller;

import com.buildingmanager.backend.dto.MiscExpenseCreateRequest;
import com.buildingmanager.backend.dto.MiscExpenseResponse;
import com.buildingmanager.backend.service.MiscExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/misc-expenses")
@RequiredArgsConstructor
public class MiscExpenseController {

    private final MiscExpenseService miscExpenseService;

    @PostMapping
    public MiscExpenseResponse create(@RequestBody MiscExpenseCreateRequest request) {
        return miscExpenseService.createExpense(request);
    }

    @GetMapping("/building/{buildingId}")
    public List<MiscExpenseResponse> getExpenses(@PathVariable Long buildingId) {
        return miscExpenseService.getExpensesForBuilding(buildingId);
    }

    @PutMapping("/share/{shareId}/pay")
    public void markPaid(@PathVariable Long shareId) {
        miscExpenseService.markShareAsPaid(shareId);
    }
}