/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.n2.test.validations;

import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.validations.AbstractBaseValidationTest;
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;

/**
 * Junit test to check if the fix in XPD-6151 works fine. Checks if warning
 * markers are raised if the Global Data service task Single instance case ref
 * field is not selected for deletion of case class.
 * 
 * 
 * @author kthombar
 * @since 23-Jul-2014
 */

public class N2_29_GlobalDataServiceTaskSingleInstanceCaseRefFieldTest extends
        AbstractBaseValidationTest {

    public N2_29_GlobalDataServiceTaskSingleInstanceCaseRefFieldTest() {
        super(true);
    }

    /**
     * TestNameTest
     * 
     * @throws Exception
     */
    public void testTestNameTest() throws Exception {
        doTestValidations();
        return;
    }

    @Override
    protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos() {
        ValidationsTestProblemMarkerInfo[] markerInfos =
                new ValidationsTestProblemMarkerInfo[] {

                        new ValidationsTestProblemMarkerInfo(
                                "/developerProject/Process Packages/developerProject.xpdl", //$NON-NLS-1$ 
                                "bx.useDeleteByCaseRefField", //$NON-NLS-1$ 
                                "_fLSToBIgEeSP3bPS8J45rQ", //$NON-NLS-1$ 
                                "Process Manager  : It is recommended that you use delete by non-array case reference field as this will ensure that the case object is not in use by other process instances (which can cause those instances to halt). (developerProjectProcess:ServiceTask3)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/developerProject/Process Packages/developerProject.xpdl", //$NON-NLS-1$ 
                                "bx.useDeleteByCaseRefField", //$NON-NLS-1$ 
                                "_moXJMBIgEeSP3bPS8J45rQ", //$NON-NLS-1$ 
                                "Process Manager  : It is recommended that you use delete by non-array case reference field as this will ensure that the case object is not in use by other process instances (which can cause those instances to halt). (developerProjectProcess:ServiceTask4)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/developerProject/Process Packages/developerProject.xpdl", //$NON-NLS-1$ 
                                "bx.useDeleteByCaseRefField", //$NON-NLS-1$ 
                                "_YHVIsBIgEeSP3bPS8J45rQ", //$NON-NLS-1$ 
                                "Process Manager  : It is recommended that you use delete by non-array case reference field as this will ensure that the case object is not in use by other process instances (which can cause those instances to halt). (developerProjectProcess:ServiceTask2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                };
        return markerInfos;
    }

    @Override
    protected String getTestName() {
        return "N2_29_GlobalDataServiceTaskSingleInstanceCaseRefFieldTest"; //$NON-NLS-1$
    }

    @Override
    protected String getTestPlugInId() {
        return "com.tibco.xpd.n2.test"; //$NON-NLS-1$
    }

    @Override
    protected TestResourceInfo[] getTestResources() {
        TestResourceInfo[] testResources =
                new TestResourceInfo[] {
                        new TestResourceInfo(
                                "resources/N2_29_GlobalDataServiceTaskSingleInstanceCaseRefFieldTest", "dataProject/Business Objects{bom}/dataProject.bom"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/N2_29_GlobalDataServiceTaskSingleInstanceCaseRefFieldTest", "developerProject/Process Packages{processes}/developerProject.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
                };

        return testResources;
    }

}
