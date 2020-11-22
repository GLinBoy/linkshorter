package com.glibnoy.linkshorter.service;

import java.util.Optional;

import com.glibnoy.linkshorter.service.dto.UrlDTO;

public interface ShorterServiceApi {
	
	UrlDTO makeShort(UrlDTO url);
	Optional<UrlDTO> getShort(String code);

}
