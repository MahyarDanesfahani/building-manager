package com.buildingmanager.backend.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class ResidentCreateRequest {
    private String fullName;
    private String nationalCode;
    private String phoneNumber;
    private String role;          // "MANAGER" یا "MEMBER"
    private boolean isOwner;
    private LocalDate residencyStartDate;
    private LocalDate residencyEndDate;
    private Long unitId;
}