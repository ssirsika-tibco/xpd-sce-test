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
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.uml2.uml.Model;
import org.openarchitectureware.workflow.issues.Issues;
import org.openarchitectureware.workflow.issues.IssuesImpl;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.test.transform.common.TransformationTest;
import com.tibco.xpd.bom.test.transform.util.TransformUtil;
import com.tibco.xpd.bom.validator.BOMValidatorActivator;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
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
public class WSDLBOM15_DifferentInlineTargetNamespaceSchemasTest extends
        TransformationTest {

    protected String TEST_FILE_NAME = "myTest.bom";

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl";

    protected String MODEL_FILE = "ObjectService.wsdl";

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

        IResource[] members = outputSpecialFolder.getFolder().members();
        for (IResource member : members) {

            if (member instanceof IFile
                    && BOMResourcesPlugin.BOM_FILE_EXTENSION.equals(member
                            .getFileExtension())) {

                IFile generatedBOMFile = (IFile) member;
                WorkingCopy wc =
                        XpdResourcesPlugin.getDefault()
                                .getWorkingCopy(generatedBOMFile);
                assertNotNull("Cannot create WorkingCopy for newly exported BOM file", wc); //$NON-NLS-1$
                assertTrue("Root element is null or not of type Model", wc.getRootElement() instanceof Model); //$NON-NLS-1$

                Model model = (Model) wc.getRootElement();
                if (generatedBOMFile.getName()
                        .equals("org.open.oasis.docs.ns.cmis.core._200901.bom")) {

                    assertEquals("Unexpected number of packaged elements",
                            128,
                            model.getPackagedElements().size());

                } else if (generatedBOMFile
                        .getName()
                        .equals("org.open.oasis.docs.ns.cmis.messaging._200901.bom")) {

                    assertEquals("Unexpected number of packaged elements",
                            91,
                            model.getPackagedElements().size());

                } else if (generatedBOMFile.getName()
                        .equals("org.open.oasis.docs.ns.cmis.ws._200901.bom")) {

                    assertEquals("Unexpected number of packaged elements",
                            1,
                            model.getPackagedElements().size());

                } else {

                    assertFalse("Unexpected BOM Found", true);
                }
            }
        }
    }
}
