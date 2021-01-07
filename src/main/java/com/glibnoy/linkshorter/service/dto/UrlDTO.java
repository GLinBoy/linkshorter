package com.glibnoy.linkshorter.service.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UrlDTO {
	private Long id;
	private String code;
	private String original;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

}
