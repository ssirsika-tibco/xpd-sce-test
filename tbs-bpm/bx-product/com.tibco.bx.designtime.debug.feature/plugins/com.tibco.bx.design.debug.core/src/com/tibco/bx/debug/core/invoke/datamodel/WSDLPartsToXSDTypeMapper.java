package com.tibco.bx.debug.core.invoke.datamodel;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import javax.xml.namespace.QName;

import org.eclipse.wst.wsdl.Part;
import org.eclipse.xsd.XSDComponent;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDModelGroupDefinition;
import org.eclipse.xsd.XSDNamedComponent;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.XSDTypeDefinition;

public class WSDLPartsToXSDTypeMapper {
	private final char POUND = '#';
	  private Vector xsdSchemaList;
	  private Hashtable partToXSDCache;

	  public WSDLPartsToXSDTypeMapper() {
	    xsdSchemaList = new Vector();
	    partToXSDCache = new Hashtable();
	  }

	  public void addSchemas(Vector schemaList) {
	    for (int i=0;i<schemaList.size();i++) {
	      Object schema = schemaList.elementAt(i);
	      if (schema != null)
	        xsdSchemaList.addElement(schema);
	    }
	  }

	  public XSDNamedComponent getXSDType(Part part, String id) {
	    XSDNamedComponent component = getXSDTypeFromCache(id);
	    if (component != null)
	      return component;
	    component = getXSDTypeFromSchema(part);
	    if (component != null)
	      addToCache(id, component);
	    return component;
	  }

	  public XSDNamedComponent getXSDTypeFromCache(String id) {
	    return (XSDNamedComponent)partToXSDCache.get(id);
	  }

	  public XSDNamedComponent getXSDTypeFromSchema(Part part) {
	    boolean isElementDeclaration = (part.getTypeName() == null);
	    QName qName = isElementDeclaration ? part.getElementName() : part.getTypeName();
	    return getXSDTypeFromSchema(qName.getNamespaceURI(), qName.getLocalPart(), isElementDeclaration);
	  }

	  public XSDNamedComponent getXSDTypeFromSchema(String namespaceURI, String localName, boolean isElementDeclaration) {
	    for (int i = 0; i < xsdSchemaList.size(); i++) {
	      XSDSchema xsdSchema = (XSDSchema)xsdSchemaList.elementAt(i);
	      Vector components = new Vector();
	      if (isElementDeclaration)
	        components.addAll(xsdSchema.getElementDeclarations());
	      else
	        components.addAll(xsdSchema.getTypeDefinitions());
	      for (Iterator it = components.iterator(); it.hasNext(); ) {
	        XSDNamedComponent component  = (XSDNamedComponent)it.next();
	        String compNSURI = component.getTargetNamespace();
	        String compLocalname = component.getName();
	        if (compNSURI != null && compLocalname != null && compNSURI.equals(namespaceURI) && compLocalname.equals(localName))
	          return component;
	      }
	    }
	    return null;
	  }

	  public XSDNamedComponent resolveXSDNamedComponent(XSDNamedComponent component)
	  {
	    if (component != null)
	    {
	      String uri = component.getURI();
	      String qname = component.getQName();
	      for (int i = 0; i < xsdSchemaList.size(); i++)
	      {
	        XSDSchema xsdSchema = (XSDSchema)xsdSchemaList.elementAt(i);
	        if (xsdSchema != null)
	        {
	          String targetNS = xsdSchema.getTargetNamespace();
	          if (targetNS != null && targetNS.equals(trimQName(uri, qname)))
	          {
	            XSDNamedComponent resolvedComponent = null;
	            if (component instanceof XSDTypeDefinition)
	              resolvedComponent = xsdSchema.resolveTypeDefinition(qname);
	            else if (component instanceof XSDElementDeclaration)
	              resolvedComponent = xsdSchema.resolveElementDeclaration(qname);
	            else if (component instanceof XSDModelGroupDefinition)
	              resolvedComponent = xsdSchema.resolveModelGroupDefinition(qname);
	            if (isComponentResolvable(resolvedComponent))
	              return resolvedComponent;
	          }
	        }
	      }
	    }
	    return null;
	  }

	  private String trimQName(String uri, String qname)
	  {
	    int index = uri.indexOf(qname);
	    if (index != -1)
	    {
	      String ns = uri.substring(0, index);
	      if (ns.charAt(index-1) == POUND)
	        return ns.substring(0, index-1);
	      else
	        return ns;
	    }
	    else
	      return uri;
	  }

	  private void addToCache(String id, XSDNamedComponent component) {
	    partToXSDCache.put(id,component);
	  }

	  protected boolean isComponentResolvable(XSDComponent component)
	  {
	    if (component == null)
	      return false;
	    XSDSchema schema = component.getSchema();
	    if (schema == null)
	      return false;
	    if (schema.getTargetNamespace() == null)
	      return false;
	    return true;
	  }
}
