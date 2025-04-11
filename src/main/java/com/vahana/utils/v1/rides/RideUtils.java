package com.vahana.utils.v1.rides;

import com.vahana.dtos.v1.rides.RideDTO;
import com.vahana.dtos.v1.rides.RideRegistrationDto;
import com.vahana.entities.v1.rides.RideEntity;
import com.vahana.entities.v1.rides.UserRideEntity;
import com.vahana.utils.v1.addresses.AddressUtils;
import com.vahana.utils.v1.users.UserUtils;

public final class RideUtils {
    public static RideDTO convertRideEntityToRideDTO(RideEntity ride) {
        var result = new RideDTO()
                .setId(ride.getId())
                .setStatus(ride.getStatus())
                .setCreated(ride.getCreated())
                .setDeparture(ride.getDeparture())
                .setAvailableSeats(ride.getAvailableSeats());

        if (ride.getDriver() != null)
            result.setDriver(UserUtils.convertUserEntityToShortUserDTO(ride.getDriver()));

        if (ride.getOrigin() != null)
            result.setOrigin(AddressUtils.convertAddressEntityToAddressDto(ride.getOrigin()));

        if (ride.getDestination() != null)
            result.setDestination(AddressUtils.convertAddressEntityToAddressDto(ride.getDestination()));

        return result;
    }

    public static RideRegistrationDto convertUserRideEntityToRideRegistrationDto(UserRideEntity userRideEntity) {
        return new RideRegistrationDto()
                .setId(userRideEntity.getId())
                .setCreated(userRideEntity.getCreated())
                .setRideID(userRideEntity.getRide().getId())
                .setUserID(userRideEntity.getUser().getId());
    }
}