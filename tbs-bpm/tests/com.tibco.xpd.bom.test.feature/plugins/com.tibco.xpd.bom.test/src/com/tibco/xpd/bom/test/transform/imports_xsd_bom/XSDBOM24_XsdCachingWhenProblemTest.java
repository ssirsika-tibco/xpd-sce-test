/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.test.transform.imports_xsd_bom;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import com.tibco.xpd.bom.test.transform.common.TransformationTestRoundtrip;

/**
 * Checks for problem with incorrect caching of a result after XSD import
 * failure. After importing a XSD with unsupported construct (for example:
 * attribute "mixed"), next time if user imports the same file containing
 * corrected XSD import fails returning previous error(s).
 * <p/>
 * <b>Note:</b> This is not a round-trip test as it is not testing XSD-BOM-XSD
 * conversion but the import process itself.
 * <p>
 * <i>Created: 22 July 2010</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 */
@SuppressWarnings("nls")
public class XSDBOM24_XsdCachingWhenProblemTest extends
        TransformationTestRoundtrip {

    /** BOM file name - results of import. */
    protected String TEST_FILE_NAME = "myTest.bom";

    /** Base for resources. */
    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/xsd";

    /** Destination file name. */
    protected String MODEL_FILE = "XSDBOM24_XsdCachingWhenProblemTest.xsd";

    /** Source file name with incorrect XSD containing unsupported construct. */
    protected String MODEL_FILE_BAD =
            "XSDBOM24_BAD_XsdCachingWhenProblemTest.xsd";

    /** Source file name with correct XSD. */
    protected String MODEL_FILE_GOOD =
            "XSDBOM24_GOOD_XsdCachingWhenProblemTest.xsd";

    protected String PLATFORM_WIZARD_GOLD_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/xsd/gold/wizard";

    protected String PLATFORM_BUILDER_GOLD_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/xsd/gold/builder";

    /*
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        modelFileNames = new ArrayList<String>();
        modelFiles = new ArrayList<IFile>();

        platformExampleFilesBase = PLATFORM_EXAMPLE_FILES_BASE;
        platformWizardGoldFilesBase = PLATFORM_WIZARD_GOLD_FILES_BASE;
        platformBuilderGoldFilesBase = PLATFORM_BUILDER_GOLD_FILES_BASE;

        super.setUp();
    }

    @Override
    public void testTransformation() throws Exception {

        IPath outputBOMPath =
                outputSpecialFolder.getFolder().getFullPath()
                        .append(TEST_FILE_NAME);

        // Copy bad XSD first to the workspace and try to do import.
        IFile badImportFile = copyResource(MODEL_FILE_BAD, MODEL_FILE);

        List<IStatus> resultFromBad =
                importXSDtoBOM(new File(badImportFile.getLocationURI()),
                        outputBOMPath);

        List<IStatus> errorsFromBad = getErrors(resultFromBad);
        if (!errorsFromBad.isEmpty()) {
            fail("Error was not expected. Mixed attr. is supported.");
        }

        // Now copy good XSD to the workspace under the same name and try to
        // import it again.
        IFile goodImportFile = copyResource(MODEL_FILE_GOOD, MODEL_FILE);
        List<IStatus> resultFromGood =
                importXSDtoBOM(new File(goodImportFile.getLocationURI()),
                        outputBOMPath);

        List<IStatus> errorsFromGood = getErrors(resultFromGood);
        if (!errorsFromGood.isEmpty()) {
            fail("Errors were not expected.");
        }
    }

    /**
     * Copies resource (and overwrite) form plug-in to the test project and
     * returns destination file.
     */
    private IFile copyResource(String srcFileName, String destFileName)
            throws IOException, CoreException {
        long start = System.currentTimeMillis();

        IFile destFile = outputSpecialFolder.getFolder().getFile(destFileName);
        InputStream modelInputStream =
                new ResourceSetImpl()
                        .getURIConverter()
                        .createInputStream(URI.createURI(platformExampleFilesBase
                                + '/' + srcFileName));
        if (destFile.exists()) {
            destFile.setContents(modelInputStream, true, true, null);
        } else {
            destFile.create(modelInputStream, true, null);
        }
        destFile.refreshLocal(IResource.DEPTH_ZERO, new NullProgressMonitor());

        modelInputStream.close();
        long duration = System.currentTimeMillis() - start;
        System.out.println("Setup copy of " + srcFileName + " to "
                + destFileName + " = " + duration + "ms");
        return destFile;
    }

}
