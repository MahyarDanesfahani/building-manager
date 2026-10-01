package com.buildingmanager.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class MiscExpenseShareResponse {
    private Long shareId;
    private Long unitId;
    private String unitNumber;
    private BigDecimal shareAmount;
    private boolean isPaid;
}
