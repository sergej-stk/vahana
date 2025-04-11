package com.vahana.utils.v1.addresses;

import com.vahana.dtos.v1.addresses.AddressDTO;
import com.vahana.dtos.v1.addresses.CoordinatesDTO;
import com.vahana.dtos.v1.addresses.UpdateAddressDTO;
import com.vahana.entities.v1.addresses.AddressEntity;

public final class AddressUtils {
    public static AddressDTO convertAddressEntityToAddressDto(AddressEntity address) {
        return new AddressDTO()
                .setId(address.getId())
                .setStreet(address.getStreet())
                .setHouseNumber(address.getHouseNumber())
                .setPostalCode(address.getPostalCode())
                .setCity(address.getCity())
                .setCountry(address.getCountry())
                .setCoordinates(convertAdressEntityToCoordinatesDTO(address));
    }

    public static AddressEntity convertUpdateAddressDTOToAddressEntity(UpdateAddressDTO updateAddressDTO) {
        return new AddressEntity()
                .setStreet(updateAddressDTO.getStreet())
                .setHouseNumber(updateAddressDTO.getHouseNumber())
                .setPostalCode(updateAddressDTO.getPostalCode())
                .setCity(updateAddressDTO.getCity())
                .setCountry(updateAddressDTO.getCountry())
                .setLatitude(updateAddressDTO.getCreateCoordinates().getLatitude())
                .setLongitude(updateAddressDTO.getCreateCoordinates().getLongitude());
    }

    public static CoordinatesDTO convertAdressEntityToCoordinatesDTO(AddressEntity address) {
        return new CoordinatesDTO()
                .setLatitude(address.getLatitude())
                .setLongitude(address.getLongitude());
    }
}
