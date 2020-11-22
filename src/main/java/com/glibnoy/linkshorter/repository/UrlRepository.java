package com.glibnoy.linkshorter.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.glibnoy.linkshorter.entity.Url;

public interface UrlRepository extends JpaRepository<Url, Long> {
	Optional<Url> findOneByCode(String code);
}
