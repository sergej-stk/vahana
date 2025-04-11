package com.vahana.services.v1.pictures;

import com.vahana.entities.v1.users.Role;
import com.vahana.entities.v1.users.UserEntity;
import com.vahana.repositories.v1.pictures.PictureRepository;
import com.vahana.repositories.v1.users.UserRepository;
import com.vahana.security.exceptions.PictureNotFoundException;
import com.vahana.security.exceptions.UnsupportedMimeTypeException;
import com.vahana.security.exceptions.UserNotFoundException;
import com.vahana.security.exceptions.UserPermissionException;
import com.vahana.services.v1.auth.PermissionService;
import com.vahana.utils.v1.pictures.PictureConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public final class PictureService {
    private final UserRepository userRepository;
    private final PictureRepository pictureRepository;
    private final PermissionService permissionService;

    public ResponseEntity<byte[]> getPicture(UUID id) {
        log.info("Received request to fetch picture with ID: {}", id);
        var user = getAuthenticatedUserEntity();
        permissionService.checkUserPermission(user, List.of(Role.USER, Role.ADMIN));
        var picture = pictureRepository.findById(id).orElseThrow(PictureNotFoundException::new);
        log.info("Picture with ID {} found and being returned", id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(new Tika().detect(picture.getPicture())))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + picture.getId().toString() + "-profile\"")
                .body(picture.getPicture());
    }

    private UserEntity getAuthenticatedUserEntity() {
        log.info("Fetching current user");
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var email = ((UserEntity) authentication.getPrincipal()).getEmail();
        var user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        log.info("User fetched {}", user.getUsername());
        return user;
    }

    public ResponseEntity<Void> updatePicture(UUID id, MultipartFile file) {
        log.info("Received request to update picture with ID: {}", id);
        var user = getAuthenticatedUserEntity();
        permissionService.checkUserPermission(user, List.of(Role.USER, Role.ADMIN));
        var picture = pictureRepository.findById(id).orElseThrow(PictureNotFoundException::new);
        log.info("Validating user permissions for picture update");
        if (!user.getPicture().getId().equals(id) && (user.getRole() != Role.ADMIN))
            throw new UserPermissionException();

        byte[] newPicture;
        try {
            newPicture = file.getBytes();
        } catch (IOException e) {
            log.error("File not readable", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File not readable");
        }

        var mimeType = new Tika().detect(newPicture);
        log.info("Detected MIME type: {}", mimeType);
        if (!PictureConst.ALLOWED_MIME_TYPES.contains(mimeType))
            throw new UnsupportedMimeTypeException();

        picture.setPicture(newPicture);
        log.info("Updating picture with ID: {}", id);
        pictureRepository.save(picture);
        log.info("Picture with ID {} successfully updated", id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
