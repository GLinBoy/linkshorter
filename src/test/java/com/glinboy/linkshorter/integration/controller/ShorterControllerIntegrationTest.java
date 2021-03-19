package com.glinboy.linkshorter.integration.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import com.glinboy.linkshorter.controller.ShorterController;
import com.glinboy.linkshorter.service.dto.UrlDTO;
import com.glinboy.linkshorter.util.TestUtils;

@ActiveProfiles("test")
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ShorterControllerIntegrationTest {
	
	@LocalServerPort
    private int port;
 
    @Autowired
    ShorterController shorterController;
    
    RestTemplate restTemplate = new RestTemplate();
    
    private final String defaultCode = "d726d2";
 	private final String defaultUrl = "https://glinboy.com";

	@Test
	void testShorterController() {
		assertThat(shorterController).isNotNull();
	}

	@Test
	void testGetShortUrl() {
		HttpEntity<UrlDTO> httpEntity = new HttpEntity<>(UrlDTO.builder().original(defaultUrl).build());
		ResponseEntity<UrlDTO> responseEntity = restTemplate.exchange(TestUtils.createURLWithPort(port, "/api/shorter"),
				HttpMethod.POST, httpEntity, UrlDTO.class);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(responseEntity.getHeaders().get(HttpHeaders.LOCATION)).endsWith("/".concat(defaultCode));
		assertThat(responseEntity.getBody()).isNotNull();
		assertThat(responseEntity.getBody().getId()).isPositive();
		assertThat(responseEntity.getBody().getCode()).isEqualTo(defaultCode);
	}

}
