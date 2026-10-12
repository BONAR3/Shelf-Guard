package com.bonar.Shelf_Guard.entities;

import com.bonar.Shelf_Guard.enums.PermissionType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.security.Permission;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@DiscriminatorValue("STAFF")
public class Staff extends Users {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "business_id", nullable = false)
    private Business business;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "staff_permission_overrides", joinColumns = @JoinColumn(name = "staff_id"))
    @MapKeyColumn(name = "permission_name")
    @MapKeyEnumerated(EnumType.STRING)
    @Column(name = "access")
    @Enumerated(EnumType.STRING)
    private Map<Permission, PermissionType> permissionOverrides = new HashMap<>();

    @ManyToMany
    @JoinTable(name = "user_location_link", joinColumns = @JoinColumn(name = "staff_id"),
            inverseJoinColumns = @JoinColumn(name = "location_id"))
    private Set<Location> locations = new HashSet<>();

    @CreationTimestamp
    @Column(name = "created_at")
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_a")
    private LocalDateTime updatedAt;
}
