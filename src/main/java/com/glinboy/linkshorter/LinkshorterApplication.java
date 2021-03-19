package com.glinboy.linkshorter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.glinboy.linkshorter.util.ApplicationProperties;

@SpringBootApplication
@EnableJpaAuditing
@EnableConfigurationProperties({ ApplicationProperties.class })
public class LinkshorterApplication {

	public static void main(String[] args) {
		SpringApplication.run(LinkshorterApplication.class, args);
	}

}
