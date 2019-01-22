/*
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */
package com.tibco.xpd.xsd.ui.wizards;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URLDecoder;

import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.impl.inst2xsd.Inst2Xsd;
import org.apache.xmlbeans.impl.inst2xsd.Inst2XsdOptions;
import org.apache.xmlbeans.impl.xb.xsdschema.SchemaDocument;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

import com.tibco.xpd.xsd.ui.internal.Messages;

/**
 * Wraps the Inst2Xsd tool from the Apache XmlBeans project.
 * 
 * @author tstephen
 */
public class Inst2XsdWrapper {

    private static final String ENCODING_UTF_8 = "UTF-8"; //$NON-NLS-1$

    public void createXsd(String inputFileName, IFile outputFile)
            throws IOException, CoreException {

        Inst2XsdOptions opts = new Inst2XsdOptions();
        // opts.set

        Reader[] readers = new Reader[1];
        FileReader reader = null;
        SchemaDocument[] schemaDocs = null;
        File file = new File(inputFileName);
        reader = new FileReader(file);
        readers[0] = reader;
        try {
            schemaDocs = Inst2Xsd.inst2xsd(readers, opts);
        } catch (XmlException e) {
            throw new IOException(String.format(
                    Messages.Inst2XsdWrapper_XmlParsingFailedMessage, e
                            .getLocalizedMessage()));
        }

        for (int i = 0; i < schemaDocs.length; i++) {
            SchemaDocument schema = schemaDocs[i];

            // if (opts.isVerbose())
            // System.out.println("----------------------\n\n" + schema);

            File f = new File(URLDecoder.decode(outputFile.getLocationURI()
                    .toURL().getFile(), ENCODING_UTF_8));

            // Although it is bad practise to do file operations on the Java
            // file rather than the IFile we have no choice since we are
            // reusing the XmlBeans tool, make sure to refresh
            schema.save(f, new XmlOptions().setSavePrettyPrint());
            outputFile.refreshLocal(IResource.DEPTH_ZERO, null);
        }

    }

    public void createXsd(IFile inputFile, File outputFile)
            throws IOException, CoreException {

        Inst2XsdOptions opts = new Inst2XsdOptions();
        // opts.set

        Reader[] readers = new Reader[1];
        InputStreamReader reader = null;
        SchemaDocument[] schemaDocs = null;
        InputStream inStream = inputFile.getContents();
        reader = new InputStreamReader(inStream);
        readers[0] = reader;
        try {
            schemaDocs = Inst2Xsd.inst2xsd(readers, opts);
        } catch (XmlException e) {
            throw new IOException(String.format(
                    Messages.Inst2XsdWrapper_XmlParsingFailedMessage, e
                            .getLocalizedMessage()));
        } finally {
            try {
                inStream.close();
            } catch (IOException e) {
                // Do nothing as closing stream
            }
        }

        for (int i = 0; i < schemaDocs.length; i++) {
            SchemaDocument schema = schemaDocs[i];

            // if (opts.isVerbose())
            // System.out.println("----------------------\n\n" + schema);

            File f = new File(URLDecoder.decode(outputFile.getAbsolutePath(),
                    ENCODING_UTF_8));

            // Although it is bad practise to do file operations on the Java
            // file rather than the IFile we have no choice since we are
            // reusing the XmlBeans tool, make sure to refresh
            schema.save(f, new XmlOptions().setSavePrettyPrint());
        }
    }
}
