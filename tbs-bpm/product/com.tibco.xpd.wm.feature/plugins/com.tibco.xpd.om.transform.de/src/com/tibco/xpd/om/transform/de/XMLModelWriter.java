/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.om.transform.de;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;

import com.tibco.n2.directory.model.de.DeFactory;
import com.tibco.n2.directory.model.de.DocumentRoot;
import com.tibco.n2.directory.model.de.ModelType;
import com.tibco.n2.directory.model.de.util.DeResourceFactoryImpl;

/**
 * Serializes model to XML.
 * <p>
 * <i>Created: 12 Mar 2008</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 */
public class XMLModelWriter {

    /** XML file extension */
    private static final String XML = "xml"; //$NON-NLS-1$

    private static Map<Object, Object> xmlSaveOptions;

    /**
     * Writes model elements to the resource represented by URI.
     * 
     * @param uri
     *            the URI of the resource.
     * @param model
     *            collection of model elements to be serialized.
     * @throws IOException
     *             when problems with serialization.
     */
    public void write(URI uri, Collection<EObject> model) throws IOException {
        Map<String, Object> extensionToFactoryMap =
                Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap();
        Object previousXMLFactory = extensionToFactoryMap.get(XML);
        extensionToFactoryMap.put(XML, new DeResourceFactoryImpl());
        try {
            ResourceSetImpl rsImpl = new ResourceSetImpl();
            Resource r = rsImpl.createResource(uri);

            DocumentRoot documentRoot =
                    DeFactory.eINSTANCE.createDocumentRoot();
            documentRoot.setModel((ModelType) model.iterator().next());
            r.getContents().addAll(Arrays.asList(documentRoot));

            r.save(getSaveOptions());
        } finally {
            extensionToFactoryMap.put(XML, previousXMLFactory);
        }
    }

    public void write(IFile file, Collection<EObject> model)
            throws IOException, CoreException {
        final URI fileURI = URI.createFileURI(file.getLocation().toOSString());
        write(fileURI, model);
        file.refreshLocal(IResource.DEPTH_ZERO, null);
    }

    private static Map<Object, Object> getSaveOptions() {
        if (xmlSaveOptions == null) {
            xmlSaveOptions = new HashMap<Object, Object>();
            XMIResource resource = new XMIResourceImpl();
            // default save options.
            xmlSaveOptions.putAll(resource.getDefaultSaveOptions());
            xmlSaveOptions.put(XMIResource.OPTION_ENCODING, "UTF-8"); //$NON-NLS-1$
            xmlSaveOptions
                    .put(XMIResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE);
        }
        return xmlSaveOptions;
    }

}
