package com.glinboy.linkshorter.unit.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.glinboy.linkshorter.controller.ShorterController;
import com.glinboy.linkshorter.service.ShorterServiceApi;
import com.glinboy.linkshorter.service.dto.UrlDTO;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class ShorterControllerTest {

	@Mock
	ShorterServiceApi shorterService;
	
	@InjectMocks
	ShorterController shorterController;

 	private final Long defaultId = 1L;
	private final String defaultCode = "d726d2";
 	private final String defaultUrl = "https://glinboy.com";
	
	@Test
	void contextLoads() throws Exception {
		assertThat(shorterController).isNotNull();
	}
	
	@Test
	void testGetShortUrl() {
		UrlDTO original = UrlDTO.builder().original(defaultUrl).build();
		UrlDTO u = UrlDTO.builder().id(defaultId)
				.code(defaultCode)
				.original(defaultUrl)
				.createdAt(LocalDateTime.now())
				.updatedAt(LocalDateTime.now())
				.build();
		doReturn(u).when(shorterService).makeShort(original);
		
		ResponseEntity<UrlDTO> shortedUrlResponse = shorterController.getShortUrl(original);
		assertThat(shortedUrlResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(shortedUrlResponse.getHeaders().get(HttpHeaders.LOCATION)).endsWith("/".concat(defaultCode));
		assertThat(shortedUrlResponse.getHeaders().get(HttpHeaders.CONTENT_TYPE))
			.contains(MediaType.APPLICATION_JSON.toString());
		assertThat(shortedUrlResponse.getBody().getId()).isEqualTo(defaultId);
		assertThat(shortedUrlResponse.getBody().getCode()).isEqualTo(defaultCode);
		assertThat(shortedUrlResponse.getBody().getOriginal()).isEqualTo(defaultUrl);
	}
}
