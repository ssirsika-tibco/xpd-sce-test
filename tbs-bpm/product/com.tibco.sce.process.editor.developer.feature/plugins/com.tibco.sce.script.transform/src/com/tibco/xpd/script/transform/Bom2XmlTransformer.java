/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.script.transform;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.util.XSDResourceImpl;
import org.openarchitectureware.xsd.OawXMLResource;
import org.openarchitectureware.xsd.XSDMetaModel;

import com.tibco.xpd.bom.xsdtransform.api.XSDUtil;
import com.tibco.xpd.script.transform.util.TransformUtil;

/**
 * Transforms BOM to XML.
 * <p>
 * <i>Created: 24 July 2008</i>
 * </p>
 * 
 * @author Miguel Torres
 */
public class Bom2XmlTransformer {

    /**
     * Performs the actual transformation
     * 
     * @param model
     * @return
     */
    public String transform(String rootName, final Classifier classifier,
            List<IStatus> issues) {
        if (classifier instanceof Class) {
            if (issues == null) {
                issues = new ArrayList<IStatus>();
            }
            // Do bom2xsd transform
            Object object =
                    XSDUtil.incrementalTransformBOMToXSD((Class) classifier,
                            null,
                            false,
                            issues,
                            true);

            if (object instanceof EObject) {
                XSDSchema schema = getSchema((EObject) object);
                if (schema != null) {
                    String xmlDoc =
                            TransformUtil.getXmlDocStr(rootName,
                                    classifier,
                                    schema);
                    return xmlDoc;
                }
            }
        }
        return null;
    }

    private XSDSchema getSchema(EObject obj) {
        XSDSchema schema = null;
        Resource res =
                new OawXMLResource(URI.createURI("/"), new XSDMetaModel()); //$NON-NLS-1$
        if (res.getContents() != null) {
            res.getContents().add(obj);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            try {
                res.save(os, new HashMap());
                schema = getSchema(os);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (CoreException e) {
                e.printStackTrace();
            }
        }
        return schema;
    }

    /**
     * Get the schema from the given File.
     * 
     * @param file
     *            schema file.
     * @return schema, or <code>null</code> if schema not found.
     */
    private XSDSchema getSchema(ByteArrayOutputStream os) throws IOException,
            CoreException {
        XSDSchema schema = null;
        if (os != null) {
            ByteArrayInputStream is =
                    new ByteArrayInputStream(os.toByteArray());

            Resource res = null;
            // If resource is not created then do that and add to cache
            if (res == null) {
                res = new XSDResourceImpl(URI.createURI("/"));

                if (res != null) {
                    res.load(is, new HashMap());
                    // Check for any errors - if found report it
                    if (res.getErrors() != null && !res.getErrors().isEmpty()) {
                        List<IStatus> status = new ArrayList<IStatus>();
                        for (Diagnostic diagnostic : res.getErrors()) {
                            status.add(new Status(IStatus.ERROR,
                                    Activator.PLUGIN_ID, String
                                            .format("Error message",
                                                    diagnostic.getMessage(),
                                                    diagnostic.getLine(),
                                                    diagnostic.getColumn())));
                        }
                        throw new CoreException(new MultiStatus(
                                Activator.PLUGIN_ID, IStatus.ERROR,
                                status.toArray(new IStatus[status.size()]),
                                String.format("Error message", ""), null));
                    }
                }
            }
            if (res != null) {
                EList<EObject> contents = res.getContents();

                if (contents != null && !contents.isEmpty()
                        && contents.get(0) instanceof XSDSchema) {
                    schema = (XSDSchema) contents.get(0);
                }
            }
        }
        return schema;
    }

}
