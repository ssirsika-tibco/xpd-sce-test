package com.tibco.bx.debug.core.ws.wss.header;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.axis.encoding.Base64;
import org.apache.axis.message.SOAPHeaderElement;
import org.apache.axis.soap.SOAP11Constants;
import org.apache.axis.soap.SOAPConstants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.tibco.bx.debug.core.ws.util.XMLUtils;
import com.tibco.bx.debug.core.ws.wss.util.WSConstants;
import com.tibco.bx.debug.core.ws.wss.util.WSSecurityUtil;
import com.tibco.bx.debug.core.ws.wss.util.XmlSchemaDateFormat;

public class WssHeaderBuilder {

	static int idIndex;

	private static SecureRandom random;
	private static SOAPConstants soapConstants;

	public static WssHeaderBuilder instances = new WssHeaderBuilder();

	private WssHeaderBuilder() {
		try {
			random = WSSecurityUtil.resolveSecureRandom();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	private SOAPConstants getSoapConstants() {
		if (soapConstants == null) {
			soapConstants = new SOAP11Constants();
		}
		return soapConstants;
	}

	public SOAPHeaderElement getWssHeaderForSoap(String userName, String password) throws ParserConfigurationException {
		SOAPHeaderElement headerElement = new SOAPHeaderElement(getWssHeader(userName, password));
		headerElement.setMustUnderstand(true);
		return headerElement;
	}

	public Element getWssHeader(String userName, String password) throws ParserConfigurationException {

		Document doc = XMLUtils.createNewDocument();

		Element wsseSecurity = doc.createElementNS(WSConstants.WSSE_NS, "wsse:Security"); //$NON-NLS-1$
		wsseSecurity.setAttributeNS(WSConstants.XMLNS_NS, "xmlns:wsse", WSConstants.WSSE_NS); //$NON-NLS-1$

		// wsseSecurity.setAttributeNS(
		// getSoapConstants().getEnvelopeURI(),
		//                "soapenv" + ":" + WSConstants.ATTR_MUST_UNDERSTAND, //$NON-NLS-1$ //$NON-NLS-2$
		//                "1"); //$NON-NLS-1$

		Element element = doc.createElementNS(WSConstants.WSSE_NS, "wsse:" + WSConstants.USERNAME_TOKEN_LN); //$NON-NLS-1$
		String prefix = WSSecurityUtil.setNamespace(element, WSConstants.WSU_NS, WSConstants.WSU_PREFIX);
		element.setAttributeNS(WSConstants.WSU_NS, prefix + ":Id", getNextId("UsernameToken")); //$NON-NLS-1$ //$NON-NLS-2$

		addUserName(doc, element, userName);
		addPassword(doc, element, password);
		addNonce(doc, element);
		addCreated(true, doc, element);

		wsseSecurity.appendChild(element);

		return wsseSecurity;
	}

	private String getNextId(String prex) {
		if (idIndex == Integer.MAX_VALUE) {
			idIndex = 0;
		}
		return prex + "-" + Integer.toString(++idIndex); //$NON-NLS-1$
	}

	private void addUserName(Document doc, Element element, String userName) {
		Element elementUsername = doc.createElementNS(WSConstants.WSSE_NS, "wsse:" + WSConstants.USERNAME_LN); //$NON-NLS-1$
		elementUsername.appendChild(doc.createTextNode(userName));
		element.appendChild(elementUsername);
	}

	private void addPassword(Document doc, Element element, String password) {
		Element elementPassword = doc.createElementNS(WSConstants.WSSE_NS, "wsse:" + WSConstants.PASSWORD_LN); //$NON-NLS-1$
		elementPassword.appendChild(doc.createTextNode(password));
		elementPassword.setAttributeNS(null, "Type", WSConstants.PASSWORD_TEXT); //$NON-NLS-1$
		element.appendChild(elementPassword);
	}

	public void addNonce(Document doc, Element element) {

		byte[] nonceValue = new byte[16];
		random.nextBytes(nonceValue);
		Element elementNonce = doc.createElementNS(WSConstants.WSSE_NS, "wsse:" + WSConstants.NONCE_LN); //$NON-NLS-1$
		elementNonce.appendChild(doc.createTextNode(Base64.encode(nonceValue)));
		elementNonce.setAttributeNS(null, "EncodingType", WSConstants.BASE64_ENCODING); //$NON-NLS-1$
		element.appendChild(elementNonce);
	}

	public void addCreated(boolean milliseconds, Document doc, Element element) {
		DateFormat df = null;
		if (milliseconds) {
			df = new XmlSchemaDateFormat();
		} else {
			df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"); //$NON-NLS-1$
			df.setTimeZone(TimeZone.getTimeZone("UTC")); //$NON-NLS-1$
		}
		Calendar rightNow = Calendar.getInstance();
		Element elementCreated = doc.createElementNS(WSConstants.WSU_NS, WSConstants.WSU_PREFIX + ":" + WSConstants.CREATED_LN); //$NON-NLS-1$
		WSSecurityUtil.setNamespace(element, WSConstants.WSU_NS, WSConstants.WSU_PREFIX);
		elementCreated.appendChild(doc.createTextNode(df.format(rightNow.getTime())));
		element.appendChild(elementCreated);
	}
}
