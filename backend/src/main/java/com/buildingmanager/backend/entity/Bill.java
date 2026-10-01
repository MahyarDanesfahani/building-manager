package com.buildingmanager.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "bills")
@Getter
@Setter
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;           // مثلاً "قبض آب - مهر 1405"
    private BigDecimal totalAmount;
    private LocalDate issueDate;
    private LocalDate dueDate;

    @ManyToOne
    @JoinColumn(name = "building_id")
    private Building building;

    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL)
    private List<BillShare> shares;
}
