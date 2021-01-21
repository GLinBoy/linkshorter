package com.glibnoy.linkshorter.unit.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import com.glibnoy.linkshorter.entity.Url;
import com.glibnoy.linkshorter.repository.UrlRepository;

@DataJpaTest
@DBRider
@DataSet(cleanBefore = true, cleanAfter = true)
@ActiveProfiles("test")
class UrlRepositoryTest {
	
	@Autowired
	private UrlRepository urlRepository;
	
	@Test
	void contextLoads() throws Exception {
		assertThat(urlRepository).isNotNull();
	}
	
	@Test
	void testInsertNewCode() {
		Url u = new Url();
		u.setCode("12345678");
		u.setOriginal("https://glinboy.com");
		u = this.urlRepository.save(u);
		assertThat(u.getId()).isNotNull();
	}

	@Test
	@DataSet(value = "dataset/urls.yml")
	void testFindOneByCode() {
		String code = "123456";
		Optional<Url> urlOpt = urlRepository.findOneByCode(code);
		assertThat(urlOpt).isPresent();
		assertThat(urlOpt.get().getId()).isNotNull();
		assertThat(urlOpt.get().getCode()).isEqualTo(code);
		
	}

}
