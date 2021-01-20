package com.glibnoy.linkshorter.integration.controller;

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
import org.springframework.web.client.RestTemplate;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import com.glibnoy.linkshorter.controller.HomeController;
import com.glibnoy.linkshorter.util.TestUtils;

@DBRider
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class HomeControllerIntegrationTest {
	
	@LocalServerPort
    private Integer port;
 
    @Autowired
    HomeController homeController;
    
    RestTemplate restTemplate = new RestTemplate();
    
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
	@DataSet(value = "dataset/urls.yml", cleanBefore = true)
	void testGetUrl() {
		String code = "123456";
		ResponseEntity<String> response = restTemplate.exchange(TestUtils.createURLWithPort(port, "/".concat(code)), HttpMethod.GET, null, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
		assertThat(response.getHeaders().get(HttpHeaders.LOCATION)).contains("https://glinboy.com");
	}

}
