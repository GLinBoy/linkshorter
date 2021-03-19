package com.glinboy.linkshorter.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.glinboy.linkshorter.entity.Url;

public interface UrlRepository extends JpaRepository<Url, Long> {
	Optional<Url> findOneByCode(String code);
}
