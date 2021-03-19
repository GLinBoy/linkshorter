package com.glinboy.linkshorter.integration.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import com.glinboy.linkshorter.controller.HomeController;
import com.glinboy.linkshorter.entity.Url;
import com.glinboy.linkshorter.repository.UrlRepository;
import com.glinboy.linkshorter.util.TestUtils;

@ActiveProfiles("test")
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class HomeControllerIntegrationTest {
	
	@LocalServerPort
    private Integer port;
 
    @Autowired
    HomeController homeController;
    
    @Autowired
    UrlRepository repository;
    
    RestTemplate restTemplate = new RestTemplate();
    
    private final String defaultCode = "d726d2";
 	private final String defaultUrl = "https://glinboy.com";
    
    @Test
	void contextLoads() {
    	assertThat(homeController).isNotNull();
	}

	@Test
	void testGetHome() {
		ResponseEntity<String> response = restTemplate.exchange(TestUtils.createURLWithPort(port, "/"), HttpMethod.GET, null, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isEqualTo("Home...");
	}

	@Test
	void testGetUrl() {
    	Url saved = repository.save(Url.builder().code(defaultCode).original(defaultUrl).build());
    	assertThat(saved.getId()).isPositive();
		ResponseEntity<String> response = restTemplate.exchange(TestUtils.createURLWithPort(port, "/".concat(defaultCode)),
				HttpMethod.GET, null, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
		assertThat(response.getHeaders().get(HttpHeaders.LOCATION)).contains("https://glinboy.com");
	}

}
