package com.buildingmanager.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Table(name = "residents")
@Getter
@Setter
public class Resident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String nationalCode;    // کد ملی - رمز عبور (بعداً هش می‌کنیم)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Role role;              // MANAGER یا MEMBER

    private LocalDate residencyStartDate;
    private LocalDate residencyEndDate;   // برای مستاجرها؛ برای مالک می‌تونه خالی باشه

    private boolean isOwner;        // مالک است یا مستاجر

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private Unit unit;
}
