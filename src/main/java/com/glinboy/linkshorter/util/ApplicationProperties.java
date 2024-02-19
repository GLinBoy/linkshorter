package com.glinboy.linkshorter.util;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public final class ApplicationProperties {

	private String name;
	private String description;
	private String version;
	private String license;
	private String licenseUrl;
	private String githubUrl;
	
	private final Config config = new Config();
	
	@Getter
	@Setter
	public static final class Config {
		private int codeSize;
		private String hashAlgorithm;
	}
}
