package com.glibnoy.linkshorter.service;

import java.security.NoSuchAlgorithmException;

public interface UrlEncoderApi {

	String encode(String url) throws NoSuchAlgorithmException;

}
