/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.n2.brm.utils;

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
 * Utility class to support BRM XSD schema validation.
 * 
 * @author Jan Arciuchiewicz
 */
public class BRMSchemaUtil {

    /**
     * Private constructor to prevent instantiation.
     */
    private BRMSchemaUtil() {
    }

    /** The URI of the base schema folder. */
    private static final String SCHEMA_BASE =
            "platform:/plugin/com.tibco.xpd.n2.brm/model"; //$NON-NLS-1$

    /**
     * Validate document against BRM schema.
     * 
     * @param xmlIFile
     *            the xml document.
     * @throws IOException
     * @throws SAXException
     * @throws CoreException
     */
    public static IStatus validateAgainstBRMXSD(IFile xmlIFile)
            throws IOException, SAXException, CoreException {
        Source document =
                new StreamSource(xmlIFile.getContents(), xmlIFile.getName());
        return XMLUtil.validateAgainstXMLSchema(getBRMSchemas(), document);
    }

    public static Source[] getBRMSchemas() throws IOException {
        Source comexception = createXSDSource(SCHEMA_BASE, "comexception.xsd"); //$NON-NLS-1$
        Source brm = createXSDSource(SCHEMA_BASE, "brm.xsd"); //$NON-NLS-1$
        Source brmdto = createXSDSource(SCHEMA_BASE, "brmdto.xsd"); //$NON-NLS-1$
        Source brmworkmodel = createXSDSource(SCHEMA_BASE, "brmworkmodel.xsd"); //$NON-NLS-1$
        Source datamodel = createXSDSource(SCHEMA_BASE, "datamodel.xsd"); //$NON-NLS-1$
        Source organisation = createXSDSource(SCHEMA_BASE, "organisation.xsd"); //$NON-NLS-1$
        Source worktype = createXSDSource(SCHEMA_BASE, "worktype.xsd"); //$NON-NLS-1$
        Source commonfacade = createXSDSource(SCHEMA_BASE, "common-facade.xsd"); //$NON-NLS-1$

        // NOTE: order is important
        Source[] schemas =
                new Source[] { comexception, datamodel, organisation, brmdto,
                        brm, worktype, brmworkmodel, commonfacade };
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
