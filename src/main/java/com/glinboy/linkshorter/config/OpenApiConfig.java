package com.glinboy.linkshorter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.glinboy.linkshorter.util.ApplicationProperties;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {
	private final ApplicationProperties properties;

	public OpenApiConfig(ApplicationProperties applicationProperties) {
		this.properties = applicationProperties;
	}
	@Bean
	OpenAPI springShopOpenAPI() {
		return new OpenAPI()
				.info(new Info().title(properties.getName())
						.description(properties.getDescription())
						.version(properties.getVersion())
						.license(new License().name(properties.getLicense())
								.url(properties.getLicenseUrl())))
				.externalDocs(new ExternalDocumentation()
						.description("Source code")
						.url(properties.getGithubUrl()));
	}
}
