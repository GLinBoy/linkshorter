package com.glibnoy.linkshorter.unit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;

import com.glibnoy.linkshorter.entity.Url;
import com.glibnoy.linkshorter.repository.UrlRepository;
import com.glibnoy.linkshorter.service.UrlEncoderApi;
import com.glibnoy.linkshorter.service.dto.UrlDTO;
import com.glibnoy.linkshorter.service.impl.ShorterServiceImpl;
import com.glibnoy.linkshorter.util.ApplicationProperties;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ShorterServiceImplTest {
	
	@Mock
	private UrlRepository urlRepository;
	
	@Mock
	private UrlEncoderApi urlEncoder;
	
	@Mock
	private ApplicationProperties applicationProperties;
	
	@Mock
	private ApplicationProperties.Config config;

 	@InjectMocks
	private ShorterServiceImpl shorterService;
	
 	private final String defaultCode = "d726d2";
 	private final String defaultUrl = "https://glinboy.com";
 	private final Long defaultId = 1L;
 	private final Long savedId = 1000L;
 	
	@BeforeEach
    void setUp() {
		Url u = Url.builder().id(defaultId)
				.code(defaultCode)
				.original(defaultUrl)
				.createdAt(LocalDateTime.now())
				.updatedAt(LocalDateTime.now())
				.build();
		
		doReturn(Optional.of(u)).when(urlRepository).findOneByCode(defaultCode);
		doAnswer(i -> {
			Url url = (Url)i.getArguments()[0];
			url.setId(savedId);
			return url;
		}).when(urlRepository).save(Mockito.any(Url.class));
		
		try {
			doReturn("d726d215cbf555440579c900d2ad3230e0322beb").when(urlEncoder).encode(defaultUrl);
		} catch (NoSuchAlgorithmException e) {
			fail("Exception happened: ".concat(e.getMessage()));
		}
		
		doReturn(config).when(applicationProperties).getConfig();
		
		doReturn(6).when(config).getCodeSize();
		doReturn("SHA-1").when(config).getHashAlgorithm();
		
		this.shorterService = new ShorterServiceImpl(urlRepository, urlEncoder, new ModelMapper(), applicationProperties);
    }
	
	@Test
	void contextLoads() throws Exception {
		assertThat(shorterService).isNotNull();
	}

	@Test
	void testMakeShort() {
		UrlDTO dto = UrlDTO.builder().original(defaultUrl).build();
		UrlDTO shorted = shorterService.makeShort(dto);
		assertThat(shorted).isNotNull();
		assertThat(shorted.getId()).isEqualTo(savedId);
		assertThat(shorted.getCode()).isEqualTo(defaultCode);
	}

	@Test
	void testGetShort() {
		Optional<UrlDTO> shortOpt = shorterService.getShort(defaultCode);
		assertThat(shortOpt).isNotEmpty();
		assertThat(shortOpt.get().getCode()).isEqualTo(defaultCode);
		assertThat(shortOpt.get().getOriginal()).isEqualTo(defaultUrl);
	}

}
