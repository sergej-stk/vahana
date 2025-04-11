package com.vahana.utils.v1.rides;

import com.vahana.entities.v1.rides.RideEntity;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public final class RideSpecificationFilter {
    public static Specification<RideEntity> hasDestinationCity(String city) {
        return (root, query, criteriaBuilder) -> {
            if (city == null || city.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("destination").get("city")), "%" + city.toLowerCase() + "%");
        };
    }

    public static Specification<RideEntity> hasOriginCity(String city) {
        return (root, query, criteriaBuilder) -> {
            if (city == null || city.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("origin").get("city")), "%" + city.toLowerCase() + "%");
        };
    }

    public static Specification<RideEntity> hasDriverUsername(String username) {
        return (root, query, criteriaBuilder) -> {
            if (username == null || username.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("driver").get("username")),
                    "%" + username.toLowerCase() + "%");
        };
    }

    public static Specification<RideEntity> hasStatus(String status) {
        return (root, query, criteriaBuilder) -> {
            if (status == null || status.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("status")),
                    status.toLowerCase());
        };
    }

    public static Specification<RideEntity> hasDriverID(UUID id) {
        return (root, query, criteriaBuilder) -> {
            if (id == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("driver").get("id"), id);
        };
    }
}
