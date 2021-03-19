package com.glinboy.linkshorter.service.impl;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.glinboy.linkshorter.entity.Url;
import com.glinboy.linkshorter.repository.UrlRepository;
import com.glinboy.linkshorter.service.ShorterServiceApi;
import com.glinboy.linkshorter.service.UrlEncoderApi;
import com.glinboy.linkshorter.service.dto.UrlDTO;
import com.glinboy.linkshorter.util.ApplicationProperties;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ShorterServiceImpl implements ShorterServiceApi {
	
	private final UrlRepository repository;
	
	private final ModelMapper mapper;
	
	private final UrlEncoderApi urlEncoder;
	
	private final ApplicationProperties applicationProperties;

	public ShorterServiceImpl(UrlRepository repository,
			UrlEncoderApi urlEncoder,
			ModelMapper mapper,
			ApplicationProperties applicationProperties) {
		this.repository = repository;
		this.mapper = mapper;
		this.urlEncoder = urlEncoder;
		this.applicationProperties = applicationProperties;
	}

	@Override
	public UrlDTO makeShort(UrlDTO urlDTO) {
		log.info("URL requested to become shoter: {}", urlDTO);
		Url url = mapper.map(encodeUrl(urlDTO), Url.class);
		url = repository.save(url);
		return mapper.map(url, UrlDTO.class);
	}

	@Override
	public Optional<UrlDTO> getShort(String code) {
		return repository.findOneByCode(code)
				.map(c -> mapper.map(c, UrlDTO.class));
	}
	
	private UrlDTO encodeUrl(UrlDTO url) {
		try {
			String encode = urlEncoder.encode(url.getOriginal());
			url.setCode(encode.substring(0, applicationProperties.getConfig().getCodeSize()));
			return url;
		} catch (NoSuchAlgorithmException e) {
			log.error(e);
			throw new ResponseStatusException(HttpStatus.FAILED_DEPENDENCY);
		}
	}

}
