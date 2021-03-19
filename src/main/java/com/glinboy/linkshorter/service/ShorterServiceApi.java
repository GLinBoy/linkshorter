package com.glinboy.linkshorter.service;

import java.util.Optional;

import com.glinboy.linkshorter.service.dto.UrlDTO;

public interface ShorterServiceApi {
	
	UrlDTO makeShort(UrlDTO url);
	Optional<UrlDTO> getShort(String code);

}
