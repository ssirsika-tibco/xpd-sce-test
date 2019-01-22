package com.tibco.bx.debug.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import com.tibco.bx.debug.core.DebugCoreActivator;

public class NetUtil {

	/**
	 * Check the given url is available or not.
	 * @param locationURI the given url
	 * @return
	 */
	public static boolean validateURLViaNet(String locationURI) throws Exception {
		if (!URLUtils.isURL(locationURI)) {
			return false;
		}
		InputStream netFileInputStream = null;
		URLConnection urlConn = null;
		try {
			URL url = new URL(locationURI);
			urlConn = url.openConnection();
			if (urlConn instanceof HttpURLConnection) {
				if (urlConn instanceof HttpsURLConnection) {
					final String host = url.getHost();
					HttpsURLConnection httpsURLConn = (HttpsURLConnection) urlConn;
					httpsURLConn.setHostnameVerifier(new HostnameVerifier() {

						@Override
						public boolean verify(String hostname, SSLSession session) {
							return host.equals(hostname);
						}
						
					});

					int state = httpsURLConn.getResponseCode();
					if (state == HttpsURLConnection.HTTP_OK) {
						return true;
					}
					return false;
				}

				HttpURLConnection httpURLConn = (HttpURLConnection) urlConn;
				int state = httpURLConn.getResponseCode();
				if (state == HttpURLConnection.HTTP_OK) {
					return true;
				}
				return false;
			} else {
				urlConn.connect();
				netFileInputStream = urlConn.getInputStream();
				if (null != netFileInputStream) {
					try {
						netFileInputStream.close();
					} catch (IOException e) {
						DebugCoreActivator.log(e);
					}
					return true;
				} else {
					return false;
				}
			}
		} catch (Exception e) {
			DebugCoreActivator.log(e);
			throw e;
		}
	}

	public static String serializeList(List<String> list) throws IOException {
		Iterator<String> i = list.iterator();
		if (!i.hasNext())
			return "[]"; //$NON-NLS-1$
		StringBuilder sb = new StringBuilder();
		sb.append('[');
		for (;;) {
			Object obj = i.next();
			sb.append(URLUtils.encode((String) obj));
			if (!i.hasNext())
				return sb.append(']').toString();
			sb.append(", "); //$NON-NLS-1$
		}
	}

	public static List<String> deserializeList(String encodedString) throws IOException {
		List<String> list = new ArrayList<String>();
		if (encodedString != null) {
			String[] itemStrs = encodedString.substring(1, encodedString.length() - 1).split(", "); //$NON-NLS-1$
			for (int i = 0; i < itemStrs.length; i++) {
				String item = URLUtils.decode(itemStrs[i].trim());
				list.add(item);
			}
		}
		return list;
	}

}
