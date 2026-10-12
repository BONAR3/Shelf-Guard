package com.bonar.Shelf_Guard.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.security.Permission;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "roles")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "business_id", nullable = false)
    private Business business;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "role_permissions", joinColumns = @JoinColumn(name = "role_id"))
    @Column(name = "permission_name")
    @Enumerated(EnumType.STRING)
    private Set<Permission> permissions = new HashSet<>();

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "created_by", nullable = false)
    private Business createdBy;

    @CreationTimestamp
    @Column(name = "created_at")
    private Instant createdAt;

}
