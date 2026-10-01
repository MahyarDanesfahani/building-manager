package com.buildingmanager.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Entity
@Table(name = "charge_deposits")
@Getter
@Setter
public class ChargeDeposit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;
    private LocalDateTime depositDate;

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private Unit unit;
}
