/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.bom.test.transform.imports_wsdl_bom;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import com.tibco.xpd.bom.test.transform.common.TransformationTest;

/**
 * Validates the import of WSDLs that contain a reference to an invalid XSD.
 * This tests the following references:
 * <ul>
 * <li>relative reference downstream</li>
 * <li>relative reference upstream</li>
 * <li>absolute reference</li>
 * </ul>
 * 
 * @author njpatel
 * 
 */
public class WSDLBOM18_InvalidImportTest extends TransformationTest {

    private static final String TEMP_PATH = "c:/temp";

    private static final String EXTERNAL_PATH = TEMP_PATH + "/WSDLBOM18";

    private static final String WSDL_DOWNSTREAM =
            "WSDLBOM18_InvalidImportTest_downstreamRelativeRef.wsdl";

    private static final String WSDL_UPSTREAM =
            "WSDLBOM18_InvalidImportTest_upstreamRelativeRef.wsdl";

    private static final String WSDL_ABSOLUTE =
            "WSDLBOM18_InvalidImportTest_absoluteRef.wsdl";

    private static final String XSD_MASTER =
            "WSDLBOM18_InvalidImportTest_AirlineUserProfileL.xsd";

    private static final String XSD_NAME = "AirlineUserProfileL.xsd";

    private static final String DOWNSTREAM_RELATIVE_PATH = "profile";

    @Override
    protected void setUp() throws Exception {
        modelFileNames = new ArrayList<String>();
        modelFiles = new ArrayList<IFile>();

        File tempFolder =
                new File(EXTERNAL_PATH + "/" + DOWNSTREAM_RELATIVE_PATH);
        if (!tempFolder.exists()) {
            tempFolder.mkdirs();
        }

        super.setUp();
    }

    /**
     * Test the wsdl import with a downstream relative reference to a schema.
     * 
     * @throws Exception
     */
    public void testDownStreamRelativePath() throws Exception {
        /*
         * Copy the test files to the file system
         */
        // Copy the downstream relative reference test wsdl
        String outputPath = EXTERNAL_PATH + "/" + WSDL_DOWNSTREAM;
        File outputFile =
                copy(platformExampleFilesBase + "/wsdl/" + WSDL_DOWNSTREAM,
                        outputPath);

        assertTrue("Failed to create '" + outputPath + "'", outputFile != null
                && outputFile.exists());

        // Copy xsd to profile folder
        outputPath =
                EXTERNAL_PATH + "/" + DOWNSTREAM_RELATIVE_PATH + "/" + XSD_NAME;
        File xsdFile =
                copy(platformExampleFilesBase + "/wsdl/" + XSD_MASTER,
                        outputPath);
        assertTrue("Failed to create '" + outputPath + "'", xsdFile != null
                && xsdFile.exists());

        // Run the transformation
        List<IStatus> result =
                importWSDLtoBOM(outputFile, outputSpecialFolder.getFolder()
                        .getFullPath().append("bom1.bom"));
        checkError(result);
    }

    /**
     * Test the wsdl import with an upstream relative reference to a schema.
     * 
     * @throws Exception
     */
    public void testUpStreamRelativePath() throws Exception {
        /*
         * Copy the test files to the file system
         */
        // Copy the upstream relative reference test wsdl
        String outputPath = EXTERNAL_PATH + "/" + WSDL_UPSTREAM;
        File outputFile =
                copy(platformExampleFilesBase + "/wsdl/" + WSDL_UPSTREAM,
                        outputPath);

        assertTrue("Failed to create '" + outputPath + "'", outputFile != null
                && outputFile.exists());

        // Copy xsd to temp folder
        outputPath = TEMP_PATH + "/" + XSD_NAME;
        File xsdFile =
                copy(platformExampleFilesBase + "/wsdl/" + XSD_MASTER,
                        outputPath);
        assertTrue("Failed to create '" + outputPath + "'", xsdFile != null
                && xsdFile.exists());

        // Run the transformation
        List<IStatus> result =
                importWSDLtoBOM(outputFile, outputSpecialFolder.getFolder()
                        .getFullPath().append("bom2.bom"));
        checkError(result);
    }

    /**
     * Test the wsdl import with an absolute reference to a schema.
     * 
     * @throws Exception
     */
    public void testAbsolutePath() throws Exception {
        // Copy the absolute reference test wsdl
        String outputPath = EXTERNAL_PATH + "/" + WSDL_ABSOLUTE;
        File outputFile =
                copy(platformExampleFilesBase + "/wsdl/" + WSDL_ABSOLUTE,
                        outputPath);

        assertTrue("Failed to create '" + outputPath + "'", outputFile != null
                && outputFile.exists());

        // Copy xsd to profile folder
        outputPath =
                EXTERNAL_PATH + "/" + DOWNSTREAM_RELATIVE_PATH + "/" + XSD_NAME;
        File xsdFile =
                copy(platformExampleFilesBase + "/wsdl/" + XSD_MASTER,
                        outputPath);
        assertTrue("Failed to create '" + outputPath + "'", xsdFile != null
                && xsdFile.exists());

        List<IStatus> result =
                importWSDLtoBOM(outputFile, outputSpecialFolder.getFolder()
                        .getFullPath().append("bom2.bom"));
        checkError(result);
    }

    /**
     * Check the error result.
     * 
     * @param result
     */
    private void checkError(List<IStatus> result) {
        /*
         * After XPD-6062, we also validated the imported wsdl/xsd so we should
         * now get validation errors as well
         */
        assertEquals("Number of errors from the import", 2, result.size());
        IStatus status = result.get(0);
        assertEquals("Status error level", IStatus.ERROR, status.getSeverity());
        assertTrue("Expected the error message to start with 'Referenced file contains errors' (got: '"
                + status.getMessage() + "')",
                status.getMessage()
                        .startsWith("Referenced file contains errors"));

        status = result.get(1);
        assertEquals("Status error level", IStatus.ERROR, status.getSeverity());
        assertTrue("Expected the error message to start with 'Referenced file contains errors' (got: '"
                + status.getMessage() + "')",
                status.getMessage()
                        .startsWith("Referenced file contains errors"));
    }

    /**
     * Copy the file from the given from (contained in plugin) path to the to
     * path.
     * 
     * @param fromPath
     * @param toPath
     * @throws IOException
     */
    private File copy(String fromPath, String toPath) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {

            is =
                    new ResourceSetImpl().getURIConverter()
                            .createInputStream(URI.createURI(fromPath));

            File outputFile = new File(toPath);
            if (outputFile.exists()) {
                outputFile.delete();
            }

            os = new FileOutputStream(outputFile);

            copy(is, os);

            return outputFile;

        } finally {
            if (is != null) {
                is.close();
            }
            if (os != null) {
                os.close();
            }
        }
    }

    /**
     * Copy between the streams.
     * 
     * @param from
     * @param to
     * @throws IOException
     */
    private void copy(InputStream from, OutputStream to) throws IOException {
        try {
            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = from.read(buffer)) != -1)
                to.write(buffer, 0, bytesRead); // write
        } finally {
            if (from != null)
                try {
                    from.close();
                } catch (IOException e) {
                    ;
                }
            if (to != null)
                try {
                    to.close();
                } catch (IOException e) {
                    ;
                }
        }
    }
}
