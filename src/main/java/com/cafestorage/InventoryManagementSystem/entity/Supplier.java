package com.cafestorage.InventoryManagementSystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "suppliers")
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false,unique = true)
    private String contact;

    @Column(nullable = false,unique = true)
    @Email
    private String email;

    private String address;

    @Column(nullable = false)
    private boolean status = true;




}
