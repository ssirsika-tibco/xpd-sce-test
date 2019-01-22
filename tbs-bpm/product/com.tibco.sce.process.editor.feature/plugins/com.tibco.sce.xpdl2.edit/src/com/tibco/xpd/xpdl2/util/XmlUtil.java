package com.tibco.xpd.xpdl2.util;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Useful method calls for xml type files
 * 
 * @author glewis
 * 
 */
public class XmlUtil {

    /**
     * replace certain xml escape charactors with suitable values so it can be
     * parsed correctly
     * 
     * @param s
     * @return
     */
    public static String escapeXmlCharacters(String s) {
        StringBuffer result = null;

        for (int i = 0, max = s.length(), delta = 0; i < max; i++) {
            char c = s.charAt(i);
            String replacement = null;

            if (c == '&') {
                replacement = "&amp;"; //$NON-NLS-1$
            } else if (c == '<') {
                replacement = "&lt;"; //$NON-NLS-1$
            } else if (c == '\r') {
                replacement = "&#13;"; //$NON-NLS-1$
            } else if (c == '>') {
                replacement = "&gt;"; //$NON-NLS-1$
            } else if (c == '"') {
                replacement = "&quot;"; //$NON-NLS-1$
            } else if (c == '\'') {
                replacement = "&apos;"; //$NON-NLS-1$
            }

            if (replacement != null) {
                if (result == null) {
                    result = new StringBuffer(s);
                }

                result.replace(i + delta, i + delta + 1, replacement);

                delta += (replacement.length() - 1);
            }
        }

        if (result == null) {
            return s;
        }

        return result.toString();

    }

    public static Document stringToDom(String xmlSource) throws SAXException,
            ParserConfigurationException, IOException {
        if (xmlSource != null) {
            DocumentBuilderFactory factory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(new InputSource(new StringReader(xmlSource)));
        }
        return null;
    }
}
