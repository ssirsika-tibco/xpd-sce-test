package com.tibco.bx.debug.core.invoke.datamodel;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.wsdl.WSDLException;
import javax.xml.namespace.QName;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.wst.common.uriresolver.internal.util.URIEncoder;
import org.eclipse.wst.ws.internal.parser.discovery.NetUtils;
import org.eclipse.wst.ws.internal.parser.discovery.WebServicesParserExt;
import org.eclipse.wst.ws.internal.parser.wsil.WebServicesParser;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.wst.wsdl.Import;
import org.eclipse.wst.wsdl.Types;
import org.eclipse.wst.wsdl.UnknownExtensibilityElement;
import org.eclipse.wst.wsdl.internal.impl.XSDSchemaExtensibilityElementImpl;
import org.eclipse.xsd.XSDPackage;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.XSDSchemaDirective;
import org.eclipse.xsd.impl.XSDSchemaImpl;
import org.eclipse.xsd.util.XSDParser;
import org.eclipse.xsd.util.XSDResourceFactoryImpl;

import com.tibco.bx.debug.core.invoke.util.StringUtils;
import com.tibco.bx.debug.core.util.URLUtils;


public class LaunchWsdlElement {
	

	private Definition definition;
	private Vector schemaList;
	private Vector schemaURIs;
	private static Vector w3SchemaQNameList;
	private static Vector constantSchemaList;
	
	private ResourceSet resourceSet;
	
	
	public Vector getSchemaList() {
		return schemaList;
	}

	public LaunchWsdlElement(Definition definition) {
		super();
		schemaList = new Vector();
		schemaURIs = new Vector();
		this.definition = definition;
	}

	public void buildModel(){
		
		resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
				.put(Resource.Factory.Registry.DEFAULT_EXTENSION,
						new XSDResourceFactoryImpl());
		// Register the package to ensure it is available during loading.
		resourceSet.getPackageRegistry().put(XSDPackage.eNS_URI,
				XSDPackage.eINSTANCE);
		
		//definition_.getServices();
		if(definition != null){
			gatherSchemas(definition, null);
		}
		
		 for (int i=0;i<constantSchemaList.size();i++)
		        schemaList.addElement(constantSchemaList.elementAt(i));
		
	}
	
	private final boolean isW3SchemaElementType(QName qname) {
		for (int i = 0; i < w3SchemaQNameList.size(); i++) {
			QName w3SchemaQName = (QName) w3SchemaQNameList.elementAt(i);
			if (w3SchemaQName.equals(qname))
				return true;
		}
		return false;
	}

	private void gatherSchemas(Definition definition, String definitionURL) {

		Types types = (Types) definition.getTypes();
		if (types != null) {
			List extTypes = types.getExtensibilityElements();
			if (extTypes != null) {
				for (int i = 0; i < extTypes.size(); i++) {
					XSDSchema xsdSchema = null;
					Object obj = extTypes.get(i);
					if (obj instanceof UnknownExtensibilityElement) {
						UnknownExtensibilityElement schemaElement = (UnknownExtensibilityElement) obj;
						if (isW3SchemaElementType(schemaElement
								.getElementType())) {
							xsdSchema = XSDSchemaImpl
									.createSchema(schemaElement.getElement());
							if (!checkSchemaURI(definitionURL)) {
								schemaList.addElement(xsdSchema);
								gatherSchemaDirective(xsdSchema, definitionURL);
							}
							// We need to add the schema to a Resource in a
							// Resource set for proper validation
							org.eclipse.emf.common.util.URI uri = org.eclipse.emf.common.util.URI
									.createURI(definitionURL);
							Resource resource = resourceSet.createResource(uri);
							// Add the Schema to the resource
							resource.getContents().add(xsdSchema);
						}
					} else if (obj instanceof XSDSchemaExtensibilityElementImpl) {
						XSDSchemaExtensibilityElementImpl schemaElement = (XSDSchemaExtensibilityElementImpl) obj;
						xsdSchema = schemaElement.getSchema();
						if (!schemaList.contains(xsdSchema)) {
							schemaList.addElement(xsdSchema);
							gatherSchemaDirective(xsdSchema, definitionURL);
						}
					}
				}
			}
		}
		Map imports = definition.getImports();
		if (imports != null)
			gatherImportedSchemas(definition, imports);
	}

	
	private final void gatherImportedSchemas(Definition definition, Map imports) {
		for (Iterator iterator = imports.keySet().iterator(); iterator
				.hasNext();) {
			List importList = (List) imports.get(iterator.next());
			for (int i = 0; i < importList.size(); i++) {
				Import imp = (Import) importList.get(i);
				StringBuffer locURI = new StringBuffer(imp.getLocationURI());
				if (!StringUtils.validateURL(locURI.toString())) {
					String base = definition.getDocumentBaseURI();
					locURI.insert(0, base.substring(0,
							base.lastIndexOf('/') + 1));
				}
				try {
					Definition importDef = loadWSDL(locURI.toString());
					gatherSchemas(importDef, locURI.toString());
				} catch (WSDLException e) {
					// May be an XSD file.
					gatherSchema(locURI.toString());
				}
			}
		}
	}

	private final void gatherSchema(String locURI) {
		XSDSchema xsdSchema = getSchema(locURI);
		if (xsdSchema != null) {
			schemaList.addElement(xsdSchema);
			gatherSchemaDirective(xsdSchema, locURI);
		}
	}
	
	private Definition loadWSDL(String wsdlURL)  throws WSDLException{

		WebServicesParser parser = new WebServicesParserExt();
		try {
			return (Definition) parser.getWSDLDefinitionVerbose(wsdlURL);
		} catch (WSDLException wsdle) {
			throw wsdle;
		} catch (Throwable t) {
			throw new WSDLException(WSDLException.OTHER_ERROR, t.getMessage(),
					t);
		}
	}

	private final void gatherSchemaDirective(XSDSchema xsdSchema,
			String xsdSchemaURL) {
		if (xsdSchema != null) {
			EList xsdSchemaContents = xsdSchema.getContents();
			for (Iterator it = xsdSchemaContents.iterator(); it.hasNext();) {
				Object content = it.next();
				if (content instanceof XSDSchemaDirective) {
					XSDSchemaDirective xsdSchemaDirective = (XSDSchemaDirective) content;
					StringBuffer xsdSchemaDirectiveURL = new StringBuffer();
					String xsdSchemaDirectiveLocation = xsdSchemaDirective
							.getSchemaLocation();
					if (xsdSchemaDirectiveLocation != null
							&& xsdSchemaDirectiveLocation.indexOf(':') == -1
							&& xsdSchemaURL != null
							&& xsdSchemaURL.indexOf(':') != -1) {
						// relative URL
						int index = xsdSchemaURL.lastIndexOf('/');
						if (index != -1)
							xsdSchemaDirectiveURL.append(xsdSchemaURL
									.substring(0, index + 1));
						else {
							xsdSchemaDirectiveURL.append(xsdSchemaURL);
							xsdSchemaDirectiveURL.append('/');
						}
					}
					xsdSchemaDirectiveURL.append(xsdSchemaDirectiveLocation);

					// encode the URL so that Schemas with non-ASCII filenames
					// can be resolved
					String xsdSchemaDirectiveURLString = URLUtils
							.encodeURLString(xsdSchemaDirectiveURL.toString());
					// resolve schema directive
					XSDSchema resolvedSchema = xsdSchemaDirective
							.getResolvedSchema();
					if (resolvedSchema == null
							&& xsdSchemaDirectiveURLString.length() > 0)
						resolvedSchema = getSchema(xsdSchemaDirectiveURLString);
					if (resolvedSchema != null) {
						if (!checkSchemaURI(xsdSchemaDirectiveURLString)) {
							schemaList.addElement(resolvedSchema);
							gatherSchemaDirective(resolvedSchema,
									xsdSchemaDirectiveURLString);
						}
					}
				}
			}
		}
	}
	
	private boolean checkSchemaURI(String schemaURI) {
		boolean found = false;

		if(schemaURI == null){
			return found;
		}
		schemaURI = normalize(schemaURI);
		
		Enumeration e = this.schemaURIs.elements();
		while (e.hasMoreElements()) {
			String uri = (String) e.nextElement();
			if (schemaURI.equals(uri)) {
				found = true;
				break;
			}
		}

		if (!found) {
			this.schemaURIs.addElement(schemaURI);
		}
		return found;

	}
	
	private String normalize(String uri) {
		try {
			String encodedURI = URIEncoder.encode(uri, "UTF-8"); //$NON-NLS-1$
			URI normalizedURI = new URI(encodedURI);
			normalizedURI = normalizedURI.normalize();
			return normalizedURI.toString();
		} catch (URISyntaxException e) {
			return uri;
		} catch (UnsupportedEncodingException e) {
			return uri;
		}
	}
	
	private final XSDSchema getSchema(String locURI) {
		locURI = URLUtils.encodeURLString(locURI);
		XSDSchema xsdSchema = XSDSchemaImpl.getSchemaForSchema(locURI);
		if (xsdSchema == null) {
			XSDParser p = new XSDParser();
			InputStream is = NetUtils.getURLInputStream(locURI);
			if (is != null) {
				p.parse(is);
				xsdSchema = p.getSchema();
			}
		}
		return xsdSchema;
	}

	
	static {
		w3SchemaQNameList = new Vector();
		w3SchemaQNameList.addElement(new QName(
				"http://www.w3.org/2001/XMLSchema", "schema")); //$NON-NLS-1$ //$NON-NLS-2$
		w3SchemaQNameList.addElement(new QName(
				"http://www.w3.org/2000/10/XMLSchema", "schema")); //$NON-NLS-1$ //$NON-NLS-2$
		w3SchemaQNameList.addElement(new QName(
				"http://www.w3.org/1999/XMLSchema", "schema")); //$NON-NLS-1$ //$NON-NLS-2$
		constantSchemaList = new Vector();
		constantSchemaList.addElement(XSDSchemaImpl
				.getSchemaForSchema("http://www.w3.org/2001/XMLSchema")); //$NON-NLS-1$
		constantSchemaList.addElement(XSDSchemaImpl
				.getSchemaForSchema("http://www.w3.org/2000/10/XMLSchema")); //$NON-NLS-1$
		constantSchemaList.addElement(XSDSchemaImpl
				.getSchemaForSchema("http://www.w3.org/1999/XMLSchema")); //$NON-NLS-1$
		constantSchemaList
				.addElement(XSDSchemaImpl
						.getSchemaForSchema("http://schemas.xmlsoap.org/soap/encoding/")); //$NON-NLS-1$
		constantSchemaList.addElement(XSDSchemaImpl
				.getSchemaForSchema("http://schemas.xmlsoap.org/wsdl/")); //$NON-NLS-1$
	}
}
