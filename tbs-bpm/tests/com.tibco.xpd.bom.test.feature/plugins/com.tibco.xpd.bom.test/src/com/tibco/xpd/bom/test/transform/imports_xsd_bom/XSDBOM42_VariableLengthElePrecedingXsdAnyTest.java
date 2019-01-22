/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.bom.test.transform.imports_xsd_bom;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.URI;
import org.eclipse.xsd.XSDSchema;
import org.openarchitectureware.xsd.builder.OawXSDResource;

import com.tibco.xpd.bom.test.transform.common.TransformationTestRoundtrip;
import com.tibco.xpd.bom.xsdtransform.api.XSDUtil;

/**
 * 
 * Test to check if xsd:any is preceded by variable length element.Check if
 * problem marker is raised. If yes then pass the test, if no then fail it.
 * 
 * @author kthombar
 * @since 22-Aug-2013
 */
public class XSDBOM42_VariableLengthElePrecedingXsdAnyTest extends
        TransformationTestRoundtrip {

    protected String TEST_FILE_NAME =
            "XSDBOM42_VariableLengthElePrecedingXsdAnyTest";

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/xsd";

    // The name of the XML Schema that is being tested
    protected String MODEL_FILE =
            "XSDBOM42_VariableLengthElePrecedingXsdAnyTest.xsd";

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

        modelFileNames.add(MODEL_FILE);

        super.setUp();
    }

    @Override
    public void testTransformation() throws Exception {
        IFile modelFile = outputSpecialFolder.getFolder().getFile(MODEL_FILE);
        OawXSDResource resource =
                new OawXSDResource(URI.createURI(modelFile.getFullPath()
                        .toPortableString()));
        resource.load(Collections.EMPTY_MAP);
        XSDSchema schema = resource.getSchema();
        TEST_FILE_NAME =
                XSDUtil.getJavaPackageNameFromNamespaceURI(modelFile
                        .getProject(), schema.getTargetNamespace())
                        + ".bom"; //$NON-NLS-1$

        IPath outputBOMPath = getBomIPath();
        List<IStatus> result =
                importXSDtoBOM(new File(modelFiles.get(0).getLocationURI()),
                        outputBOMPath);
        List<IStatus> errors = getErrors(result);
        if (errors.isEmpty()) {
            assertFalse("This test was supposed to fail as xsd:any cannot be preceded by variable length element.",
                    true);
        }

    }

    private IPath getBomIPath() {
        IPath outputBOMPath =
                ResourcesPlugin.getWorkspace().getRoot()
                        .getProject(testProjectName).getFullPath()
                        .append(TEST_FILE_NAME);
        return outputBOMPath;
    }
}
