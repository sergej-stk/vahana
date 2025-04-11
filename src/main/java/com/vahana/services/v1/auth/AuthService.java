package com.vahana.services.v1.auth;
import com.vahana.dtos.v1.auth.LoginResponseDto;
import com.vahana.dtos.v1.auth.PasswordResetConfirmDTO;
import com.vahana.dtos.v1.auth.PasswordResetRequestDTO;
import com.vahana.dtos.v1.general.ErrorResponseDTO;
import com.vahana.dtos.v1.notifications.CreateNotificationDTO;
import com.vahana.dtos.v1.users.RegisterUserDTO;
import com.vahana.dtos.v1.users.LoginUserDTO;
import com.vahana.dtos.v1.users.UserDTO;
import com.vahana.entities.v1.addresses.AddressEntity;
import com.vahana.entities.v1.pictures.PictureEntity;
import com.vahana.entities.v1.users.Role;
import com.vahana.entities.v1.users.UserEntity;
import com.vahana.repositories.v1.users.UserRepository;
import com.vahana.security.exceptions.TermsNotAgreedException;
import com.vahana.security.exceptions.UsernameOrEMailExistsException;
import com.vahana.services.v1.emails.EmailService;
import com.vahana.services.v1.emails.EmailTemplatesType;
import com.vahana.services.v1.notifications.NotificationService;
import com.vahana.services.v1.users.UserAuditService;
import com.vahana.services.v1.users.UserService;
import com.vahana.utils.v1.auth.AuthType;
import com.vahana.utils.v1.auth.AuthUtils;
import com.vahana.utils.v1.notifications.NotificationType;
import com.vahana.utils.v1.users.ActionType;
import com.vahana.utils.v1.users.UserUtils;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.time.OffsetDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public final class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;
    private final UserAuditService userAuditService;
    private final EmailService emailService;
    private final NotificationService notificationService;

    public ResponseEntity<UserDTO> register(RegisterUserDTO registerUserDTO) {
        log.info("Registering new user: {}", registerUserDTO.getEmail());
        if(registerUserDTO.getAgreedToTerms().equals(false)) {
            log.warn("User did not agree to terms: {}", registerUserDTO.getEmail());
            throw new TermsNotAgreedException();
        }

        if(userRepository.findByEmail(registerUserDTO.getEmail()).isPresent() || userRepository.findByUsername(registerUserDTO.getUsername()).isPresent()) {
            log.warn("Username or Email already exists: {}", registerUserDTO.getEmail());
            throw new UsernameOrEMailExistsException();
        }

        var picture = new PictureEntity();
        Resource image = new ClassPathResource("static/default.jpg");
        try (InputStream inputStream = image.getInputStream()) {
            picture.setPicture(IOUtils.toByteArray(inputStream));
        } catch (IOException e) {
            log.error("Error loading default picture: {}", e.getMessage());
        }

        var address = new AddressEntity();
        var user = userRepository.save(new UserEntity()
                .setUsername(registerUserDTO.getUsername())
                .setEmail(registerUserDTO.getEmail())
                .setFirstname(registerUserDTO.getFirstname())
                .setLastname(registerUserDTO.getLastname())
                .setPassword(passwordEncoder.encode(registerUserDTO.getPassword()))
                .setRole(Role.USER)
                .setPicture(picture)
                .setAgreedToTerms(Instant.now())
                .setAddress(address));

        log.info("User registered successfully: {}", user.getEmail());
        userAuditService.logUserAction(user, ActionType.CREATE, "{}");

        var notification =
                """
                        Willkommen bei Vahana!\s
                        Schön, dass du da bist! Bevor du loslegst, nimm dir einen Moment Zeit, um dein Profil auszufüllen – so wissen andere, mit wem sie unterwegs sind.
                        Auf Vahana findest du:
                        
                        Fahrten – Erstelle eigene Fahrten oder schließe dich bestehenden an
                        
                        Nachrichten – Hier siehst du Updates zu deinen Aktivitäten, z.B.. wenn jemand deiner Fahrt beitritt
                        
                        Profil – Zeige, wer du bist, und halte deine Infos aktuell
                        Viel Spaß beim Entdecken und Mitfahren!""";
        notificationService.createNotification(
                user.getId(),
                new CreateNotificationDTO()
                        .setType(NotificationType.RIDE)
                        .setRead(false)
                        .setMessage(notification)
        );
        log.info("Notification created with message: {}", notification);

        return ResponseEntity.status(HttpStatus.CREATED).body(UserUtils.convertUserEntityToUserDto(user));
    }

    public ResponseEntity<LoginResponseDto> login(LoginUserDTO loginUserDTO) {
        final ResponseStatusException EXCEPTION = new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email or password.");
        log.info("User attempting login: {}", loginUserDTO.getEmail());
        //user not found
        var user = userRepository.findByEmail(loginUserDTO.getEmail())
                .orElseThrow(() -> {
                    log.warn("Login failed - User not found: {}", loginUserDTO.getEmail());
                    return EXCEPTION;
                });

        //password incorrect
        if (!passwordEncoder.matches(loginUserDTO.getPassword(), user.getPassword())) {
            log.warn("Login failed - Incorrect password: {}", loginUserDTO.getEmail());
            throw EXCEPTION;
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUserDTO.getEmail(),
                        loginUserDTO.getPassword()
                )
        );

        String token = jwtService.generateToken(AuthUtils.getJWTClaim(AuthType.ACCESS), user);
        log.info("User logged in successfully: {}", loginUserDTO.getEmail());
        userAuditService.logUserAction(user, ActionType.LOGIN, "{}");
        return ResponseEntity.status(HttpStatus.CREATED).body(new LoginResponseDto()
                .setToken(token)
                .setExpiresIn(jwtService.getExpirationTime())
        );
    }

    public ResponseEntity<ErrorResponseDTO> requestPasswordReset(PasswordResetRequestDTO passwordResetRequestDTO) {
        log.info("Password reset requested for email: {}", passwordResetRequestDTO.getEmail());
        var user = userRepository.findByEmail(passwordResetRequestDTO.getEmail())
                .orElse(null);

        if(user != null)
        {
            emailService.sendEmail(
                    EmailTemplatesType.RESET_PASSWORD,
                    user,
                    user.getEmail()
            );
            log.info("Password reset email sent to: {}", user.getEmail());
        } else {
            log.warn("Password reset requested for non-existent email: {}", passwordResetRequestDTO.getEmail());
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ErrorResponseDTO()
                        .setStatus(HttpStatus.CREATED.value())
                        .setTimestamp(OffsetDateTime.now())
                        .setMessage("If an account with this email exists, a password reset link has been sent."));
    }

    public ResponseEntity<UserDTO> confirmPasswordReset(PasswordResetConfirmDTO resetConfirmDTO) {
        final ResponseStatusException EXCEPTION = new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token invalid");
        try {
            if (!(jwtService.extractTokenType(resetConfirmDTO.getToken()) == AuthType.PASSWORD_RESET))
                throw EXCEPTION;

            var user = userRepository.findByEmail(jwtService.extractUsername(resetConfirmDTO.getToken()))
                    .orElseThrow(() -> EXCEPTION);

            if (passwordEncoder.matches(resetConfirmDTO.getPassword(), user.getPassword()))
                throw new ResponseStatusException(HttpStatus.CONFLICT, "New password can not be the same.");

            user.setPassword(passwordEncoder.encode(resetConfirmDTO.getPassword()));

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(UserUtils.convertUserEntityToUserDto(userRepository.save(user)));

        } catch (JwtException jwtException) {
            throw EXCEPTION;
        }
    }

    public ResponseEntity<Void> logout() {
        var user = userService.getAuthenticatedUserEntity();
        log.info("User logged out: {}", user.getEmail());
        userAuditService.logUserAction(user, ActionType.LOGOUT, "{}");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}