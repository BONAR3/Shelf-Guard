package com.bonar.Shelf_Guard.entities;

import com.bonar.Shelf_Guard.enums.Permissions;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "permissions_log")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PermissionsLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "granted_by_staff_id", nullable = false)
    private Staff grantedBy; // which owner/manager assigned this permission to the role

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Permissions permission;

    @CreationTimestamp
    @Column(nullable = false)
    private Instant grantedAt;

}
