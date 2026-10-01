package com.buildingmanager.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class ResidentResponse {
    private Long id;
    private String fullName;
    private String phoneNumber;
    private String role;
    private boolean isOwner;
    private LocalDate residencyStartDate;
    private LocalDate residencyEndDate;
}