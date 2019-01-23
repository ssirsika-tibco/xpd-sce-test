/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.test.transform.rules_xsd;

import java.util.ArrayList;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;

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
public class BOMXSDRules01_AssociationTypeRule extends TransformationTest {

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/bom-xsd-rules";

    protected String MODEL_FILE = "BOMXSDRules01AssociationTypeRule.bom";

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
        String[] destIds =
                new String[] { BOMValidatorActivator.VALIDATION_DEST_ID_XSD };
        int errorCount =
                TransformUtil.getMarkerSeverityCount(destIds,
                        modelFiles.get(0),
                        IMarker.SEVERITY_ERROR);
        assertEquals("There should be XSD Errors for this particular BOM file", 5, errorCount); //$NON-NLS-1$        
    }
}
