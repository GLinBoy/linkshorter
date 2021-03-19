package com.glinboy.linkshorter.util;

public final class TestUtils {
	
	private TestUtils() {}

	public static final String createURLWithPort(Integer port, String uri) {
		return "http://localhost:".concat(String.valueOf(port)).concat(uri);
	}

}
