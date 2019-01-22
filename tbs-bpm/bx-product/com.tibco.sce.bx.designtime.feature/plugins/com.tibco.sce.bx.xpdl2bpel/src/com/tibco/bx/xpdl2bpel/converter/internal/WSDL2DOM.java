package com.tibco.bx.xpdl2bpel.converter.internal;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import javax.wsdl.Message;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.EClassImpl;
import org.eclipse.emf.ecore.impl.EClassifierImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.BasicExtendedMetaData;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.GenericXMLResourceFactoryImpl;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.wst.wsdl.Part;
import org.eclipse.wst.wsdl.internal.util.WSDLResourceFactoryImpl;
import org.eclipse.xsd.XSDComplexTypeDefinition;
import org.eclipse.xsd.XSDTypeDefinition;
import org.eclipse.xsd.ecore.XSDEcoreBuilder;
import org.eclipse.xsd.util.XSDResourceFactoryImpl;
import org.w3c.dom.Document;

import com.tibco.bx.xpdl2bpel.ConverterActivator;
import com.tibco.bx.xpdl2bpel.converter.ConversionException;
import com.tibco.bx.xpdl2bpel.util.WSDLUtils;
import com.tibco.bx.xpdl2bpel.util.internal.SDOXSDEcoreBuilder;

public class WSDL2DOM {
	
	private org.eclipse.wst.wsdl.Definition wsdlDefinition;

	public WSDL2DOM(org.eclipse.wst.wsdl.Definition definition) {
		this.wsdlDefinition = definition;
	}
	
	public EObject getInstanceAsEMF(org.eclipse.wst.wsdl.Part part, String[] assigns, boolean useElementName) throws ConversionException {
		QName qName = part.getElementName() != null ? part.getElementName() : part.getTypeName();		
		XSDTypeDefinition typeDefinition = WSDLUtils.getTypeDefinitionForPart(wsdlDefinition, part);
		if (!(typeDefinition instanceof XSDComplexTypeDefinition)) {
			return null;
		}
		
		ExtendedMetaData extendedMetaData = new BasicExtendedMetaData(part.eResource().getResourceSet().getPackageRegistry());
		XSDEcoreBuilder xsdEcoreBuilder = new SDOXSDEcoreBuilder(extendedMetaData);
		xsdEcoreBuilder.generate(typeDefinition.getSchema());
	    
	    EPackage ePackage = (EPackage)xsdEcoreBuilder.getTargetNamespaceToEPackageMap().get(qName.getNamespaceURI());
		String typeName = typeDefinition.getName();
		if (typeName == null) {
			typeName = qName.getLocalPart();
		}

		EClass rootClass = null;
	    if (ePackage != null) {
	    	rootClass = (EClass) ePackage.getEClassifier(typeName);
	    } 
	    if (rootClass == null) {
	    	rootClass = xsdEcoreBuilder.getEClass((XSDComplexTypeDefinition) typeDefinition);
	    }
	    
	    if (useElementName) {
			// override the type name for the root
			String rootName = qName.getLocalPart();
			rootClass.setName(rootName); 
			if (rootClass instanceof EClassImpl) {
				((EClassImpl)rootClass).getExtendedMetaData().setName(rootName);
			}
	    }
	    EObject root = EcoreUtil.create(rootClass);
	    
	    for (String assign : assigns) {
		    EObject instance = root;

		    String[] paths = assign.split("/"); //$NON-NLS-1$
		    
		    for (int i = 0; i < paths.length; i++) {
		    	String path = paths[i];
		    	if (path == null || path.length() == 0) continue;
		    	
		    	//attributes in XPath start with "@"
		    	if (path.startsWith("@")) { //$NON-NLS-1$
		    		path = path.substring(1);
		    	}
		    	
		    	int pos = path.indexOf(":"); //$NON-NLS-1$
		    	if (pos > 0) {
		    		path = path.substring(pos+1);
		    	}
			    
		    	EStructuralFeature feature = instance.eClass().getEStructuralFeature(path);
			    if (feature == null) {
			    	//see if the feature is using a different capital case
			    	EList<EStructuralFeature> allFeatures = instance.eClass().getEAllStructuralFeatures();
			    	for (EStructuralFeature f : allFeatures) {
						if (f.getName().equalsIgnoreCase(path)) {
							feature = f;
//							feature.setName(path);
							break;
						}
					}
			    	
			    	if (feature == null) {
			    		//bail out if the feature is still not found
				    	break;
			    	}
			    }
			    
			    if (i < paths.length-1) {
			    	if (instance.eIsSet(feature)) {
				    	Object child = instance.eGet(feature);
						instance = child instanceof EObject ? (EObject) child : null;
			    	} else if (!feature.isMany()) {
			    		EClass instanceClass;
			    		if (feature instanceof EReference) {
			    			EReference ref = (EReference) feature;
			    			instanceClass = ref.getEReferenceType();
			    		} else {
				    	    String instanceType = feature.getEType().getName();
							instanceClass = (EClass) instance.eClass().getEPackage().getEClassifier(instanceType);
			    		}
			    	    EObject child = EcoreUtil.create(instanceClass);
			    	    instance.eSet(feature, child);
			    	    instance = child;
			    	}
				    if (instance == null) {
				    	throw new ConversionException(assign + " is an invalid path.");
				    }
			    } else {
		    		if (feature.isMany()) {
		    			continue;
		    		}

			    	Object defaultValue = feature.getDefaultValue();
			    	if (defaultValue == null) {
			    		List<String> enumerationFacet = ((EClassifierImpl)feature.getEType()).getExtendedMetaData().getEnumerationFacet();
		    			if (enumerationFacet != null && !enumerationFacet.isEmpty()) {
		    				defaultValue = enumerationFacet.get(0);
		    			} else if (String.class.equals(feature.getEType().getInstanceClass())) {
				    		defaultValue = "";//$NON-NLS-1$
		    			} else if (BigInteger.class.equals(feature.getEType().getInstanceClass())) {
				    		defaultValue = BigInteger.valueOf(0);//$NON-NLS-1$
		    			} else if (BigDecimal.class.equals(feature.getEType().getInstanceClass())) {
				    		defaultValue = BigDecimal.valueOf(0.0);//$NON-NLS-1$
				    	} else if (XMLGregorianCalendar.class.equals(feature.getEType().getInstanceClass())) {
				    		try {
								defaultValue = DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar());
							} catch (DatatypeConfigurationException e) {
								ConverterActivator.logError("failed to create instance of datetime", e);
							}
				    	} else if (Duration.class.equals(feature.getEType().getInstanceClass())) {
				    		try {
								defaultValue = DatatypeFactory.newInstance().newDuration(0);
							} catch (DatatypeConfigurationException e) {
								ConverterActivator.logError("failed to create instance of duration", e);
							}
				    	} else if (Object.class.equals(feature.getEType().getInstanceClass())) {
				    		//xsd:anySimpleType
				    		defaultValue = "";//$NON-NLS-1$
				    	}
			    	}
					instance.eSet(feature, defaultValue);
			    }
			}
		}

	    return root;
	}
	
	public Document getInstanceAsDOM(org.eclipse.wst.wsdl.Part part, String[] assigns, boolean useElementName) throws ConversionException {
		EObject root = getInstanceAsEMF(part, assigns, useElementName);
		
		// Save EMF to DOM
	    ResourceSet resourceSet = new ResourceSetImpl();
	    resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(
	    		Resource.Factory.Registry.DEFAULT_EXTENSION, new GenericXMLResourceFactoryImpl());
		String nsURI = root.eClass().getEPackage().getNsURI();
		URI uri = URI.createURI(nsURI);
		XMLResource resource = (XMLResource) resourceSet.createResource(uri);
		resource.getContents().add(root);

		HashMap<String, Object> options = new HashMap<String, Object>();
		options.put(XMLResource.OPTION_KEEP_DEFAULT_CONTENT, Boolean.TRUE);
		options.put(XMLResource.OPTION_EXTENDED_META_DATA, Boolean.TRUE);

		Document document = ((XMLResource)resource).save(null, options, null);

		return document;
	}

	public String getInstanceAsString(org.eclipse.wst.wsdl.Part part, String[] assigns, boolean useElementName) throws ConversionException {
		Document document = getInstanceAsDOM(part, assigns, useElementName);

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
	
	public static void main(String[] args) throws Throwable {
		String[] assigns = new String[] {
				"requiredString"//, 
//				"requiredDateTime", 
//				"requriedIntSequence", 
//				"simpleContentWithAttr", 
//				"simpleContentWithAttr/@requiredAttribute", 
//				"simpleContentWithAttr/@optionalAttribute", 
//				"optionalInt", 
//				"optionalString"
				};

	    ResourceSet resourceSet = new ResourceSetImpl();
	    resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xml", new GenericXMLResourceFactoryImpl()); //$NON-NLS-1$
	    resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xsd", new XSDResourceFactoryImpl()); //$NON-NLS-1$
	    resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("wsdl", new WSDLResourceFactoryImpl()); //$NON-NLS-1$
		URI wsdlURI = URI.createFileURI("C:/tmp/SimpleWSDL.wsdl");
		Resource resource = resourceSet.getResource(wsdlURI, true);
		Definition wsdl = (Definition) resource.getContents().get(0);
		QName msgName = new QName(wsdl.getTargetNamespace(), "NewOperationRequest");
		Message message = wsdl.getMessage(msgName);
		Part part = (Part) message.getPart("NewOperationRequest");
		WSDL2DOM wsdl2dom = new WSDL2DOM(wsdl);
		String dom = wsdl2dom.getInstanceAsString(part, assigns, true);
		System.out.println(dom);
	}
	
}
