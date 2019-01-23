package com.tibco.bx.debug.core.ws.util;

import java.util.Enumeration;
import java.util.Hashtable;

import org.apache.axis.Constants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.tibco.bx.debug.core.util.URLUtils;


public class SoapHelper {
	
	 private static Hashtable defaultSoapEnvelopeNamespaces_ = null;
	 
	 private static final void initDefaultSoapEnvelopeNamespaces()
	  {
	    defaultSoapEnvelopeNamespaces_ = new Hashtable();
	    defaultSoapEnvelopeNamespaces_.put(Constants.URI_SOAP11_ENV,Constants.NS_PREFIX_SOAP_ENV);
	    defaultSoapEnvelopeNamespaces_.put(Constants.URI_2001_SCHEMA_XSI,Constants.NS_PREFIX_SCHEMA_XSI);
	    defaultSoapEnvelopeNamespaces_.put(Constants.URI_2001_SCHEMA_XSD,Constants.NS_PREFIX_SCHEMA_XSD);
	  }
	  
	  public static final void addDefaultSoapEnvelopeNamespaces(Hashtable soapEnvelopeNamespaces)
	  {
	    if (defaultSoapEnvelopeNamespaces_ == null)
	      initDefaultSoapEnvelopeNamespaces();
	    Enumeration defaultSoapEnvelopeNamespaceURIs = defaultSoapEnvelopeNamespaces_.keys();
	    while (defaultSoapEnvelopeNamespaceURIs.hasMoreElements())
	    {
	      String defaultSoapEnvelopeNamespaceURI = (String)defaultSoapEnvelopeNamespaceURIs.nextElement();
	      soapEnvelopeNamespaces.put(defaultSoapEnvelopeNamespaceURI,(String)defaultSoapEnvelopeNamespaces_.get(defaultSoapEnvelopeNamespaceURI));
	    }
	  }
	  
	  public static final boolean isDefaultSoapEnvelopeNamespace(String namespaceURI,String namespacePrefix)
	  {
	    if (defaultSoapEnvelopeNamespaces_ == null)
	      initDefaultSoapEnvelopeNamespaces();
	    if (defaultSoapEnvelopeNamespaces_.get(namespaceURI) != null)
	      return true;
	    return false;
	  }  
	  
	  public static final Element createSoapEnvelopeElement(Document doc,Hashtable soapEnvelopeNamespaceTable)
	  {
		Element soapEnvelopeElement = doc.createElementNS(Constants.URI_SOAP11_ENV, Constants.NS_PREFIX_SOAP_ENV + ":Envelope");//$NON-NLS-1$
	    Enumeration e = soapEnvelopeNamespaceTable.keys();
	    while (e.hasMoreElements())
	    {
	      String soapEnvelopeNamespaceURI = (String)e.nextElement();
	      StringBuffer soapEnvelopeNamespaceAttr = new StringBuffer("xmlns:");//$NON-NLS-1$
	      soapEnvelopeNamespaceAttr.append((String)soapEnvelopeNamespaceTable.get(soapEnvelopeNamespaceURI));
	      soapEnvelopeElement.setAttribute(soapEnvelopeNamespaceAttr.toString(),soapEnvelopeNamespaceURI);      
	    }    
	    return soapEnvelopeElement;
	  }
	  
	  public static final Element createSoapHeaderElement(Document doc)
	  {
	    return doc.createElement("soapenv:Header");//$NON-NLS-1$
	  }
	  
	  public static final Element createSoapBodyElement(Document doc)
	  {
	    return doc.createElement("soapenv:Body");//$NON-NLS-1$
	  }
	  
	  public static final Element createRPCWrapperElement(Document doc,Hashtable soapEnvelopeNamespaceTable,String encodingNamespaceURI,String operationName, String encodingStyle)
	  {
	    int nsId = 0;
	    StringBuffer wrapperElementName = new StringBuffer();
	    String encodingNamespacePrefix = (String)soapEnvelopeNamespaceTable.get(encodingNamespaceURI);
	    if (encodingNamespacePrefix != null)
	      wrapperElementName.append(encodingNamespacePrefix);
	    else
	    {
	      // Loop until we generate a unique prefix.
	      do
	      {
	        wrapperElementName.setLength(0);
	        wrapperElementName.append("ns").append(nsId);//$NON-NLS-1$
	        if (!soapEnvelopeNamespaceTable.containsValue(wrapperElementName.toString()))
	          break;
	        nsId++;
	      } while (true);
	      
	      // need to ensure whatever prefix we choose is added to the namespace table
	      // so that it will not be reused lated on
	      soapEnvelopeNamespaceTable.put(encodingNamespaceURI, wrapperElementName.toString());
	    }    
	    String wrapperElementNamePrefix = wrapperElementName.toString();
	    wrapperElementName.append(':').append(operationName);
	    Element wrapperElement = doc.createElement(wrapperElementName.toString());
	    StringBuffer namespaceAttrName = new StringBuffer("xmlns:");//$NON-NLS-1$
	    namespaceAttrName.append(wrapperElementNamePrefix);
	    wrapperElement.setAttribute(namespaceAttrName.toString(),encodingNamespaceURI);
	    if (encodingStyle != null)
	      wrapperElement.setAttribute("soapenv:encodingStyle",encodingStyle);//$NON-NLS-1$
	    return wrapperElement;
	  }

	  public static final String encodeNamespaceDeclaration(String prefix, String uri)
	  {
	    StringBuffer sb = new StringBuffer();
	    sb.append(prefix);
	    sb.append(" "); //$NON-NLS-1$
	    sb.append(uri);
	    String result = URLUtils.encode(sb.toString());
	    return result;
	  }

	  public static final String[] decodeNamespaceDeclaration(String s)
	  {
	    String sCopy = URLUtils.decode(s);
	    int index = sCopy.indexOf(" ");//$NON-NLS-1$
	    String[] nsDecl = new String[2];
	    if (index != -1)
	    {
	      nsDecl[0] = sCopy.substring(0, index);
	      nsDecl[1] = sCopy.substring(index+1, sCopy.length());
	    }
	    else
	    {
	      nsDecl[0] = null;
	      nsDecl[1] = sCopy;
	    }
	    return nsDecl;
	  }
}
