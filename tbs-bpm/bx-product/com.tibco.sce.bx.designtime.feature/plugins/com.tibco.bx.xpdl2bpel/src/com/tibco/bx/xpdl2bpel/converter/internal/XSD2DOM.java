package com.tibco.bx.xpdl2bpel.converter.internal;

import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.GenericXMLResourceFactoryImpl;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.ecore.XSDEcoreBuilder;
import org.eclipse.xsd.util.XSDResourceFactoryImpl;
import org.eclipse.xsd.util.XSDResourceImpl;
import org.w3c.dom.Document;

import com.tibco.bx.xpdl2bpel.converter.ConversionException;

public class XSD2DOM {
	
	private XSDSchema schema;

	public XSD2DOM(XSDSchema schema) {
		this.schema = schema;
	}
	
	public EObject getInstanceAsEMF(String rootElementName, String[] assigns) throws ConversionException {
	    //build ecore model dynamically
		XSDEcoreBuilder xsdEcoreBuilder = new XSDEcoreBuilder();
		xsdEcoreBuilder.generate(schema);
	    EPackage ePackage = (EPackage)xsdEcoreBuilder.getTargetNamespaceToEPackageMap().get(schema.getTargetNamespace());
	    
	    EClass rootClass = (EClass) ePackage.getEClassifier(rootElementName);
	    EObject root = EcoreUtil.create(rootClass);
	    
	    for (String assign : assigns) {
		    EObject instance = root;

		    String[] paths = assign.split("/"); //$NON-NLS-1$
		    
		    for (int i = 0; i < paths.length; i++) {
		    	String path = paths[i];
		    	if (path == null || path.length() == 0) continue;
		    	
		    	int pos = path.indexOf(":"); //$NON-NLS-1$
		    	if (pos > 0) {
		    		path = path.substring(pos);
		    	}
			    
		    	EStructuralFeature feature = instance.eClass().getEStructuralFeature(path);
			    if (feature == null) {
			    	break;
			    }
			    
			    if (i < paths.length-1) {
			    	if (instance.eIsSet(feature)) {
				    	Object child = instance.eGet(feature);
						instance = child instanceof EObject ? (EObject) child : null;
			    	} else {
			    	    String instanceType = feature.getEType().getName();
						EClass instanceClass = (EClass) ePackage.getEClassifier(instanceType);
			    	    EObject child = EcoreUtil.create(instanceClass);
			    	    instance.eSet(feature, child);
			    	    instance = child;
			    	}
				    if (instance == null) {
				    	throw new ConversionException(assign + " is an invalid path.");
				    }
			    }
			}
		}

	    return root;
	}
	
	public Document getInstanceAsDOM(String rootElementName, String[] assigns) throws ConversionException {
		EObject root = getInstanceAsEMF(rootElementName, assigns);
		
		// Convert / "save" EMF to DOM
	    ResourceSet resourceSet = new ResourceSetImpl();
	    resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(
	    		Resource.Factory.Registry.DEFAULT_EXTENSION, new GenericXMLResourceFactoryImpl());
		String nsURI = root.eClass().getEPackage().getNsURI();
		URI uri = URI.createURI(nsURI);
		XMLResource resource = (XMLResource) resourceSet.createResource(uri);
		resource.getContents().add(root);
		Document document = ((XMLResource)resource).save(null, null, null);

		return document;
	}

	public String getInstanceAsString(String rootElementName, String[] assigns) throws ConversionException {
		Document document = getInstanceAsDOM(rootElementName, assigns);

		try {
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer trans = tf.newTransformer();
			trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");  //$NON-NLS-1$
			trans.setOutputProperty(OutputKeys.INDENT, "yes");  //$NON-NLS-1$
			StringWriter sw = new StringWriter();
			trans.transform(new DOMSource(document), new StreamResult(sw));

			return sw.toString();
		} catch (TransformerException e) {
			throw new ConversionException(e.getMessage());
		}		
	}
	
	public static void main(String[] args) throws ConversionException {
	    ResourceSet resourceSet = new ResourceSetImpl();
	    resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xml", new GenericXMLResourceFactoryImpl()); //$NON-NLS-1$
	    resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xsd", new XSDResourceFactoryImpl()); //$NON-NLS-1$

	    //load the schema
	    URI xsdFileUri = URI.createFileURI("C:/tmp/library.xsd");
		XSDResourceImpl xsdResource = (XSDResourceImpl) resourceSet.getResource(xsdFileUri, true);
	    XSDSchema xsdSchema = xsdResource.getSchema();
	    
	    XSD2DOM converter = new XSD2DOM(xsdSchema);
		String[] assigns = {"/title", "/pages", "/author/name"};
	    String output = converter.getInstanceAsString("Book", assigns);
	    System.out.println(output);
	}

}
