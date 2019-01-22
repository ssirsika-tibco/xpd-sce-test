package com.tibco.www.bx._2010.debugging.debugger;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Random;
import java.util.Set;
import java.util.TimeZone;

import javax.xml.bind.DatatypeConverter;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

public class SOAPHeaderHandler implements SOAPHandler<SOAPMessageContext> {

	private static final String URI_PROCESS_MANAGER = "http://www.tibco.com/bx/2009/management/processManagerType"; //$NON-NLS-1$
	private static final String PREFIX_PROCESS_MANAGER = "proc"; //$NON-NLS-1$

	private static final String URI_WSS_EXT = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd"; //$NON-NLS-1$
	private static final String PREFIX_WSS_EXT = "wsse"; //$NON-NLS-1$

	private static final String URI_WSS_UTIL = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd"; //$NON-NLS-1$
	private static final String PREFIX_WSS_UTIL = "wsu"; //$NON-NLS-1$

	private static final String TYPE_WSS_PASS = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText"; //$NON-NLS-1$
	private static final String TYPE_WSS_B64 = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary"; //$NON-NLS-1$

	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US); //$NON-NLS-1$

	static {
		DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("UTC")); //$NON-NLS-1$
	}

	protected String username;
	protected String password;

	public SOAPHeaderHandler(String username, String password) {
		this.username = username;
		this.password = password;
	}

	@Override
	public void close(MessageContext context) {
	}

	@Override
	public boolean handleFault(SOAPMessageContext context) {
		return true;
	}

	@Override
	public boolean handleMessage(SOAPMessageContext context) {
		try {
			SOAPMessage soapMsg = context.getMessage();
			addCredentials(soapMsg, username, password);
			soapMsg.saveChanges();
		} catch (SOAPException e) {
			e.printStackTrace();
		}

		return true;
	}

	@Override
	public Set<QName> getHeaders() {
		return null;
	}

	private void addCredentials(SOAPMessage message, String username, String password) {
		try {
			SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
			SOAPHeader header = envelope.getHeader();
			if (header == null) {
				header = envelope.addHeader();
			} else {
				Iterator iter = header.getChildElements();

				while (iter.hasNext()) {
					SOAPElement elem = (SOAPElement) iter.next();

					if ("wsse:Security".equals(elem.getNodeName())) { //$NON-NLS-1$
						elem.detachNode();
					}
				}
			}

			SOAPFactory factory = SOAPFactory.newInstance();
			SOAPElement securityElem = factory.createElement("Security", PREFIX_WSS_EXT, URI_WSS_EXT); //$NON-NLS-1$
			securityElem.addAttribute(QName.valueOf("xmlns:" + PREFIX_WSS_UTIL), URI_WSS_UTIL); //$NON-NLS-1$

			final String soapPrefix = message.getSOAPHeader().getPrefix();
			securityElem.addAttribute(QName.valueOf(soapPrefix + ":mustUnderstand"), "1"); //$NON-NLS-1$ //$NON-NLS-2$

			SOAPElement tokenElem = factory.createElement("UsernameToken", PREFIX_WSS_EXT, URI_WSS_EXT); //$NON-NLS-1$
			tokenElem.addAttribute(QName.valueOf(PREFIX_WSS_UTIL + ":Id"), "UsernameToken-1136"); //$NON-NLS-1$ //$NON-NLS-2$

			SOAPElement userElem = factory.createElement("Username", PREFIX_WSS_EXT, URI_WSS_EXT); //$NON-NLS-1$
			userElem.addTextNode(username);

			SOAPElement pwdElem = factory.createElement("Password", PREFIX_WSS_EXT, URI_WSS_EXT); //$NON-NLS-1$
			pwdElem.addTextNode(password);
			pwdElem.addAttribute(QName.valueOf("Type"), TYPE_WSS_PASS); //$NON-NLS-1$

			SOAPElement nonceElem = factory.createElement("Nonce", PREFIX_WSS_EXT, URI_WSS_EXT); //$NON-NLS-1$
			nonceElem.addTextNode(genNonce());
			nonceElem.addAttribute(QName.valueOf("EncodingType"), TYPE_WSS_B64); //$NON-NLS-1$

			SOAPElement createdElem = factory.createElement("Created", PREFIX_WSS_UTIL, URI_WSS_UTIL); //$NON-NLS-1$
			createdElem.addTextNode(DATE_FORMAT.format(new Date()));

			tokenElem.addChildElement(userElem);
			tokenElem.addChildElement(pwdElem);
			tokenElem.addChildElement(nonceElem);
			tokenElem.addChildElement(createdElem);
			securityElem.addChildElement(tokenElem);

			header.addChildElement(securityElem);

			message.saveChanges();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String genNonce() {
		byte[] nonce = new byte[16];
		Random rand;
		try {
			rand = SecureRandom.getInstance("SHA1PRNG"); //$NON-NLS-1$
			rand.nextBytes(nonce);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "2uVUTURgvhm4MfciVr9mvA=="; //$NON-NLS-1$
		}

		return DatatypeConverter.printBase64Binary(nonce);
	}

}
