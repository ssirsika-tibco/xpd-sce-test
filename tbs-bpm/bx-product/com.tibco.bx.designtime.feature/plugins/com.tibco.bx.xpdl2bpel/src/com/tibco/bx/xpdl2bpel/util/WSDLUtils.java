package com.tibco.bx.xpdl2bpel.util;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.wsdl.Types;
import javax.xml.namespace.QName;

import org.eclipse.bpel.model.resource.BPELResourceSetImpl;
import org.eclipse.bpel.model.util.BPELUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.wst.wsdl.Binding;
import org.eclipse.wst.wsdl.BindingOperation;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.wst.wsdl.ExtensibilityElement;
import org.eclipse.wst.wsdl.Import;
import org.eclipse.wst.wsdl.Operation;
import org.eclipse.wst.wsdl.Port;
import org.eclipse.wst.wsdl.PortType;
import org.eclipse.wst.wsdl.Service;
import org.eclipse.wst.wsdl.binding.http.HTTPAddress;
import org.eclipse.wst.wsdl.binding.soap.SOAPAddress;
import org.eclipse.wst.wsdl.binding.soap.SOAPBinding;
import org.eclipse.wst.wsdl.binding.soap.SOAPBody;
import org.eclipse.wst.wsdl.internal.impl.ImportImpl;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.XSDSchemaDirective;
import org.eclipse.xsd.XSDTypeDefinition;
import org.eclipse.xsd.impl.XSDSchemaImpl;
import org.eclipse.xsd.util.XSDConstants;
import org.w3c.dom.Element;

import com.tibco.bx.xpdl2bpel.ConverterActivator;
import com.tibco.bx.xpdl2bpel.Messages;
import com.tibco.bx.xpdl2bpel.converter.ConversionException;
import com.tibco.bx.xpdl2bpel.converter.internal.WsdlCopyHelper;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.xpdExtension.PortTypeOperation;

public class WSDLUtils {

    // This is just some default starting name
    private static final String  DEFAULT_WSDL_OUTPUT_FILE_NAME = "wsdlFile"; //$NON-NLS-1$

    /** Get the root of the WSDL file name parameter.  Removes file extension
     * and then removes name following final slash ('/').
     * @param fileName A WSDL filename to get the root of.
     * @return The root of the WSDL file name parameter. */
    public static String getWSDLFileNameRoot (String fileName) {
        String  resultFileName = DEFAULT_WSDL_OUTPUT_FILE_NAME;
        if (fileName != null) {
            int  indexOfLastDot = fileName.lastIndexOf ('.'); //$NON-NLS-1$
            if (indexOfLastDot > 0) {
                fileName = fileName.substring (0, indexOfLastDot);
            }
            int  indexOfLastSlash = fileName.lastIndexOf ('/'); //$NON-NLS-1$
            if (indexOfLastSlash != -1 && indexOfLastSlash < fileName.length ()) {
                fileName = fileName.substring (indexOfLastSlash + 1);
            }
            if (!fileName.equals ("") && fileName.indexOf ('/') == -1 && fileName.indexOf ('.') == -1) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                resultFileName = fileName;
            }
        }
        return resultFileName;
    }


    /**
	 * Read a WSDL URI into an EMF model and return it.
	 *
	 * @param wsdlURI
	 *            The URI of a WSDL to read.
	 * @return A Resource containing the EMF representation of the WSDL
     * @throws ConversionException
	 */
	public static Resource readWSDLURIIntoEMFModel(final URI wsdlURI) throws ConversionException {
		try {
			return new ResourceSetImpl().getResource(wsdlURI, true);
		} catch (RuntimeException e) {
			String msg = String.format(Messages.getString("ConvertUtil.cannotLoadEMF"), new Object[] {wsdlURI}); //$NON-NLS-1$
			throw new ConversionException(msg, e);
		}
	}

	public static Definition loadWsdlDefinition(final URI wsdlURI) throws ConversionException {
		Resource resource = readWSDLURIIntoEMFModel(wsdlURI);
		if (resource != null && !resource.getContents().isEmpty()) {
			Object firstContent = resource.getContents().get(0);
			if (firstContent instanceof Definition) {
				return (Definition) firstContent;
			}
		}
		return null;
	}

	public static Definition getWsdlDefinition(IResource wsdl) {
		WorkingCopy wsdlResource = XpdResourcesPlugin.getDefault().getWorkingCopy(wsdl);
		return wsdlResource != null && wsdlResource.isExist() ? (Definition) wsdlResource.getRootElement() : null;
	}

    /**
	 * Create a new WSDL file with the name specified in wsdlFilename with the
	 * content in wsdlDefinition.
	 *
	 * @param wsdlURI
	 *            The URI of the new WSDL (overwritten if it already exists).
	 * @param wsdlDefinition
	 *            The WSDL contents to put in the new WSDL file.
	 * @throws IOException
	 */
    public static void saveWSDLFile(URI wsdlURI,
			org.eclipse.wst.wsdl.Definition wsdlDefinition) throws Exception {
    	ResourceSet resourceSet = new ResourceSetImpl();
    	resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
    	Resource resource = resourceSet.createResource(wsdlURI);
    	resource.getContents().add(wsdlDefinition);
    	resource.save(null);
	}
    
    public static IFile getFile(URI fileURI) {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IFile file = root.getFile(new Path(fileURI.toPlatformString(true)));
    	return file;
    }

    public static boolean fileExists(URI fileURI) {
		IFile file = getFile(fileURI);
    	return file != null && file.exists();
    }

    /**
     * Clones a WSDL file from wsdlURI to a target folder. 
     * @param wsdlURI the source URI
     * @param targetFolderUri the target folder
     * @return the cloned WSDL
     * @throws Exception
     */
    public static  org.eclipse.wst.wsdl.Definition clone(URI wsdlURI, URI targetFolderUri) throws Exception {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IFile wsdlFile = root.getFile(new Path(wsdlURI.toPlatformString(true)));
		IFolder targetFolder = root.getFolder(new Path(targetFolderUri.toPlatformString(true)));
		WsdlCopyHelper helper = new WsdlCopyHelper(wsdlFile, targetFolder, wsdlFile.getName());
		helper.run(null);
		
		URI targetFileURI = targetFolderUri.appendSegment(wsdlFile.getName());
		return loadWsdlDefinition(targetFileURI);

	}
	/**
	 * this method used to read the initialize uri from giving portType
	 * 
	 * @param portType
	 */
	public static String getBindingLocation(PortType portType) {		
		Binding binding = getBinding(portType);
		if (binding != null) {
			Collection<Service> services = portType.getEnclosingDefinition().getServices().values();
			for (Service service : services) {
				Port port = null;
				Collection<Port> ports = service.getPorts().values();
				for (Port p : ports) {
					if (p.getBinding() == binding) {
						port = p;
						break;
					}
				}
				if (port != null) {
					List addresses = port.getExtensibilityElements();
					for (Object address : addresses) {
						if (address instanceof SOAPAddress) {
							return ((SOAPAddress) address).getLocationURI();
						} else if (address instanceof HTTPAddress) {
							return ((HTTPAddress) address).getLocationURI();
						}
					}
					break;
				}
			}
		}
		
		return ""; //$NON-NLS-1$
	}

	/**
	 * this method used to read the initialize binding style from giving portType
	 * 
	 * @param portType
	 */
	public static String getBindingStyle(PortType portType){
		String bindingStyle = ""; //$NON-NLS-1$
		Binding binding = getBinding(portType);
		if (binding != null) {
			EList<ExtensibilityElement> extensibilityElements = binding
					.getEExtensibilityElements();
			for (ExtensibilityElement ee : extensibilityElements) {
				if (ee instanceof SOAPBinding) {
					bindingStyle = ((SOAPBinding) ee).getStyle();
					break;
				}
			}

		}
		return bindingStyle;
	}
	
	public static String getOperationEncoding(Operation operation) {
		PortType portType = (PortType) operation.eContainer();
		Binding binding = getBinding(portType);
		if (binding != null) {
			List<BindingOperation> bindingOperations = binding
					.getBindingOperations();
			for (BindingOperation o : bindingOperations) {
				if (o.getOperation() == operation) {
					List<ExtensibilityElement> extensibilityElements = 
						o.getBindingInput().getExtensibilityElements();
					for (ExtensibilityElement ee : extensibilityElements) {
						if (ee instanceof SOAPBody) {
							return ((SOAPBody) ee).getUse();
						}
					}
				}
			}

		}
		
		return null;
	}
	
	public static String getBindingTransport(PortType portType){
		String transportURI = ""; //$NON-NLS-1$
		Binding binding = getBinding(portType);
		if (binding != null) {
			EList<ExtensibilityElement> extensibilityElements = binding
					.getEExtensibilityElements();
			for (ExtensibilityElement ee : extensibilityElements) {
				if (ee instanceof SOAPBinding) {
					transportURI = ((SOAPBinding) ee).getTransportURI();
					break;
				}
			}

		}
		if(!transportURI.equals("")){
			int lastDash = transportURI.lastIndexOf("/");
			transportURI = transportURI.substring(lastDash+1);
		}
		return transportURI;
	}
	
	private static Binding getBinding(PortType portType){
		Definition definition = portType.getEnclosingDefinition();
		Map bindings = definition.getBindings();
		Binding binding = null;
		Iterator bindingIterator = bindings.values().iterator();
		while (bindingIterator.hasNext()) {
			binding = (Binding) bindingIterator.next();
			if (binding.getPortType() == portType) {
				break;
			}
		}
		return binding;
	}
	
	public static XSDElementDeclaration getElementDeclarationForPart(Definition wsdlDefinition, javax.wsdl.Part part) {
		QName qName = part.getElementName() != null ? part.getElementName() : part.getTypeName();
		XSDElementDeclaration elementDeclaration = XSDUtil.resolveXSDElementDeclaration(wsdlDefinition, qName);
		if (elementDeclaration != null && elementDeclaration.isElementDeclarationReference()) {
			elementDeclaration = elementDeclaration.getResolvedElementDeclaration();
		}
		return elementDeclaration;
	}
	
	public static XSDTypeDefinition getTypeDefinitionForPart(Definition wsdlDefinition, javax.wsdl.Part part) {
		QName qName = part.getElementName() != null ? part.getElementName() : part.getTypeName();
		
		XSDTypeDefinition typeDefinition;
		if (part.getElementName() != null) {
			// e.g. <wsdl:part name = "PO" element = "po:PurchaseOrder"/>
			XSDElementDeclaration elementDeclaration = XSDUtil.resolveXSDElementDeclaration(wsdlDefinition, qName);
			if (elementDeclaration.isElementDeclarationReference()) {
				elementDeclaration = elementDeclaration.getResolvedElementDeclaration();
			}
			typeDefinition = elementDeclaration.getTypeDefinition();
		} else {
			// e.g. <wsdl:part name = "PO" type = "po:PurchaseOrderType"/>
			typeDefinition = XSDUtil.resolveXSDTypeDefinition(wsdlDefinition, qName);
		}

		return typeDefinition;
	}

    /**
     * Return the list of schemas that are imported in the WSDL definition.
     * This includes XSD imports and schemas present in the WSDL as well.
     *
     * @param wsdlDefinition the BPEL process
     * @param bIncludeXSD whether the XSD standard schemas ought to be included
     * regardless of import.
     *
     * @return a list of XSDScheme objects
     */
	public static Collection<XSDSchema> getSchemaImports(Definition wsdlDefinition, boolean bIncludeXSD) {
		if (wsdlDefinition == null) {
			return Collections.emptyList();
		}

		Map<String, XSDSchema> schemas = new HashMap<String, XSDSchema>();

		resolveSchemas(wsdlDefinition, schemas);
		
		if (bIncludeXSD) {
			XSDSchema schemaForSchema = XSDSchemaImpl.getSchemaForSchema(XSDConstants.SCHEMA_FOR_SCHEMA_URI_2001);
			schemas.put(schemaForSchema.getTargetNamespace(), schemaForSchema);
		}

		return schemas.values();
	}


	private static void resolveSchemas(Definition wsdlDefinition, Map<String, XSDSchema> allSchemas) {
		//search WSDL imports
		Collection<List<Import>> imports = wsdlDefinition.getImports().values();
		for (List<Import> importList : imports) {
			for (Import wsdlImport : importList) {
    			((ImportImpl)wsdlImport).importDefinitionOrSchema();
    			XSDSchema schema = wsdlImport.getESchema();
    			if (schema != null && schema.getTargetNamespace() != null) {
					allSchemas.put(schema.getTargetNamespace(), schema);
					resolveSchemas(schema, allSchemas, wsdlDefinition.eResource());
    			}
    			Definition wsdlDef = wsdlImport.getEDefinition(); 
    			if (wsdlDef != null) {
    				resolveSchemas(wsdlDef, allSchemas);
    			}
			}
		}
		
		//search XSD imports
		Types types = wsdlDefinition.getTypes();
		if (types != null) {
			List extensibilityElements = types.getExtensibilityElements();
			for (Object ee : extensibilityElements) {
				if (!(ee instanceof ExtensibilityElement)) {
					continue;
				}
				Element element = ((ExtensibilityElement) ee).getElement();
				try {
					XSDSchema schema = XSDSchemaImpl.createSchema(element);
					allSchemas.put(schema.getTargetNamespace(), schema);
					
					resolveSchemas(schema, allSchemas, wsdlDefinition.eResource());
				} catch (Exception t) {
					ConverterActivator.logError(
							"Failed to analyze the imported schema for " + wsdlDefinition.getTargetNamespace(), t);
				}
			}
		}
	}
	
	private static void resolveSchemas(XSDSchema xsdSchema, Map<String, XSDSchema> allSchemas, Resource baseResource) {
		List xsdSchemaContents = xsdSchema.getContents();
		for (Object content : xsdSchemaContents) {
			if (content instanceof XSDSchemaDirective) {
				XSDSchemaDirective xsdSchemaDirective = (XSDSchemaDirective) content;
				XSDSchema resolvedSchema = xsdSchemaDirective.getResolvedSchema();
				
				if (resolvedSchema == null) {
					String location = xsdSchemaDirective.getSchemaLocation();
					if (location == null) {
						continue;
					}
					
					Resource resource = xsdSchema.eResource() != null ? xsdSchema.eResource() : baseResource;
					
			        if (!resource.getURI().isRelative()) {
			            location = URI.createURI(location).resolve(resource.getURI()).toString();
			        }
			        
			        URI locationURI = URI.createURI(location);       
			        resolvedSchema = XSDSchemaImpl.getSchemaForSchema(locationURI.toString());
					// if schema is not cached, load it
					if (resolvedSchema == null) {
				        BPELResourceSetImpl hackedResourceSet = BPELUtils.slightlyHackedResourceSet(baseResource.getResourceSet());
				        try {
				        	Resource result = hackedResourceSet.getResource(locationURI, true, "*.xsd");
					        if (!result.getContents().isEmpty()) {
					        	resolvedSchema = (XSDSchema) result.getContents().get(0);
					        }
				        } catch (Throwable t) {
				        	ConverterActivator.logError("Failed to load the schema " + locationURI, t);
				        }
					}
				}
				
				if (resolvedSchema != null) {
					if (allSchemas.containsKey(resolvedSchema.getTargetNamespace())) {
						continue;
					}
					allSchemas.put(resolvedSchema.getTargetNamespace(), resolvedSchema);
					resolveSchemas(resolvedSchema, allSchemas, baseResource);
				}
			}
		}
	}
	
	public static String getUniqueNamespacePrefix(String prefix, Set<String> existingPrefixes) {
		if (!existingPrefixes.contains(prefix)) {
			return prefix;
		}
		
		String newPrefix;
		
		//duplicate is found; try to append a number to the prefix, e.g. foo -> foo1, and foo1 -> foo2
		Pattern p = Pattern.compile("([\\d]+)$"); //$NON-NLS-1$
		Matcher m = p.matcher(prefix);		
		if (m.find()) {
			String number = m.group();
			StringBuffer result = new StringBuffer();
		    m.appendReplacement(result, String.valueOf(Integer.parseInt(number)+1));//$NON-NLS-1$
			m.appendTail(result);
			newPrefix = result.toString();
		} else {
			newPrefix = prefix + "1";
		}
		
		if (existingPrefixes.contains(newPrefix)) {
			return getUniqueNamespacePrefix(newPrefix, existingPrefixes);
		}
		return newPrefix;
	}
	
	/**
	 * Resolves the <code>PortType</code> based on the XPD's <code>PortTypeOperation</code>, which
	 * lacks the namespace information.
	 * @param definition the WSDL definition to search in
	 * @param pto the <code>PortTypeOperation</code>
	 * @return the PortType that corresponds to pto
	 */
	public static javax.wsdl.PortType findPortType(javax.wsdl.Definition definition, PortTypeOperation pto) {
		//There is no namespace information for the port type in the WebServiceOperation; 
		//we'll assume that the port type's namespace is the WSDL's target namespace
		QName portTypeName = new QName(definition.getTargetNamespace(), pto.getPortTypeName());
		javax.wsdl.PortType result = definition.getPortType(portTypeName);
		if (result != null) {
			return result;
		}
		
		// The port type may be defined under another namespace from an imported WSDL
		Collection<List<Import>> imports = definition.getImports().values();
		for (List<Import> importList : imports) {
			for (Import wsdlImport : importList) {
    			result = findPortType(wsdlImport.getDefinition(), pto);
    			if (result != null) {
    				return result;
    			}
			}
		}
		
		//not found
		return null;
	}
	
}
