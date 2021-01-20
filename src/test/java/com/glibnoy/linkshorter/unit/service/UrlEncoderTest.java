package com.glibnoy.linkshorter.unit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.glibnoy.linkshorter.service.UrlEncoderApi;

@SpringBootTest
@ActiveProfiles("test")
class UrlEncoderTest {

	@Autowired
	private UrlEncoderApi urlEncode;
	
	@Test
	void contextLoads() throws Exception {
		assertThat(urlEncode).isNotNull();
	}
	
	@Test
	void testEncode() {
		try {
			String encode = this.urlEncode.encode("https://glinboy.com");
			assertThat(encode).isEqualTo("d726d215cbf555440579c900d2ad3230e0322beb");
		} catch (NoSuchAlgorithmException e) {
			fail("Exception happened: ".concat(e.getMessage()));
		}
	}

}
