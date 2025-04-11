package com.vahana.utils.v1.users;

import com.vahana.dtos.v1.users.ShortUserDTO;
import com.vahana.dtos.v1.users.UserDTO;
import com.vahana.entities.v1.users.UserEntity;
import com.vahana.utils.v1.addresses.AddressUtils;

public final class UserUtils {
    public static UserDTO convertUserEntityToUserDto(UserEntity user) {
        return new UserDTO()
            .setId(user.getId())
            .setUsername(user.getUsername())
            .setRole(user.getRole())
            .setLastname(user.getLastname())
            .setFirstname(user.getFirstname())
            .setEmail(user.getEmail())
            .setPhoneNumber(user.getPhoneNumber())
            .setPictureID(user.getPicture().getId())
            .setAddressDTO(AddressUtils.convertAddressEntityToAddressDto(user.getAddress()));
    }

    public static ShortUserDTO convertUserEntityToShortUserDTO(UserEntity user) {
        return new ShortUserDTO()
                .setId(user.getId())
                .setUsername(user.getUsername())
                .setPictureID(user.getPicture().getId());
    }
}
