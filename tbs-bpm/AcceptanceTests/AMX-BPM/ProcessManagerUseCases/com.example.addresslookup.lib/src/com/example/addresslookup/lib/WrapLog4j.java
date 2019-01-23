package com.example.addresslookup.lib;

import java.util.logging.Logger;

import org.eclipse.emf.ecore.util.EcoreUtil;

public class WrapLog4j {
	private static final Logger LOG = Logger.getLogger("WrapLog4j");
	public static final void info(String message) {
		String generateUUID = EcoreUtil.generateUUID();
		LOG.info("Generted UUID"+generateUUID);
		LOG.info(message);
	}

}
