package com.glibnoy.linkshorter.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.glibnoy.linkshorter.service.ShorterServiceApi;
import com.glibnoy.linkshorter.service.dto.UrlDTO;

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
		return ResponseEntity.ok().body(shorted);
	}

}
