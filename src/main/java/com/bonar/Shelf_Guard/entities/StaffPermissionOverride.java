package com.bonar.Shelf_Guard.entities;

import com.bonar.Shelf_Guard.enums.PermissionType;
import com.bonar.Shelf_Guard.enums.Permissions;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "staff_permission_overrides")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class StaffPermissionOverride {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "staff_id", nullable = false)
    private Staff staff;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Permissions permission;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PermissionType type; //GRANTED or REVOKED

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "granted_by_staff_id", nullable = false)
    private Staff grantedBy;

    @CreationTimestamp
    @Column(nullable = false)
    private Instant grantedAt;
}
