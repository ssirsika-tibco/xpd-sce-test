/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.test.transform.exports_bom_xsd;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;

import com.tibco.xpd.bom.test.transform.common.TransformationTest;

/**
 * Its a very base transformation test it only test for now if transformation
 * will run without exceptions. You can later test manually the result in the
 * junit-workspace.
 * <p>
 * <i>Created: 06 Feb 2009</i>
 * </p>
 * 
 * @author glewis
 */
@SuppressWarnings("nls")
public class BOMXSD15_InvalidSchemaTransformationTest extends
        TransformationTest {

    protected String TEST_FILE_NAME = "myTest.xsd";

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/bom-xsd";

    protected String MODEL_FILE = "BOMXSD15InvalidSchema.bom";

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

    public void testTransformation() throws Exception {
        List<IStatus> result =
                exportBOMtoXSD(modelFiles.get(0), true, ResourcesPlugin
                        .getWorkspace().getRoot().getProject(testProjectName)
                        .getFullPath().append("XsdOutput"), true);

        Assert.assertNotSame("This should be error due to xsd validation", 0, result.size()); //$NON-NLS-1$LS

        Assert.assertNotSame("This should be error due to xsd validation", 0, getErrors(result).size()); //$NON-NLS-1$LS
    }
}
