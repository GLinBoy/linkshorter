package com.glibnoy.linkshorter.unit.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;

import com.glibnoy.linkshorter.controller.HomeController;
import com.glibnoy.linkshorter.service.ShorterServiceApi;
import com.glibnoy.linkshorter.service.dto.UrlDTO;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class HomeControllerTest {
	
	@Mock
	ShorterServiceApi shorterService;
	
	@InjectMocks
	HomeController homeController;

 	private final Long defaultId = 1L;
	private final String defaultCode = "d726d2";
 	private final String defaultUrl = "https://glinboy.com";
	
	@Test
	void homeShouldReturnDefaultMessage() throws Exception {
		String home = homeController.getHome();
		assertThat(home).isEqualTo("Home...");
	}
	
	@Test
	void testGetOriginalUrl() throws Exception {
		UrlDTO u = UrlDTO.builder().id(defaultId)
				.code(defaultCode)
				.original(defaultUrl)
				.createdAt(LocalDateTime.now())
				.updatedAt(LocalDateTime.now())
				.build();
		doReturn(Optional.of(u)).when(shorterService).getShort(defaultCode);
		
		HttpServletResponse httpServletResponse = new MockHttpServletResponse();
		homeController.getUrl(defaultCode, httpServletResponse);
		assertThat(httpServletResponse.getStatus()).isEqualTo(HttpStatus.FOUND.value());
		assertThat(httpServletResponse.getHeader(HttpHeaders.LOCATION)).isEqualTo(defaultUrl);
	}
}
