package com.tibco.bx.emulation.bpm.core.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLParserPool;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMLParserPoolImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceFactoryImpl;

import com.tibco.bx.debug.core.models.variable.ICDSDeserializer;
import com.tibco.bx.debug.core.models.variable.ICDSSerializer;
import com.tibco.bx.emulation.bpm.core.common.BomProjectManager;

public class CDSManager implements ICDSDeserializer, ICDSSerializer {

	private static final URI EMPTY_STRING_URI = URI.createURI("");//$NON-NLS-1$
	private static final String ENCODING = "UTF-8"; //$NON-NLS-1$
	private static Map<String, Object> saveOptionMap;
	private static Map<String, Object> loadOptionMap;
	private static XMLParserPool pool;
	
	static {
		pool = new XMLParserPoolImpl();
		loadOptionMap = createLoadOptionMap(pool);
		saveOptionMap = createSaveOptionMap();
	}
	private static CDSManager INSTANCE = new CDSManager();

	private CDSManager() {
		BomProjectManager.getDefault().refresh();
	}

	public static CDSManager getDefault() {
		return INSTANCE;
	}

	public String serialize(Object object) throws IOException {
		EObject objectToSerialize = (EObject) object;
		if (objectToSerialize.eContainer() != null) {
			objectToSerialize = EcoreUtil.copy(objectToSerialize);
		}
		XMLResource resource = createResourceForWritingAsUTF8XML(objectToSerialize);
		StringWriter writer = new StringWriter();
		resource.save(writer, saveOptionMap);
		writer.flush();
		writer.close();
		String result = writer.getBuffer().toString();
		return result;
	}

	public EObject deserialize(String string) throws IOException {
		ResourceSet set = createResourceSet();
		XMLResource resource = (XMLResource) set.createResource(EMPTY_STRING_URI);
		resource.load(new ByteArrayInputStream(string.getBytes(ENCODING)), loadOptionMap);
		EObject root = resource.getContents().get(0);
		if (root != null && root.eClass().getName().equals("DocumentRoot")) {//$NON-NLS-1$
			root = root.eContents().get(0);// get child of DocumentRoot
		}
		return root;
	}
	
	public EObject deserialize(String string,String pName) throws IOException {
		ResourceSet set = createResourceSet();
		EPackage ePackage = BomProjectManager.getDefault().getEPackage(pName);
		if (ePackage != null) {
			set.getPackageRegistry().put(ePackage.getNsURI(), ePackage);
		}
		XMLResource resource = (XMLResource) set.createResource(EMPTY_STRING_URI);
		resource.load(new ByteArrayInputStream(string.getBytes(ENCODING)), loadOptionMap);
		EObject root = resource.getContents().get(0);
		if (root != null && root.eClass().getName().equals("DocumentRoot")) {//$NON-NLS-1$
			root = root.eContents().get(0);// get child of DocumentRoot
		}
		return root;
	}
	
	private XMLResource createResourceForWritingAsUTF8XML(EObject eObject) {
		ResourceSet set = createResourceSet();
		XMLResource resource = (XMLResource) set.createResource(EMPTY_STRING_URI);
		resource.setEncoding(ENCODING);
		resource.getContents().add(eObject);
		return resource;
	}

	private ResourceSet createResourceSet() {
		ResourceSet set = new ResourceSetImpl();
		set.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new XMLResourceFactoryImpl()); //$NON-NLS-1$
		return set;
	}

	private static Map<String, Object> createCommonOptionMap() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put(XMLResource.OPTION_EXTENDED_META_DATA, Boolean.TRUE);
		return options;
	}

	private static Map<String, Object> createLoadOptionMap(XMLParserPool pool) {
		Map<String, Object> options = createCommonOptionMap();
		options.put(XMLResource.OPTION_DEFER_ATTACHMENT, Boolean.TRUE);
		options.put(XMLResource.OPTION_USE_PARSER_POOL, pool);
		options.put(XMLResource.OPTION_USE_DEPRECATED_METHODS, Boolean.FALSE);
		return options;
	}

	private static Map<String, Object> createSaveOptionMap() {
		Map<String, Object> options = createCommonOptionMap();
		options.put(XMLResource.OPTION_SAVE_TYPE_INFORMATION, Boolean.TRUE);
		options.put(XMLResource.OPTION_KEEP_DEFAULT_CONTENT, Boolean.TRUE);
		options.put(XMLResource.OPTION_ENCODING, ENCODING);
		return options;
	}
}
