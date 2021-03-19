package com.glinboy.linkshorter.service.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

import org.springframework.stereotype.Service;

import com.glinboy.linkshorter.service.UrlEncoderApi;
import com.glinboy.linkshorter.util.ApplicationProperties;

@Service
public class UrlEncoderImpl implements UrlEncoderApi {
	
	private final ApplicationProperties applicationProperties;

	public UrlEncoderImpl(ApplicationProperties applicationProperties) {
		this.applicationProperties = applicationProperties;
	}

	@Override
	public String encode(String url) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance(applicationProperties.getConfig().getHashAlgorithm());
		md.reset();
		md.update(url.getBytes());
		return byteToHex(md.digest());
	}

	private String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

}
