package com.vahana.models.v0.users;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.vahana.utils.AuditActionType;

import java.time.Instant;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "UUID", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "created", nullable = false, updatable = false)
    private Instant created = Instant.now();

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserModel user;

    @Enumerated(EnumType.STRING)
    @Column(name = "action", nullable = false, updatable = false)
    private AuditActionType actionType;

    @Column(name = "field", nullable = false, updatable = false)
    private String field;

    @Column(name = "old_value", nullable = false, updatable = false)
    private String oldValue;

    @Column(name = "new_value", nullable = false, updatable = false)
    private String newValue;
}
