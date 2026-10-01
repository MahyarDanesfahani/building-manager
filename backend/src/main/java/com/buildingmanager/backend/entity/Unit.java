package com.buildingmanager.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@Table(name = "units")
@Getter
@Setter
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String unitNumber;      // شماره واحد

    @Column(unique = true)
    private String postalCode;      // کدپستی - همون یوزرنیم لاگین

    private String unitPhone;       // شماره تماس واحد

    @ManyToOne
    @JoinColumn(name = "building_id")
    private Building building;

    @OneToMany(mappedBy = "unit", cascade = CascadeType.ALL)
    private List<Resident> residents;

    @OneToMany(mappedBy = "unit", cascade = CascadeType.ALL)
    private List<ChargeDeposit> chargeDeposits;
}
