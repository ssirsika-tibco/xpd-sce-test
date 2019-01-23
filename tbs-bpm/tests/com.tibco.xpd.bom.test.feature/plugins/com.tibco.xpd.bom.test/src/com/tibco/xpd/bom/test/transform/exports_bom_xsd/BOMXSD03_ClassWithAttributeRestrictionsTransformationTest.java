/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.test.transform.exports_bom_xsd;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;

import com.tibco.xpd.bom.test.transform.common.TransformationTest;
import com.tibco.xpd.bom.test.transform.util.TransformUtil;
import com.tibco.xpd.bom.validator.BOMValidatorActivator;

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
public class BOMXSD03_ClassWithAttributeRestrictionsTransformationTest extends
        TransformationTest {

    protected String TEST_FILE_NAME = "myTest.xsd";

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/bom-xsd";

    protected String MODEL_FILE = "BOMXSD03ClassWithAttributeRestrictions.bom";

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
                new String[] { BOMValidatorActivator.VALIDATION_DEST_ID_XSD,
                        BOMValidatorActivator.VALIDATION_DEST_ID_BOMGENERIC };
        boolean hasErrorMarker =
                TransformUtil.isMarkerSeverityExist(destIds,
                        modelFiles.get(0),
                        IMarker.SEVERITY_ERROR);
        assertEquals("There are XSD or BOM GENERIC errors on the BOM",
                false,
                hasErrorMarker);

        destIds = new String[] { BOMValidatorActivator.VALIDATION_DEST_ID_CDS };
        hasErrorMarker =
                TransformUtil.isMarkerSeverityExist(destIds,
                        modelFiles.get(0),
                        IMarker.SEVERITY_ERROR);
        assertEquals("There are CDS errors on the BOM", false, hasErrorMarker);

        /*
         * Sid XPD-5118 No longer any need for two separate tests (one with
         * ignoreStereoTypes OFF and one with ignoreStereoTypes OFF) because
         * that functionality NO LONGER EXISTS in the underlying
         * BOM2XSDTransformer. (see exportBOMtoXSD() for more info.
         * 
         * this test because there's no such thing as ignoreStereoTypes for
         * BOM2XSD transform any more.
         */
        List<IStatus> result =
                exportBOMtoXSD(modelFiles.get(0), true, ResourcesPlugin
                        .getWorkspace().getRoot().getProject(testProjectName)
                        .getFullPath().append("XsdOutput"), true);
        assertEquals("There were errors on this transformation process (simulating an export from wizard).",
                0,
                getErrors(result).size());

    }

}
