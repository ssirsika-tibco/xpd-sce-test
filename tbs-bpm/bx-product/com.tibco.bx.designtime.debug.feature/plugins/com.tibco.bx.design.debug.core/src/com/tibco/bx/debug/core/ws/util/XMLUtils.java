package com.tibco.bx.debug.core.ws.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.tibco.bx.debug.core.DebugCoreActivator;

public class XMLUtils {

	public static Document createNewDocument(DocumentBuilder docBuilder) throws ParserConfigurationException {
		if (docBuilder == null) {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(false);
			factory.setValidating(false);
			docBuilder = factory.newDocumentBuilder();
		}
		Document doc = docBuilder.newDocument();
		return doc;
	}

	public static Document createNewDocument() throws ParserConfigurationException {
		return createNewDocument(null);
	}

	public static Element removeAllChild(Element parent) {
		NodeList children = parent.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			parent.removeChild(children.item(i));
		}
		return parent;
	}

	public static final String UTF8_ENCODING = "UTF-8";//$NON-NLS-1$
	private static final String SOAP_ADDR_TAG = "soap:address"; //$NON-NLS-1$
	private static final String LOC_TAG = "location"; //$NON-NLS-1$

	public static final String serialize(String content, boolean omitXMLDeclaration) {

		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(content));
			Document doc = db.parse(is);
			return serialize(doc.getDocumentElement(), omitXMLDeclaration);
		} catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		} catch (SAXException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static final String serialize(Element e, boolean omitXMLDeclaration) {
		if (e != null) {
			try {
				DOMSource domSource = new DOMSource(e);
				Transformer serializer = TransformerFactory.newInstance().newTransformer();
				serializer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, ((omitXMLDeclaration) ? "yes" : "no"));//$NON-NLS-1$ //$NON-NLS-2$
				serializer.setOutputProperty(OutputKeys.INDENT, "yes");//$NON-NLS-1$
				serializer.setOutputProperty(OutputKeys.ENCODING, UTF8_ENCODING);

				serializer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");//$NON-NLS-1$ //$NON-NLS-2$
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				serializer.transform(domSource, new StreamResult(baos));
				baos.close();
				return new String(baos.toByteArray(), UTF8_ENCODING);
			} catch (Throwable t) {
			}
		}
		return null;
	}

	public static Element byteArrayToElement(byte[] b, boolean namespaceAware) throws ParserConfigurationException, SAXException,
			UnsupportedEncodingException, IOException {
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		docBuilderFactory.setNamespaceAware(namespaceAware);
		docBuilderFactory.setValidating(false);
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(new ByteArrayInputStream(b));
		return doc.getDocumentElement();
	}

	private static Document documentParseBuilder(String resp) {
		Document doc = null;
		if (resp == null || "".equals(resp)) { //$NON-NLS-1$
			return doc;
		}
		try {
			DocumentBuilderFactory parseFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder parseBuilder = parseFactory.newDocumentBuilder();
			InputStream inputStream = new java.io.ByteArrayInputStream(resp.getBytes(UTF8_ENCODING));
			InputSource input = new InputSource();
			input.setByteStream(inputStream);
			input.setEncoding(UTF8_ENCODING);
			doc = parseBuilder.parse(input);
		} catch (ParserConfigurationException pce) {
			DebugCoreActivator.log(pce);
		} catch (IOException ioe) {
			DebugCoreActivator.log(ioe);
		} catch (SAXException se) {
			DebugCoreActivator.log(se);
		} catch (NullPointerException ne) {
			DebugCoreActivator.log(ne);
		}
		return doc;
	}

	private static String getContentsFromURL(String wsdlUrl) {
		StringBuffer sb = new StringBuffer();
		try {
			URL url = new URL(wsdlUrl);
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				sb.append(inputLine).append("\n"); //$NON-NLS-1$
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	private static String getServiceLocation(Document doc) {
		String loc = null;
		if (doc != null) {
			NodeList nodeList = doc.getElementsByTagName(SOAP_ADDR_TAG); 
			for (int i = 0; i < nodeList.getLength(); i++) {
				if (nodeList.item(i).getAttributes().getNamedItem(LOC_TAG) != null) {
					loc = nodeList.item(i).getAttributes().getNamedItem(LOC_TAG).getNodeValue();
				}
			}

		}
		return loc;
	}

	public static String getWSLocation(String url) {
		String wsLoc = null;
		try {
			String content = getContentsFromURL(url);
			Document doc = documentParseBuilder(content);
			String loc = getServiceLocation(doc);
			URL wsURL = new URL(url);
			String host = wsURL.getHost();
			URL newURL = new URL(loc);
			wsLoc = newURL.getProtocol() + "://" + host + ":" + newURL.getPort() + newURL.getPath() + "?wsdl"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return wsLoc;
	}

}
