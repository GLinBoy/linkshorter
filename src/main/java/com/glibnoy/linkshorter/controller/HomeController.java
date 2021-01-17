package com.glibnoy.linkshorter.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import com.glibnoy.linkshorter.service.ShorterServiceApi;
import com.glibnoy.linkshorter.service.dto.UrlDTO;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HomeController {
	
	private final ShorterServiceApi shorterService;

	public HomeController(ShorterServiceApi shorterService) {
		this.shorterService = shorterService;
	}

	@ResponseBody
	@RequestMapping(value = {"/", "/index", "index.html"})
	public String getHome() {
		return "Home...";
	}
	
	@GetMapping("/{code}")
	public void getUrl(@PathVariable String code,
			HttpServletResponse httpServletResponse) {
		log.info("Reuqest to get URL with code: {}", code);
		UrlDTO s = shorterService.getShort(code)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		httpServletResponse.setHeader(HttpHeaders.LOCATION, s.getOriginal());
		httpServletResponse.setStatus(HttpStatus.FOUND.value());
	}
}
