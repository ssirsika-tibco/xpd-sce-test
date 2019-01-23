/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.n2.wp.utils;

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
 * Utility class to support WP model XSD schema validation.
 * 
 * @author Jan Arciuchiewicz
 */
public class WPSchemaUtil {

    /**
     * Private constructor to prevent instantiation.
     */
    private WPSchemaUtil() {
    }

    /** The URI of the base schema folder. */
    private static final String SCHEMA_BASE =
            "platform:/plugin/com.tibco.n2.common.wsdls/wsdls"; //$NON-NLS-1$

    /**
     * Validate document against BRM schema.
     * 
     * @param xmlIFile
     *            the xml document.
     * @throws IOException
     * @throws SAXException
     * @throws CoreException
     */
    public static IStatus validateAgainstWPXSD(IFile xmlIFile)
            throws IOException, SAXException, CoreException {
        Source document =
                new StreamSource(xmlIFile.getContents(), xmlIFile.getName());
        return XMLUtil.validateAgainstXMLSchema(getWPSchemas(), document);
    }

    public static Source[] getWPSchemas() throws IOException {
        Source channeltype = createXSDSource(SCHEMA_BASE, "channeltype.xsd"); //$NON-NLS-1$
        Source wpModel = createXSDSource(SCHEMA_BASE, "wpModel.xsd"); //$NON-NLS-1$

        // NOTE: order is important
        Source[] schemas = new Source[] { channeltype, wpModel };
        return schemas;
    }

    private static Source createXSDSource(String baseURI, String fileName)
            throws IOException {
        InputStream deMetaXSDInputStream =
                new ResourceSetImpl().getURIConverter().createInputStream(URI
                        .createURI(baseURI + '/' + fileName));
        return new StreamSource(deMetaXSDInputStream, fileName);
    }

}
