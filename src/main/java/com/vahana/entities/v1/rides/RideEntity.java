package com.vahana.entities.v1.rides;

import com.vahana.entities.v1.addresses.AddressEntity;
import com.vahana.entities.v1.users.UserEntity;
import com.vahana.utils.v1.rides.RideStatusType;
import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Table(name = "rides")
public final class RideEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "UUID", updatable = false, nullable = false)
    private UUID id;

    @CreationTimestamp
    @Column(name = "created", nullable = false, updatable = false)
    private Instant created;

    @UpdateTimestamp
    @Column(name = "modified", nullable = false)
    private Instant modified;

    @Column(name = "departure", nullable = false)
    private Instant departure;

    @ManyToOne()
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "driver_id", referencedColumnName = "id", nullable = false)
    private UserEntity driver;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "origin_id", referencedColumnName = "id", nullable = false)
    private AddressEntity origin;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "destination_id", referencedColumnName = "id", nullable = false)
    private AddressEntity destination;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private RideStatusType status;

    @Column(name = "available_seats")
    private Integer availableSeats;

    @OneToMany(mappedBy = "ride", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserRideEntity> userRides;
}
