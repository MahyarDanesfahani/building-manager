package com.buildingmanager.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class MiscExpenseResponse {
    private Long expenseId;
    private String title;
    private String description;
    private BigDecimal amount;
    private LocalDate expenseDate;
    private List<MiscExpenseShareResponse> shares;
}
