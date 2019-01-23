/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.n2.test.validations;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;

import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;
import com.tibco.xpd.n2.test.core.validation.AbstractN2BaseValidationTest;
import com.tibco.xpd.resources.util.ProjectUtil;

/**
 * JUnit to make sure that the fix gone under XPD-6418 works fine i.e.,
 * multilevel generalisation is supported in case BOMs.
 * 
 * @author sajain
 * @since 15th Sep, 2014
 */
public class N2_33_GlobalDataMultilevelGeneralisationTest extends
        AbstractN2BaseValidationTest {

    public N2_33_GlobalDataMultilevelGeneralisationTest() {
        super(false);
    }

    /**
     * N2_33_GlobalDataMultilevelGeneralisationTest
     * 
     * @throws Exception
     */
    public void testN2_33_GlobalDataMultilevelGeneralisationTest()
            throws Exception {
        doTestValidations();
        return;
    }

    @Override
    protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos() {
        ValidationsTestProblemMarkerInfo[] markerInfos =
                new ValidationsTestProblemMarkerInfo[] {

                new ValidationsTestProblemMarkerInfo(
                        "/CRUDOperations_MultipleLevelParentChildClasess_ProcessProject01_Test15/Process Packages/CRUDOperations_MultipleLevelParentChildClasess_ProcessProject01_Test15.xpdl", //$NON-NLS-1$ 
                        "bpmn.dev.globalDataTask.addLinkOp.caseObjectRefType", //$NON-NLS-1$ 
                        "_X5AogPClEeOI1rYG7XxWKw", //$NON-NLS-1$ 
                        "BPMN : The Add Link(s) operation requires valid case object reference(s) of type 'Education' to be set (CRUDLinkUnlink_CertMastersPerson_ProcessProject01_Test15Process:Link01:LinkCertificates_Masters)", //$NON-NLS-1$ 
                        ""), //$NON-NLS-1$ 

                };
        return markerInfos;
    }

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#tearDown()
     * 
     * @throws Exception
     */
    @Override
    protected void tearDown() throws Exception {
        // TODO Auto-generated method stub
        // super.tearDown();
    }

    @Override
    protected String getTestName() {
        return "N2_33_GlobalDataMultilevelGeneralisationTest"; //$NON-NLS-1$
    }

    @Override
    protected String getTestPlugInId() {
        return "com.tibco.xpd.n2.test"; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#configureProject(org.eclipse.core.resources.IProject)
     * 
     * @param testProject
     */
    @Override
    protected void configureProject(IProject testProject) {

        super.configureProject(testProject);

        if (testProject
                .getName()
                .equals("CRUDOperations_MultipleLevelParentChildClasess_ProcessProject01_Test15")) { //$NON-NLS-1$

            IProject projectToREference =
                    getTestProject("CRUDOperations_MultipleLevelParentChildClasess_BDSGlobalDataProject01_Test15"); //$NON-NLS-1$

            try {
                ProjectUtil
                        .addReferenceProject(testProject, projectToREference);
            } catch (CoreException e) {

                e.printStackTrace();
            }
        }

    }

    @Override
    protected TestResourceInfo[] getTestResources() {
        TestResourceInfo[] testResources =
                new TestResourceInfo[] {
                        new TestResourceInfo(
                                "resources/N2_33_GlobalDataMultilevelGeneralisationTest", "CRUDOperations_MultipleLevelParentChildClasess_BDSGlobalDataProject01_Test15/Business Objects{bom}/CRUDOperations_MultipleLevelParentChildClasess_BDSGlobalDataProject01_Test15.bom"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/N2_33_GlobalDataMultilevelGeneralisationTest", "CRUDOperations_MultipleLevelParentChildClasess_ProcessProject01_Test15/Business Objects{bom}/CRUDOperations_MultipleLevelParentChildClasess_ProcessProject01_Test15.bom"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/N2_33_GlobalDataMultilevelGeneralisationTest", "CRUDOperations_MultipleLevelParentChildClasess_ProcessProject01_Test15/Process Packages{processes}/CRUDOperations_MultipleLevelParentChildClasess_ProcessProject01_Test15.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
                };

        return testResources;
    }

}
