package com.tibco.bx.debug.core.util;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;

public final class URLUtils {

	private URLUtils() {
	}

	public static boolean isURL(String url) {
		if (url == null) {
			return false;
		}

		String pattern = "https?\\:\\/\\/(www\\.)?[-\\w\\d]+(\\.\\w{0,3})*(:\\d+)?(\\/[\\w\\d]+)*.*"; //$NON-NLS-1$
		return url.matches(pattern);
	}

	public static final String UTF8 = "UTF-8"; //$NON-NLS-1$

	public static String encode(String s) {
		return encode(s, UTF8);
	}

	public static String encode(String s, String enc) {
		try {
			return URLEncoder.encode(s, enc);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("", e); //$NON-NLS-1$
		}
	}

	public static String decode(String s) {
		while(s.contains("%")){ //$NON-NLS-1$
			s = decode(s, UTF8);
		}
		return s;
	}

	public static String decode(String s, String enc) {
		try {
			return URLDecoder.decode(s, enc);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("", e); //$NON-NLS-1$
		}
	}

	public static String encodeURLString(String urlString) {
		try {
			URI uri = new URI(urlString);
			return uri.toASCIIString();
		} catch (Exception e) {
		}
		return urlString;
	}

}
