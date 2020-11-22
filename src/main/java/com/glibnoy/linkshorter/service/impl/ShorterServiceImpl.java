package com.glibnoy.linkshorter.service.impl;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.glibnoy.linkshorter.entity.Url;
import com.glibnoy.linkshorter.repository.UrlRepository;
import com.glibnoy.linkshorter.service.ShorterServiceApi;
import com.glibnoy.linkshorter.service.UrlEncoderApi;
import com.glibnoy.linkshorter.service.dto.UrlDTO;
import com.glibnoy.linkshorter.service.mapper.UrlMapper;
import com.glibnoy.linkshorter.util.ApplicationProperties;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ShorterServiceImpl implements ShorterServiceApi {
	
	private final UrlRepository repository;
	
	private final UrlMapper mapper;
	
	private final UrlEncoderApi urlEncoder;
	
	private final ApplicationProperties applicationProperties;

	public ShorterServiceImpl(UrlRepository repository,
			UrlMapper mapper,
			UrlEncoderApi urlEncoder,
			ApplicationProperties applicationProperties) {
		this.repository = repository;
		this.mapper = mapper;
		this.urlEncoder = urlEncoder;
		this.applicationProperties = applicationProperties;
	}

	@Override
	public UrlDTO makeShort(UrlDTO urlDTO) {
		log.info("URL requested to become shoter: {}", urlDTO);
		Url url = mapper.toEntity(encodeUrl(urlDTO));
		url = repository.save(url);
		return mapper.toDto(url);
	}

	@Override
	public Optional<UrlDTO> getShort(String code) {
		return repository.findOneByCode(code)
				.map(mapper::toDto);
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
