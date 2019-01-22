package com.tibco.bds.designtime.generator.test.validations;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;

import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.core.test.validations.AbstractBaseValidationTest;
import com.tibco.xpd.resources.util.ProjectUtil;

public abstract class AbstractBDSBaseValidationTest extends
        AbstractBaseValidationTest {

    /**
     * 
     */
    public AbstractBDSBaseValidationTest() {
        this(true);
    }

    public AbstractBDSBaseValidationTest(boolean isCheckProblemExists) {
        super(isCheckProblemExists);
    }

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#configureProject(org.eclipse.core.resources.IProject)
     * 
     * @param testProject
     */
    @Override
    protected void configureProject(IProject testProject) {
        super.configureProject(testProject);
        TestUtil.addGlobalDestinationToProject(getTestPlugInId(),
                "BPM", //$NON-NLS-1$
                testProject);
        try {
            ProjectUtil.addNature(testProject,
                    "com.tibco.xpd.bom.xsdtransform.xsdNature"); //$NON-NLS-1$

            ProjectUtil.addNature(testProject,
                    "com.tibco.xpd.wsdlgen.wsdlGenNature"); //$NON-NLS-1$

            ProjectUtil.addNature(testProject,
                    "com.tibco.xpd.wsdltobom.wsdlBomNature"); //$NON-NLS-1$
        } catch (CoreException e) {
            e.printStackTrace();
        }
    }
}
