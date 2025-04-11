package com.vahana.services.v1.emails;

import com.vahana.entities.v1.users.UserEntity;
import com.vahana.services.v1.auth.JwtService;
import com.vahana.utils.v1.auth.AuthType;
import com.vahana.utils.v1.auth.AuthUtils;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender sender;
    private final TemplateEngine engine;
    private final JwtService jwtService;

    @Async
    public void sendEmail(EmailTemplatesType type, UserEntity user, String... to) {
        try {
            var template = convertEmailTemplatesTypeToString(type);
            log.info("Sending email to: {} template {}", to, template);
            var subject = convertEmailTemplatesTypeToSubject(type);
            var message = sender.createMimeMessage();
            var helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(user.getEmail());
            helper.setFrom("thomasleckteier@gmail.com");
            helper.setSubject(subject);
            var context = defineContext(type, user);
            var content = engine.process(template, context);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            sender.send(message);
        } catch (MessagingException e) {
            log.error("Error on sending E-Mail occurred:{}", e.getMessage());
        }
    }

    private static String convertEmailTemplatesTypeToString(EmailTemplatesType type) {
        return switch (type) {
            case NONE -> throw new NullPointerException("EmailTemplatesType is not defined");
            case RESET_PASSWORD -> "password-reset-mail-template.html";
            case EMAIL_CHANGED -> "email-changed-mail-template.html";
        };
    }

    private static String convertEmailTemplatesTypeToSubject(EmailTemplatesType type) {
        return switch (type) {
            case NONE -> throw new NullPointerException("EmailTemplatesType is not defined");
            case RESET_PASSWORD -> "[Vahana] Password Reset";
            case EMAIL_CHANGED -> "[Vahana] Email Changed";
        };
    }

    private Context defineContext(EmailTemplatesType type, UserEntity user) {
        var context = new Context();
        context.setVariable("username", user.getUsername());
        context.setVariable("email", user.getEmail());
        context.setVariable("date", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        if (type == EmailTemplatesType.RESET_PASSWORD) {
            var token = jwtService.generateToken(AuthUtils.getJWTClaim(AuthType.PASSWORD_RESET), user, 900_000L);
            context.setVariable("token", token);
        }

        return context;
    }
}
