package com.buildingmanager.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class UnitChargeStatus {
    private Long unitId;
    private String unitNumber;
    private BigDecimal totalDeposited;   // کل واریزی
    private BigDecimal totalSpent;       // کل هزینه‌شده (قبض‌ها + جانبی‌های پرداخت‌شده)
    private BigDecimal remaining;        // مانده
}
