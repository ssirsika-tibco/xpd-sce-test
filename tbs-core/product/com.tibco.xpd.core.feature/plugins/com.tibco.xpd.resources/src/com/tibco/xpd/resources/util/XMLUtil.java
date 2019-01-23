/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.resources.util;

import java.io.IOException;

import javax.xml.transform.Source;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.xml.sax.SAXException;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.internal.Messages;

/**
 * XML utility class.
 * 
 * @since 3.3
 * @author Jan Arciuchiewicz
 */
public class XMLUtil {

    /**
     * Validate XML document against XMLSchema. If document is invalid
     * SAXException is thrown.<br/>
     * 
     * <b>Example usage to validate IFile against schemas from test plug-in:</b>
     * <code>
     * <pre>
     * private static String SCHEMA_BASE_URI = &quot;platform:/plugin/com.tibco.xpd.om.test/resources/schema&quot;;
     * 
     * ...
     * 
     * String deMetaFileName = &quot;directory-metamodel-1.0.xsd&quot;;
     * InputStream deMetaXSDInputStream =
     *         new ResourceSetImpl().getURIConverter().createInputStream(URI
     *                 .createURI(SCHEMA_BASE_URI + deMetaFileName));
     * Source deMetaXSD = new StreamSource(deMetaXSDInputStream, deMetaFileName);
     * 
     * String deFileName = &quot;directory-model-1.0.xsd&quot;;
     * InputStream deXSDInputStream =
     *         new ResourceSetImpl().getURIConverter().createInputStream(URI
     *                 .createURI(SCHEMA_BASE_URI + deFileName));
     * Source deXSD = new StreamSource(deXSDInputStream, deFileName);
     * 
     * //deMetaXSD is imported by deXSD so it must be before deXSD.
     * Source[] schemas = new Source[] { deMetaXSD, deXSD };
     * Source document = new StreamSource(xmlIFile.getContents(), xmlIFile.getName());
     * IStatus s =XMLUtil.validateAgainstXMLSchema(schemas, document);
     * log(s);
     * </pre>
     * </code>
     * 
     * @param schemas
     *            the array of sources containing schema definitions. The order
     *            of schemas is important. All imported schemas must be
     *            specified first. It is also important to provide the valid
     *            systemIds for the schema sources so the references can be
     *            resolved.
     * @param document
     *            xml document source to validate.
     * @return OK status if document is valid against schema. ERROR status will
     *         be returned with a accompanying SAXException if document isn't
     *         valid or if there is problem with compiling schema (for example:
     *         the provided schema definitions order is incorrect, or part of
     *         definition is missed).
     * @throws IOException
     *             if problem with reading documents.
     * 
     * @throws NullPointerException
     *             If the schemas parameter itself is null or any item in the
     *             array is null.
     * @throws llegalArgumentException
     *             If any item in the array is not recognized by this method.
     */
    public static IStatus validateAgainstXMLSchema(Source[] schemas,
            Source document) throws SAXException, IOException {
        // Lookup a factory for the W3C XML Schema language
        SchemaFactory factory =
                SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema"); //$NON-NLS-1$

        // Compile the schema.
        Schema schema = factory.newSchema(schemas);

        // Get a validator from the schema.
        Validator validator = schema.newValidator();

        String systemId = document.getSystemId();
        try {
            // Check the document
            validator.validate(document);
            return Status.OK_STATUS;
        } catch (SAXException e) {
            String message =
                    String.format(Messages.XMLUtil_invalidFile_message,
                            systemId,
                            e.getLocalizedMessage());
            return new Status(IStatus.ERROR, XpdResourcesPlugin.ID_PLUGIN,
                    message, e);
        }
    }
}
