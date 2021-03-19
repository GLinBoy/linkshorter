package com.glinboy.linkshorter.controller;

import java.net.URI;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.glinboy.linkshorter.service.ShorterServiceApi;
import com.glinboy.linkshorter.service.dto.UrlDTO;

@RestController
@RequestMapping("/api/shorter")
public class ShorterController {
	
	private final ShorterServiceApi shorterService;
	
	public ShorterController(ShorterServiceApi shorterService) {
		this.shorterService = shorterService;
	}

	@PostMapping
	public ResponseEntity<UrlDTO> getShortUrl(@RequestBody UrlDTO urlDTO) {
		UrlDTO shorted = shorterService.makeShort(urlDTO);
		URI location = URI.create(String.format("/%s", shorted.getCode()));
		return ResponseEntity.created(location).contentType(MediaType.APPLICATION_JSON).body(shorted);
	}

}
