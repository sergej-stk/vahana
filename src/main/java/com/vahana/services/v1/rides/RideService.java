package com.vahana.services.v1.rides;

import com.vahana.dtos.v1.notifications.CreateNotificationDTO;
import com.vahana.dtos.v1.rides.*;
import com.vahana.dtos.v1.users.FilterUserDTO;
import com.vahana.dtos.v1.users.ShortUserDTOList;
import com.vahana.entities.v1.rides.RideEntity;
import com.vahana.entities.v1.rides.UserRideEntity;
import com.vahana.entities.v1.users.Role;
import com.vahana.repositories.v1.rides.RideRepository;
import com.vahana.repositories.v1.rides.UserRideRepository;
import com.vahana.repositories.v1.users.UserRepository;
import com.vahana.security.exceptions.*;
import com.vahana.services.v1.auth.PermissionService;
import com.vahana.services.v1.notifications.NotificationService;
import com.vahana.services.v1.users.UserService;
import com.vahana.utils.v1.addresses.AddressUtils;
import com.vahana.utils.v1.notifications.NotificationType;
import com.vahana.utils.v1.rides.RideSpecificationFilter;
import com.vahana.utils.v1.rides.RideStatusType;
import com.vahana.utils.v1.rides.RideUtils;
import com.vahana.utils.v1.users.UserUtils;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public final class RideService {
    private final RideRepository rideRepository;
    private final UserRideRepository userRideRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final PermissionService permissionService;
    private final NotificationService notificationService;

    public ResponseEntity<RideDTO> createRide(CreateRideDTO createRideDTO) {
        if(createRideDTO.getDestination() == null || createRideDTO.getDeparture() == null || createRideDTO.getOrigin() == null)
            throw new BadRequestException("Destination and origin cannot be null.");

        if(createRideDTO.getDestination().getCreateCoordinates() == null || createRideDTO.getOrigin().getCreateCoordinates() == null)
            throw new BadRequestException("Coordinates cannot be null.");

        if(createRideDTO.getAvailableSeats() == null || createRideDTO.getAvailableSeats() < 1 || createRideDTO.getAvailableSeats() > 10)
            throw new BadRequestException("Available Seats cannot be less than 1 or greater than 10.");

        var user = userService.getAuthenticatedUserEntity();
        log.info("User {} is creating a new ride", user.getUsername());
        permissionService.checkUserPermission(user, List.of(Role.USER, Role.ADMIN));

        var origin = AddressUtils.convertUpdateAddressDTOToAddressEntity(createRideDTO.getOrigin());
        var destination = AddressUtils.convertUpdateAddressDTOToAddressEntity(createRideDTO.getDestination());
        var ride = rideRepository.save(new RideEntity()
                .setDriver(user)
                .setOrigin(origin)
                .setDestination(destination)
                .setDeparture(createRideDTO.getDeparture())
                .setAvailableSeats(createRideDTO.getAvailableSeats())
                .setStatus(RideStatusType.ACTIVE));
        log.info("Ride created with ID: {} by user {}", ride.getId(), user.getUsername());

        var notification =
                "Es wurde die Fahrt von '" + ride.getOrigin().getCity() + "' bis '" + ride.getDestination().getCity() + "' erfoglreich angelegt.";
        notificationService.createNotification(
                user.getId(),
                new CreateNotificationDTO()
                        .setType(NotificationType.RIDE)
                        .setRead(false)
                        .setMessage(notification)
        );
        log.info("Notification created with message: {}", notification);

        return ResponseEntity.status(HttpStatus.CREATED).body(RideUtils.convertRideEntityToRideDTO(ride));
    }

    public ResponseEntity<RideDTOList> getAllRides(@Nullable FilterRideDTO filter) {
        var user = userService.getAuthenticatedUserEntity();
        permissionService.checkUserPermission(user, List.of(Role.USER, Role.ADMIN));

        if (filter == null)
            filter = new FilterRideDTO();
        var specs = validateFilter(filter);

        log.info("User {} is fetching all rides (page: {}, size: {})", user.getUsername(), filter.getPage(), filter.getSize());
        var entities = rideRepository.findAll(
                specs,
                PageRequest.of(
                        filter.getPage(),
                        filter.getSize(),
                        Sort.by(
                                filter.getSortdirection(),
                                filter.getSortby()
                        )
                )
        );
        var total = rideRepository.count(specs);
        entities.forEach(this::calculateAvailableSeats);
        var result = entities.map(RideUtils::convertRideEntityToRideDTO).toList();
        log.info("Fetched {} rides for user {}", result.size(), user.getUsername());

        return ResponseEntity.ok(
                new RideDTOList(
                        result,
                        PageRequest.of(
                                filter.getPage(),
                                filter.getSize(),
                                Sort.by(filter.getSortdirection(), filter.getSortby())),
                        total
                )
        );
    }

    private Specification<RideEntity> validateFilter(@Nullable @Valid FilterRideDTO filter) {
        if (filter == null)
            throw new NullPointerException("Filter cannot be null.");

        Specification<RideEntity> result = Specification.where(null);

        if(filter.getSize() < 1 || filter.getSize() > 100)
            throw new BadRequestException("Filter size '" + filter.getSize() + "' is invalid (Range 1..100).");

        if(filter.getPage() < 0)
            throw new BadRequestException("Filter page '" + filter.getPage() + "' is invalid (Range 0..n).");

        switch (filter.getSortby()) {
            case "departure", "created" -> {}
            default -> throw new BadRequestException("Filter sort '" + filter.getSortby() + "' is invalid.");
        }

        if (filter.getOrigincity() != null && !filter.getOrigincity().isEmpty()) {
            result = result.and(RideSpecificationFilter.hasOriginCity(filter.getOrigincity()));
        }
        if (filter.getDestinationcity() != null && !filter.getDestinationcity().isEmpty()) {
            result = result.and(RideSpecificationFilter.hasDestinationCity(filter.getDestinationcity()));
        }

        if (filter.getUsername() != null && !filter.getUsername().isEmpty()) {
            result = result.and(RideSpecificationFilter.hasDriverUsername(filter.getUsername()));
        }

        if (filter.getStatus() != null && filter.getStatus() != RideStatusType.NONE) {
            result = result.and(RideSpecificationFilter.hasStatus(filter.getStatus().toString()));
        }

        if (filter.getUserid() != null) {
            result = result.and(RideSpecificationFilter.hasDriverID(filter.getUserid()));
        }

        return result;
    }

    public ResponseEntity<RideDTOList> getAuthenticatedUserRides(@Nullable @Valid FilterRideDTO filter) {
        var user = userService.getAuthenticatedUserEntity();
        permissionService.checkUserPermission(user, List.of(Role.USER, Role.ADMIN));

        if (filter == null)
            filter = new FilterRideDTO();
        filter.setUserid(user.getId());
        var specs = validateFilter(filter);

        log.info("Fetching authenticated user rides");
        var entities = rideRepository.findAll(
                specs,
                PageRequest.of(
                        filter.getPage(),
                        filter.getSize(),
                        Sort.by(
                                filter.getSortdirection(),
                                filter.getSortby()
                        )
                )
        );
        var total = rideRepository.count(specs);

        entities.forEach(this::calculateAvailableSeats);
        var result = entities.map(RideUtils::convertRideEntityToRideDTO);
        log.info("User {} is retrieving their own rides with filter: {}", user.getUsername(), filter);

        log.info("User {} retrieved {} rides from their own records", user.getUsername(), result.getSize());
        return ResponseEntity.ok(
                new RideDTOList(
                        result.toList(),
                        PageRequest.of(
                                filter.getPage(),
                                filter.getSize(),
                                Sort.by(filter.getSortdirection(), filter.getSortby())),
                        total
                )
        );
    }

    public ResponseEntity<RideDTO> getRide(UUID id) {
        var user = userService.getAuthenticatedUserEntity();
        log.info("User {} is attempting to fetch ride with ID: {}", user.getUsername(), id);
        permissionService.checkUserPermission(user, List.of(Role.USER, Role.ADMIN));
        var result = rideRepository.findById(id).orElseThrow(RideNotFoundException::new);
        calculateAvailableSeats(result);
        log.info("Ride with ID: {} successfully retrieved by user {}", id, user.getUsername());
        return ResponseEntity.ok(RideUtils.convertRideEntityToRideDTO(result));
    }

    public ResponseEntity<Void> deleteAAuthenticatedUserRide(UUID id) {
        var user = userService.getAuthenticatedUserEntity();
        log.info("User {} is attempting to delete ride with ID: {}", user.getUsername(), id);
        permissionService.checkUserPermission(user, List.of(Role.USER, Role.ADMIN));

        var ride = rideRepository.findById(id).orElseThrow(RideNotFoundException::new);
        if(!ride.getDriver().getId().equals(user.getId()))
            throw new UserPermissionException();

        if(ride.getStatus() == RideStatusType.CANCELED)
            throw new BadRequestException("Ride already cancelled.");

        ride.setStatus(RideStatusType.CANCELED);
        rideRepository.save(ride);

        log.info("Ride with ID: {} successfully deleted by user {}", id, user.getUsername());
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<Void> deleteRideByID(UUID id) {
        var user = userService.getAuthenticatedUserEntity();
        log.info("User {} is attempting to delete ride with ID: {}", user.getUsername(), id);
        permissionService.checkUserPermission(user, List.of(Role.ADMIN));

        var ride = rideRepository.findById(id).orElseThrow(RideNotFoundException::new);

        ride.setStatus(RideStatusType.CANCELED);
        rideRepository.save(ride);

        userRideRepository.findAllByRide_id(id).forEach(userRide ->
             notificationService.createNotification(
                userRide.getUser().getId(),
                new CreateNotificationDTO()
                        .setType(NotificationType.RIDE)
                        .setRead(false)
                        .setMessage("Der Benutzer " + user.getUsername() + " hat die Fahrt von '" + ride.getOrigin().getCity() + "' nach '" + ride.getDestination().getCity() + "' storniert.")
             )
        );

        log.info("Ride with ID: {} successfully deleted by user {}", id, user.getUsername());
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<RideRegistrationDto> joinRideByID(UUID id) {
        var user = userService.getAuthenticatedUserEntity();
        log.info("User {} is attempting to join ride with ID: {}", user.getUsername(), id);
        permissionService.checkUserPermission(user, List.of(Role.USER, Role.ADMIN));

        var ride = rideRepository.findById(id).orElseThrow(RideNotFoundException::new);
        if(ride.getDriver().getId().equals(user.getId()))
            throw new DriverNotAllowedToJoinException(id, "User is the driver of this ride");

        if(ride.getStatus() == RideStatusType.CANCELED || ride.getStatus() == RideStatusType.COMPLETED)
            throw new UserNotAllowedException("Ride is already canceled or completed");

        if(userRideRepository.existsByUserAndRide(user, ride))
            throw new UserNotAllowedException("User is already in ride");

        if(userRideRepository.countByRide_Id(ride.getId()) >= ride.getAvailableSeats())
            throw new UserNotAllowedException("Ride is already full.");

        var registration = userRideRepository.save(new UserRideEntity()
                .setUser(user)
                .setRide(ride)
        );
        log.info("User {} has successfully joined ride with ID: {}", user.getUsername(), id);

        var notification =
                "Du bist der Fahrt von '" + ride.getOrigin().getCity() + "' bis '" + ride.getDestination().getCity() + "' erfolgreich beigetreten.";
        notificationService.createNotification(
                user.getId(),
                new CreateNotificationDTO()
                        .setType(NotificationType.RIDE)
                        .setRead(false)
                        .setMessage(notification)
        );
        log.info("Notification created with message: {}", notification);

        notification =
                user.getUsername() + " ist deiner Fahrt von '" + ride.getOrigin().getCity() + "' bis '" + ride.getDestination().getCity() +
                        "' beigetreten.";
        notificationService.createNotification(
                ride.getDriver().getId(),
                new CreateNotificationDTO()
                        .setType(NotificationType.RIDE)
                        .setRead(false)
                        .setMessage(notification)
        );
        log.info("Notification created with message: {}", notification);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                RideUtils.convertUserRideEntityToRideRegistrationDto(registration));
    }

    public ResponseEntity<Void> leaveRideById(UUID id) {
        var user = userService.getAuthenticatedUserEntity();
        log.info("User {} is attempting to leave ride with ID: {}", user.getUsername(), id);
        permissionService.checkUserPermission(user, List.of(Role.USER, Role.ADMIN));

        var ride = rideRepository.findById(id).orElseThrow(RideNotFoundException::new);

        if(!userRideRepository.existsByUserAndRide(user, ride))
            throw new UserNotAllowedException("User is not part of ride");

        if(ride.getStatus() == RideStatusType.CANCELED || ride.getStatus() == RideStatusType.COMPLETED)
            throw new UserNotAllowedException("Ride is already canceled or completed");

        userRideRepository.deleteByUserAndRide(user, ride);

        var notification =
                "Nutzer " + user.getUsername() + " hat deine Fahrt von '" + ride.getOrigin().getCity()  + "' nach '" + ride.getDestination().getCity()  + "' verlassen.";
        notificationService.createNotification(
                ride.getDriver().getId(),
                new CreateNotificationDTO()
                        .setType(NotificationType.RIDE)
                        .setRead(false)
                        .setMessage(notification)
        );
        log.info("Notification created with message: {}", notification);

        log.info("User {} has successfully left ride with ID: {}", user.getUsername(), id);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<ShortUserDTOList> getAllParticipantsRideById(UUID id, @Nullable FilterUserDTO filterUserDTO) {
        var user = userService.getAuthenticatedUserEntity();
        log.info("User {} is attempting to read all participants of ride with ID: {}", user.getUsername(), id);
        permissionService.checkUserPermission(user, List.of(Role.USER, Role.ADMIN));

        if (filterUserDTO == null)
            filterUserDTO = new FilterUserDTO();
        userService.validateFilter(filterUserDTO);

        var ride = rideRepository.findById(id).orElseThrow(RideNotFoundException::new);
        var participants = userRideRepository.findAllByRide_id(id)
                .stream()
                .map(UserRideEntity::getUser)
                .map(UserUtils::convertUserEntityToShortUserDTO)
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(
          new ShortUserDTOList(
                  participants,
                  PageRequest.of(filterUserDTO.getPage(), filterUserDTO.getSize()),
                  userRideRepository.countByRide_Id(ride.getId())
          )
        );
    }

    private void calculateAvailableSeats(RideEntity ride) {
        ride.setAvailableSeats(
                ride.getAvailableSeats() -
                        Long.valueOf(userRideRepository.countByRide_Id(ride.getId())).intValue()
        );
    }

    public ResponseEntity<Void> removeParticipantOfRideById(UUID rideid, UUID userid) {
        var user = userService.getAuthenticatedUserEntity();
        log.info("User {} is attempting to remove participant of ride with ID: {}", user.getUsername(), userid);
        permissionService.checkUserPermission(user, List.of(Role.USER, Role.ADMIN));

        var ride = rideRepository.findById(rideid).orElseThrow(RideNotFoundException::new);

        if (!ride.getDriver().getId().equals(user.getId()))
            throw new UserNotAllowedException("User is not maintainer of ride");

        if (!userRideRepository.existsByUser_IdAndRide_Id(userid, rideid))
            throw new RideNotFoundException();

        var result = userRepository.findById(userid).orElseThrow(UserNotFoundException::new);
        userRideRepository.deleteByUserAndRide(result, ride);

        var notification =
                "Du wurdest aus der Fahrt von Benutzer " + user.getUsername() + " entfernt.";
        notificationService.createNotification(
                userid,
                new CreateNotificationDTO()
                        .setType(NotificationType.RIDE)
                        .setRead(false)
                        .setMessage(notification)
        );
        log.info("Notification created with message: {}", notification);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
