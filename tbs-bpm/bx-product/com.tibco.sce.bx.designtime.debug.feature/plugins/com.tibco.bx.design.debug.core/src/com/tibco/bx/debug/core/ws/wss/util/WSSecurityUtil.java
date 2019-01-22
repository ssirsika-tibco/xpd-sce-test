package com.tibco.bx.debug.core.ws.wss.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;


import org.apache.axis.soap.SOAP11Constants;
import org.apache.axis.soap.SOAP12Constants;
import org.apache.axis.soap.SOAPConstants;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class WSSecurityUtil {
    
    private static SecureRandom random = null;
    private static String randomAlgorithm = null;
    
    public static SecureRandom 
    resolveSecureRandom() throws NoSuchAlgorithmException {
        return resolveSecureRandom("SHA1PRNG");//$NON-NLS-1$
    }
    
    public synchronized static SecureRandom
    resolveSecureRandom( final String algorithm ) throws NoSuchAlgorithmException {
        if (random == null || !algorithm.equals(randomAlgorithm)) {
            random = SecureRandom.getInstance(algorithm);
            randomAlgorithm = algorithm;
            random.setSeed(System.currentTimeMillis());
        }
        return random;
    }
    
    public static String setNamespace(Element element, String namespace, String prefix) {
        String pre = getPrefixNS(namespace, element);
        if (pre != null) {
            return pre;
        }
        element.setAttributeNS(WSConstants.XMLNS_NS, "xmlns:" + prefix, namespace);//$NON-NLS-1$
        return prefix;
    }
    
    public static String getPrefixNS(String uri, Node e) {
        while (e != null && (e.getNodeType() == Element.ELEMENT_NODE)) {
            NamedNodeMap attrs = e.getAttributes();
            for (int n = 0; n < attrs.getLength(); n++) {
                Attr a = (Attr) attrs.item(n);
                String name = a.getName();
                if (name.startsWith("xmlns:") && a.getNodeValue().equals(uri)) {//$NON-NLS-1$
                    return name.substring(6);
                }
            }
            e = e.getParentNode();
        }
        return null;
    }
    
    public static SOAPConstants getSOAPConstants(Element startElement) {
        Document doc = startElement.getOwnerDocument();
        String ns = doc.getDocumentElement().getNamespaceURI();
        if (WSConstants.URI_SOAP12_ENV.equals(ns)) {
            return new SOAP12Constants();
        }
        return new SOAP11Constants();
    }
}
