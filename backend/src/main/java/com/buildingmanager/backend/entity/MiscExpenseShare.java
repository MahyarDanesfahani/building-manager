package com.buildingmanager.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
@Table(name = "misc_expense_shares")
@Getter
@Setter
public class MiscExpenseShare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal shareAmount;
    private boolean isPaid;

    @ManyToOne
    @JoinColumn(name = "misc_expense_id")
    private MiscExpense miscExpense;

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private Unit unit;
}
