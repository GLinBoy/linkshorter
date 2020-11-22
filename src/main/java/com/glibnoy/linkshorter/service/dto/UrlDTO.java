package com.glibnoy.linkshorter.service.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UrlDTO {
	private Long id;
	private String code;
	private String original;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

}
