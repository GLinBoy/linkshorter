package com.glinboy.linkshorter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.glinboy.linkshorter.util.ApplicationProperties;

@SpringBootApplication
@EnableJpaAuditing
@EnableConfigurationProperties({ ApplicationProperties.class })
@EnableAutoConfiguration(exclude = ErrorMvcAutoConfiguration.class)
public class LinkshorterApplication {

	public static void main(String[] args) {
		SpringApplication.run(LinkshorterApplication.class, args);
	}

}
