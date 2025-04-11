package com.vahana.configurations;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
		info = @Info(
				title = "Vahana-API",
				summary = "A ride-sharing API for connecting drivers and passengers.",
				description = "A ride-sharing API for connecting drivers and passengers.",
				version = "1",
				termsOfService = "https://github.com/sergej-stk/vahana",
				contact = @Contact(
						name = "Contact",
						url = "https://github.com/sergej-stk/vahana"
		))
)
@SecurityScheme(
		name = "bearerAuth",
		type = SecuritySchemeType.HTTP,
		scheme = "bearer",
		bearerFormat = "JWT",
		in = SecuritySchemeIn.HEADER,
		description = "Bearer Authentication (JWT)",
		paramName = "Authorization"
)
public class OpenApiConfiguration { }
