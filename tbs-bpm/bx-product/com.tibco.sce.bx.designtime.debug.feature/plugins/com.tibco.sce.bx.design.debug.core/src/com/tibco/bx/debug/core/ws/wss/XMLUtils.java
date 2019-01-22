package com.tibco.bx.debug.core.ws.wss;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

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

}
