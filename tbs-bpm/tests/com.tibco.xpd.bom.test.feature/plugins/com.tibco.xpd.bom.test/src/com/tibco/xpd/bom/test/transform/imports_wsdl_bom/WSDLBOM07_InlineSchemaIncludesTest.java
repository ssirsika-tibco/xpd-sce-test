/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.test.transform.imports_wsdl_bom;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.openarchitectureware.workflow.issues.Issues;
import org.openarchitectureware.workflow.issues.IssuesImpl;

import com.tibco.xpd.bom.test.transform.common.TransformationTest;
import com.tibco.xpd.bom.test.transform.util.TransformUtil;
import com.tibco.xpd.bom.validator.BOMValidatorActivator;
import com.tibco.xpd.validation.ValidationActivator;
import com.tibco.xpd.validation.destinations.Destination;
import com.tibco.xpd.validation.explicit.Validator;
import com.tibco.xpd.validation.provider.IIssue;

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
public class WSDLBOM07_InlineSchemaIncludesTest extends TransformationTest {

    protected String TEST_FILE_NAME = "myTest.bom";

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl/migrated";

    protected String MODEL_FILE = "AmazonS3.wsdl";

    protected String DEPENDENCY_FILE = "Amazon/Inner/AmazonS3.xsd";

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
        modelFileNames.add(DEPENDENCY_FILE);

        super.setUp();
    }

    public void testTransformation() throws Exception {
        List<IStatus> statusArr =
                importWSDLtoBOM(new File(modelFiles.get(0).getLocationURI()),
                        outputSpecialFolder.getFolder().getFullPath()
                                .append(TEST_FILE_NAME));
        List<IStatus> errors = getErrors(statusArr);
        if (!errors.isEmpty()) {
            throw new Exception(errors.get(0).getMessage());
        }
        project.build(IncrementalProjectBuilder.FULL_BUILD,
                new NullProgressMonitor());
        Issues issues = new IssuesImpl();
        IFile file =
                TransformUtil.getOutputWSDLFile(issues,
                        modelFiles.get(0),
                        outputSpecialFolder.getFolder());
        Destination destination =
                ValidationActivator
                        .getDefault()
                        .getDestination(BOMValidatorActivator.VALIDATION_DEST_ID_BOMGENERIC);
        if (destination != null) {
            Validator validator = new Validator(destination);
            Collection<IIssue> issues2 = validator.validate(file);

            if (issues2 != null && !issues2.isEmpty()) {
                for (IIssue issue : issues2) {
                    if (issue.getSeverity() == IMarker.SEVERITY_ERROR) {
                        throw new Exception("" + issue.getMessage());
                    }
                }
            }
        }
    }
}
