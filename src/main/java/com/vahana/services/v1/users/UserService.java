package com.vahana.services.v1.users;

import com.vahana.dtos.v1.users.*;
import com.vahana.entities.v1.users.Role;
import com.vahana.entities.v1.users.UserEntity;
import com.vahana.repositories.v1.users.UserRepository;
import com.vahana.security.exceptions.BadRequestException;
import com.vahana.security.exceptions.NoChangesException;
import com.vahana.security.exceptions.UserNotFoundException;
import com.vahana.security.exceptions.UsernameOrEMailExistsException;
import com.vahana.services.v1.auth.PermissionService;
import com.vahana.services.v1.emails.EmailService;
import com.vahana.services.v1.emails.EmailTemplatesType;
import com.vahana.utils.v1.users.UserUtils;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public final class UserService {
    private final UserRepository userRepository;
    private final PermissionService permissionService;
    private final EmailService emailService;

    public ResponseEntity<ShortUserDTO> getUser(UUID id) {
        log.info("Fetching user with ID: {}", id);
        permissionService.checkUserPermission(getAuthenticatedUserEntity(), List.of(Role.USER, Role.ADMIN));
        var user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        log.info("User found: {}", user.getUsername());
        return ResponseEntity.ok(UserUtils.convertUserEntityToShortUserDTO(user));
    }

    public UserEntity getAuthenticatedUserEntity() {
        log.info("Fetching current user");
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var email = ((UserEntity) authentication.getPrincipal()).getEmail();
        var user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        log.info("User fetched {}", user.getUsername());
        return user;
    }

    public ResponseEntity<UserDTO> getAuthenticatedUser() {
        log.info("Fetching authenticated user");
        var user = getAuthenticatedUserEntity();
        permissionService.checkUserPermission(user, List.of(Role.USER, Role.ADMIN));
        return ResponseEntity.ok(UserUtils.convertUserEntityToUserDto(user));
    }

    public ResponseEntity<UserDTO> updateAuthenticatedUser(UpdateUserDTO updateUserDTO) {
        log.info("Updating authenticated user");
        var user = getAuthenticatedUserEntity();
        permissionService.checkUserPermission(user, List.of(Role.USER, Role.ADMIN));
        if(updateUserDTO == null)
            throw new NoChangesException();
        return ResponseEntity.ok(UserUtils.convertUserEntityToUserDto(updateUser(user, updateUserDTO)));
    }

    private UserEntity updateUser(UserEntity user, UpdateUserDTO updateUserDTO) {
        var changed = false;

        if (updateUserDTO == null || user == null) {
            log.info("Invalid user update request");
            return user;
        }

        if((updateUserDTO.getFirstname() != null) && (!updateUserDTO.getFirstname().equals(user.getFirstname()))) {
            log.info("Updating first name from '{}' to '{}'", user.getFirstname(), updateUserDTO.getFirstname());
            user.setFirstname(updateUserDTO.getFirstname());
            changed = true;
        }

        if ((updateUserDTO.getLastname() != null) && (!updateUserDTO.getLastname().equals(user.getLastname()))) {
            log.info("Updating last name from '{}' to '{}'", user.getLastname(), updateUserDTO.getLastname());
            user.setLastname(updateUserDTO.getLastname());
            changed = true;
        }

        if ((updateUserDTO.getPhoneNumber() != null) && (!updateUserDTO.getPhoneNumber().equals(user.getPhoneNumber()))) {
            log.info("Updating phone-number from '{}' to '{}'", user.getPhoneNumber(), updateUserDTO.getPhoneNumber());
            user.setPhoneNumber(updateUserDTO.getPhoneNumber());
            changed = true;
        }

        if ((updateUserDTO.getEmail() != null) && (!updateUserDTO.getEmail().equals(user.getEmail()))) {
            log.info("Updating email from '{}' to '{}'", user.getEmail(), updateUserDTO.getEmail());
            var user_exists = userRepository.findByEmail(updateUserDTO.getEmail()).orElse(null);

            if (!(user_exists == null))
                throw new UsernameOrEMailExistsException();

            emailService.sendEmail(
                    EmailTemplatesType.EMAIL_CHANGED,
                    user,
                    user.getEmail()
            );

            user.setEmail(updateUserDTO.getEmail());
            changed = true;
        }

        if ((updateUserDTO.getUsername() != null) && (!updateUserDTO.getUsername().equals(user.getUsername()))) {
            log.info("Updating username from '{}' to '{}'", user.getUsername(), updateUserDTO.getUsername());
            var user_exists = userRepository.findByUsername(updateUserDTO.getUsername()).orElse(null);

            if (!(user_exists == null))
                throw new UsernameOrEMailExistsException();
            user.setUsername(updateUserDTO.getUsername());
            changed = true;
        }

        if (updateUserDTO.getAddressDTO() != null) {
            if ((updateUserDTO.getAddressDTO().getCity() != null) && (!updateUserDTO.getAddressDTO().getCity().equals(user.getAddress().getCity()))) {
                log.info("Updating city from '{}' to '{}'", user.getAddress().getCity(), updateUserDTO.getAddressDTO().getCity());
                user.getAddress().setCity(updateUserDTO.getAddressDTO().getCity());
                changed = true;
            }

            if ((updateUserDTO.getAddressDTO().getStreet() != null) && (!updateUserDTO.getAddressDTO().getStreet().equals(user.getAddress().getStreet()))) {
                log.info("Updating street from '{}' to '{}'", user.getAddress().getCity(), updateUserDTO.getAddressDTO().getCity());
                user.getAddress().setStreet(updateUserDTO.getAddressDTO().getStreet());
                changed = true;
            }

            if ((updateUserDTO.getAddressDTO().getPostalCode() != null) && (!updateUserDTO.getAddressDTO().getPostalCode().equals(user.getAddress().getPostalCode()))) {
                log.info("Updating postalcode from '{}' to '{}'", user.getAddress().getPostalCode(), updateUserDTO.getAddressDTO().getPostalCode());
                user.getAddress().setPostalCode(updateUserDTO.getAddressDTO().getPostalCode());
                changed = true;
            }

            if ((updateUserDTO.getAddressDTO().getHouseNumber() != null) && (!updateUserDTO.getAddressDTO().getHouseNumber().equals(user.getAddress().getHouseNumber()))) {
                log.info("Updating house number from '{}' to '{}'", user.getAddress().getHouseNumber(), updateUserDTO.getAddressDTO().getHouseNumber());
                user.getAddress().setHouseNumber(updateUserDTO.getAddressDTO().getHouseNumber());
                changed = true;
            }

            if ((updateUserDTO.getAddressDTO().getCountry() != null) && (!updateUserDTO.getAddressDTO().getCountry().equals(user.getAddress().getCountry()))) {
                log.info("Updating country from '{}' to '{}'", user.getAddress().getCountry(), updateUserDTO.getAddressDTO().getCountry());
                user.getAddress().setCountry(updateUserDTO.getAddressDTO().getCountry());
                changed = true;
            }

            if(updateUserDTO.getAddressDTO().getCreateCoordinates() != null) {
                if (updateUserDTO.getAddressDTO().getCreateCoordinates().getLatitude() != null){
                    log.info("Updating coordinates latitude from '{}' to '{}'", user.getAddress().getLatitude(), updateUserDTO.getAddressDTO().getCreateCoordinates().getLatitude());
                    user.getAddress().setLatitude(updateUserDTO.getAddressDTO().getCreateCoordinates().getLatitude());
                    changed = true;
                }

                if (updateUserDTO.getAddressDTO().getCreateCoordinates().getLongitude() != null){
                    log.info("Updating coordinates longtitude from '{}' to '{}'", user.getAddress().getLongitude(), updateUserDTO.getAddressDTO().getCreateCoordinates().getLongitude());
                    user.getAddress().setLongitude(updateUserDTO.getAddressDTO().getCreateCoordinates().getLongitude());
                    changed = true;
                }
            }
        }

        if (!changed)
            throw new NoChangesException();
        return userRepository.save(user);
    }

    public ResponseEntity<UserDTO> updateUser(UUID id, UpdateUserDTO updateUserDTO) {
        log.info("Updating user with ID: {}", id);
        permissionService.checkUserPermission(getAuthenticatedUserEntity(), List.of(Role.ADMIN));
        if(updateUserDTO == null)
            throw new NoChangesException();
        var user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        return ResponseEntity.ok(UserUtils.convertUserEntityToUserDto(updateUser(user, updateUserDTO)));
    }

    public ResponseEntity<ShortUserDTOList> getAllUser(FilterUserDTO filter) {
        permissionService.checkUserPermission(getAuthenticatedUserEntity(), List.of(Role.USER, Role.ADMIN));
        if (filter == null)
            filter = new FilterUserDTO();
        validateFilter(filter);
        log.info("Fetching all users - Page: {}, Size: {}", filter.getPage(), filter.getSize());
        var entities = userRepository.findAll(PageRequest.of(filter.getPage(), filter.getSize()));
        var total = userRepository.count();
        var result = entities.map(UserUtils::convertUserEntityToShortUserDTO).toList();
        log.info("Retrieved {} users", result.size());
        return ResponseEntity.ok(
                new ShortUserDTOList(
                        result,
                        PageRequest.of(filter.getPage(), filter.getSize()),
                        total
                )
        );
    }

    public void validateFilter(@Nullable FilterUserDTO filter) {
        if (filter == null)
            return;

        if(filter.getSize() < 1 || filter.getSize() > 100)
            throw new BadRequestException("Filter size '" + filter.getSize() + "' is invalid (Range 1..100).");

        if(filter.getPage() < 0)
            throw new BadRequestException("Filter page '" + filter.getPage() + "' is invalid (Range 0..n).");
    }

    public ResponseEntity<Void> deleteUser(UUID id) {
        log.info("Deleting user with ID: {}", id);
        permissionService.checkUserPermission(getAuthenticatedUserEntity(), List.of(Role.ADMIN));
        if (!userRepository.existsById(id))
            throw new UserNotFoundException();
        userRepository.deleteById(id);
        log.info("User with ID {} deleted", id);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<Void> deleteCurrentUser() {
        var user = getAuthenticatedUserEntity();
        log.info("Deleting authenticated user with ID: {}", user.getId());
        permissionService.checkUserPermission(user, List.of(Role.USER, Role.ADMIN));
        userRepository.delete(user);
        log.info("Authenticated user {} deleted", user.getId());
        return ResponseEntity.noContent().build();
    }
}
