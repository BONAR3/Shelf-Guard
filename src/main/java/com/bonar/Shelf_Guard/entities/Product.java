package com.bonar.Shelf_Guard.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "business_id", nullable = false)
    private Business business;

    @Column(nullable = false)
    private String name;

    private String sku; // optional barcode/stock-keeping unit, useful once you add scanning

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal costPrice; // what the business pays to acquire it

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal basePrice; // default sell price — outlets override via OutletProductPrice only when needed

    @Column(nullable = false)
    private boolean active = true; // soft-disable a discontinued product instead of deleting it

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;
}
