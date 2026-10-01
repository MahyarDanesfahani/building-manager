package com.buildingmanager.backend.dto;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class MiscExpenseCreateRequest {
    private String title;
    private String description;
    private BigDecimal amount;
    private LocalDate expenseDate;
    private Long buildingId;
}