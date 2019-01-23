/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.test.transform.imports_wsdl_bom;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import junit.framework.Assert;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Property;
import org.openarchitectureware.workflow.issues.Issues;
import org.openarchitectureware.workflow.issues.IssuesImpl;

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
 * <i>Created: 22 Mar 2010</i>
 * </p>
 * 
 * @author glewis
 */
@SuppressWarnings("nls")
public class WSDLBOM10_CrossReferencedInlineSchemasTest extends
        TransformationTest {

    protected String TEST_FILE_NAME = "myTest.bom";

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl";

    protected String MODEL_FILE = "WSDLBOM10_InlineCrossSchema.wsdl";

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
        Assert.assertNotNull(statusArr);

        Assert.assertEquals(0, getErrors(statusArr).size());

        IFile schema2 = outputSpecialFolder.getFolder().getFile("schema2.bom");
        assertTrue("Cannot find newly exported BOM file", schema2.exists()); //$NON-NLS-1$
        WorkingCopy schema2wc =
                XpdResourcesPlugin.getDefault().getWorkingCopy(schema2);
        assertNotNull("Cannot create WorkingCopy for newly exported BOM file", schema2wc); //$NON-NLS-1$
        assertTrue("Root element is null or not of type Model", schema2wc.getRootElement() instanceof Model); //$NON-NLS-1$
        Model schema2Model = (Model) schema2wc.getRootElement();

        IFile schema1 = outputSpecialFolder.getFolder().getFile("schema1.bom");
        assertTrue("Cannot find newly exported BOM file", schema1.exists()); //$NON-NLS-1$
        WorkingCopy schema1wc =
                XpdResourcesPlugin.getDefault().getWorkingCopy(schema1);
        assertNotNull("Cannot create WorkingCopy for newly exported BOM file", schema1wc); //$NON-NLS-1$
        assertTrue("Root element is null or not of type Model", schema1wc.getRootElement() instanceof Model); //$NON-NLS-1$
        Model schema1Model = (Model) schema1wc.getRootElement();

        Class class2 =
                TransformUtil.assertPackagedElementClass(schema2Model
                        .getPackagedElements(), "Class2");

        Class class1 =
                TransformUtil.assertPackagedElementClass(schema1Model
                        .getPackagedElements(), "Class1");

        Property attribute =
                TransformUtil.assertAttributeInClass(class1.getAllAttributes(),
                        "myClass2",
                        null);

        Assert.assertNotNull(attribute.getType());

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
