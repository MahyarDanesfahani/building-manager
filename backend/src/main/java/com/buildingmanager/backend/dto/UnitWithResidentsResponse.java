package com.buildingmanager.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UnitWithResidentsResponse {
    private Long unitId;
    private String unitNumber;
    private String postalCode;
    private String unitPhone;
    private List<ResidentResponse> residents;
}
