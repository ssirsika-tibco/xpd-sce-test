/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.test.transform.rules_xsd;

import java.util.ArrayList;

import org.eclipse.core.resources.IFile;

import com.tibco.xpd.bom.test.transform.common.TransformationTest;
import com.tibco.xpd.bom.test.transform.util.TransformUtil;
import com.tibco.xpd.bom.validator.BOMValidatorActivator;

/**
 * Its a very base transformation test it only test for now if transformation
 * will run without exceptions. You can later test manually the result in the
 * junit-workspace.
 * <p>
 * <i>Created: 16 April 2009</i>
 * </p>
 * 
 * @author glewis
 */
@SuppressWarnings("nls")
public class BOMXSDRules02_CyclicDependencyRule extends TransformationTest {

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/bom-xsd-rules";

    protected String MODEL_FILE = "BOMXSDRules02CyclicModel.bom";

    protected String MODEL_FILE2 = "BOMXSDRules02CyclicModel2.bom";

    /*
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        timeStart = System.currentTimeMillis();

        modelFileNames = new ArrayList<String>();
        modelFiles = new ArrayList<IFile>();

        platformExampleFilesBase = PLATFORM_EXAMPLE_FILES_BASE;
        modelFileNames.add(MODEL_FILE);
        modelFileNames.add(MODEL_FILE2);

        super.setUp();

        System.out.println("Setup Duration = "
                + (System.currentTimeMillis() - timeStart) + "ms");
    }

    public void testTransformation() throws Exception {
        long time = System.currentTimeMillis();
        String[] destIds =
                new String[] { BOMValidatorActivator.VALIDATION_DEST_ID_BOMGENERIC };
        int count =
                TransformUtil.countMarkersMatchingPattern(destIds,
                        modelFiles.get(0),
                        ".*Cyclic Dependency.*");
        assertEquals("No BOM Generic Marker has been found for the known cyclic dependency error", 1, count); //$NON-NLS-1$

        count =
                TransformUtil.countMarkersMatchingPattern(destIds,
                        modelFiles.get(1),
                        ".*Cyclic Dependency.*");
        assertEquals("No BOM Generic Marker has been found for the 2 known cyclic dependency errors", 1, count); //$NON-NLS-1$

        System.out.println("Test Duration = "
                + (System.currentTimeMillis() - time) + "ms");
    }
}
