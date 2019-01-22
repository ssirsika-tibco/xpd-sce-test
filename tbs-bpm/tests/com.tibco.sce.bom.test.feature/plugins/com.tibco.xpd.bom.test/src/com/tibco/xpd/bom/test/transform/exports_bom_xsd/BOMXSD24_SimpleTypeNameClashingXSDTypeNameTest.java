/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
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
 * XPD-5895: If the user defined primitive type is having same name as xsd base
 * type (for instance a Text primitive type with name as 'Name' which the
 * reserved word validation done by CDSEntityNameRule.validateClassifier doesn't
 * report as a problem. It checks for Java reserved words and JavaScript
 * reserved words) then the top level element added for this primitive type gets
 * the wrong base type set. It was trying to set the base xsd type (without any
 * prefix, which was not correct) instead of the user defined primitive type
 * 
 * @author bharge
 * @since 27 Mar 2014
 */
public class BOMXSD24_SimpleTypeNameClashingXSDTypeNameTest extends
        TransformationTest {

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/bom-xsd";

    protected String MODEL_FILE =
            "BOMXSD24_SimpleTypeNameClashingXSDTypeNameTest.bom";

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

        List<IStatus> result =
                exportBOMtoXSD(modelFiles.get(0), true, ResourcesPlugin
                        .getWorkspace().getRoot().getProject(testProjectName)
                        .getFullPath().append("XsdOutput"), true);
        assertEquals("There were errors on this transformation process (simulating an export from wizard).",
                0,
                getErrors(result).size());

    }

}
