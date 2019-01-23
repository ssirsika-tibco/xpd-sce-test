/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.test.transform;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;

import com.tibco.xpd.bom.test.transform.common.TransformationTest;
import com.tibco.xpd.bom.validator.BOMValidatorActivator;
import com.tibco.xpd.validation.ValidationActivator;
import com.tibco.xpd.validation.explicit.Validator;
import com.tibco.xpd.validation.provider.IIssue;

/**
 * Its a very base transformation test it only test for now if transformation
 * will run without exceptions. You can later test manually the result in the
 * junit-workspace.
 * <p>
 * <i>Created: 31 July 2008</i>
 * </p>
 * 
 * @author glewis
 */
@SuppressWarnings("nls")
public class XSDToBOMMultipleCombinationsTransformationTest extends
        TransformationTest {

    protected static String TEST_FILE_NAME = "myTest.bom";

    private static final String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/xsd";

    private static final String MODEL_FILE1 =
            "XSDToBOMMultipleCombinationsTransformationTest.xsd";

    private static final String MODEL_FILE2 = "includedModel.xsd";

    private static final String MODEL_FILE3 = "gary.xsd";

    private static final String MODEL_FILE4 = "doc1.xsd";

    private static final String MODEL_FILE5 = "doc2.xsd";

    private static final String MODEL_FILE6 = "doc3.xsd";

    private static final String MODEL_FILE7 = "doc4.xsd";

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
        modelFileNames.add(MODEL_FILE1);
        modelFileNames.add(MODEL_FILE2);
        modelFileNames.add(MODEL_FILE3);
        modelFileNames.add(MODEL_FILE4);
        modelFileNames.add(MODEL_FILE5);
        modelFileNames.add(MODEL_FILE6);
        modelFileNames.add(MODEL_FILE7);

        super.setUp();
    }

    public void testTransformation() throws Exception {
        List<IStatus> result =
                importXSDtoBOM(new File(modelFiles.get(0).getLocationURI()),
                        ResourcesPlugin.getWorkspace().getRoot()
                                .getProject(testProjectName).getFullPath()
                                .append(TEST_FILE_NAME));
        List<IStatus> errors = getErrors(result);
        if (!errors.isEmpty()) {
            throw new Exception(errors.get(0).getMessage());
        }
        project.build(IncrementalProjectBuilder.FULL_BUILD,
                new NullProgressMonitor());
        String[] destIds =
                new String[] { BOMValidatorActivator.VALIDATION_DEST_ID_BOMGENERIC };
        Collection<com.tibco.xpd.validation.destinations.Destination> destinations =
                new ArrayList<com.tibco.xpd.validation.destinations.Destination>();

        for (String destId : destIds) {
            com.tibco.xpd.validation.destinations.Destination destination =
                    ValidationActivator.getDefault().getDestination(destId);
            if (destination != null) {
                destinations.add(destination);
            }
        }
        Validator validator = new Validator(destinations);
        Collection<IIssue> issues2 =
                validator.validate(project.getFile(TEST_FILE_NAME));
        int count = 0;
        for (IIssue issue : issues2) {
            int markerSeverity = issue.getSeverity();
            if (markerSeverity == IMarker.SEVERITY_ERROR) {
                count++;
            }
        }
        assertEquals("BOM Generic Markers have been found for the imported BOM objects", 0, count); //$NON-NLS-1$
    }
}
