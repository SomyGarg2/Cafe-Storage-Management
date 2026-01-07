package com.cafestorage.InventoryManagementSystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "raw_materials")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RawMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String name;

    private String category;

    @Column(nullable = false)
    private String unit;

    @Column(nullable = false)
    private Double quantity;

    @Column(nullable = false)
    private Double minStockLevel;

    @Column(nullable = false)
    private Boolean active = true;

    @ManyToOne
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

}
