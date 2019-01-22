/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.om.transform.de.utils;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.xml.sax.SAXException;

import com.tibco.xpd.resources.util.XMLUtil;

/**
 * Utility class to support Directory Engine XSD schema validation.
 * 
 * @author Jan Arciuchiewicz
 */
public class DESchemaUtil {

    /**
     * Private constructor to prevent instantiation.
     */
    private DESchemaUtil() {
    }

    /** The URI of the base schema folder. */
    private static final String SCHEMA_BASE =
            "platform:/plugin/com.tibco.n2.common.wsdls/wsdls"; //$NON-NLS-1$

    /**
     * Validate document against DE schema.
     * 
     * @param xmlIFile
     *            the xml document.
     * @throws IOException
     * @throws SAXException
     * @throws CoreException
     */
    public static IStatus validateAgainstDEXSD(IFile xmlIFile)
            throws IOException, SAXException, CoreException {
        Source document =
                new StreamSource(xmlIFile.getContents(), xmlIFile.getName());
        return XMLUtil.validateAgainstXMLSchema(getDESchemas(), document);
    }

    public static Source[] getDESchemas() throws IOException {
        Source model = createXSDSource(SCHEMA_BASE, "directory-model-2.0.xsd"); //$NON-NLS-1$

        // NOTE: order is important
        Source[] schemas = new Source[] { model };
        return schemas;
    }

    private static Source createXSDSource(String baseURI, String fileName)
            throws IOException {
        InputStream deMetaXSDInputStream =
                new ResourceSetImpl().getURIConverter()
                        .createInputStream(URI.createURI(baseURI + '/'
                                + fileName));
        return new StreamSource(deMetaXSDInputStream, fileName);
    }

}
