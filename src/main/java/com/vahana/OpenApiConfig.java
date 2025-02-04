package com.vahana;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("Vahana")
						.version("1")
						.description("A ride-sharing API for connecting drivers and passengers. ")
						.termsOfService("https://github.com/sergej-stk/vahana")
						.contact(new Contact()
								.name("Support")
								.url("https://github.com/sergej-stk/vahana/issues")));
	}
}
