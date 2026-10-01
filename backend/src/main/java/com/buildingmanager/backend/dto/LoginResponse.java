package com.buildingmanager.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private String fullName;
    private String role;
    private Long unitId;
    private Long buildingId;
    private Long residentId;
}