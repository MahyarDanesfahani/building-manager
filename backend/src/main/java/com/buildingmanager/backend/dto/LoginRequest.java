package com.buildingmanager.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    private String postalCode;
    private String nationalCode;
}