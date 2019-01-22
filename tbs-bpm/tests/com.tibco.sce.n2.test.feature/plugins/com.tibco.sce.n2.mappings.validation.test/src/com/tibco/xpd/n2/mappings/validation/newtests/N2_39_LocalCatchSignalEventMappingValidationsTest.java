/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.n2.mappings.validation.newtests;

import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;
import com.tibco.xpd.n2.test.core.validation.AbstractN2BaseValidationTest;

/**
 * Junit test to check that the regression fix under XPD-7225 and XPD-7214 works
 * fine. The Regression was that the model used to get changed on studio restart
 * and hence the mappings used to break. The validations below ensure that the
 * validations are raised whcih implies that the mappings are intact.
 * 
 * 
 * @author kthombar
 * @since Mar 13, 2015
 */
public class N2_39_LocalCatchSignalEventMappingValidationsTest extends
        AbstractN2BaseValidationTest {

    public N2_39_LocalCatchSignalEventMappingValidationsTest() {
        super(true);
    }

    /**
     * N2_39_LocalCatchSignalEventMappingValidationsTest
     * 
     * @throws Exception
     */
    public void testN2_39_LocalCatchSignalEventMappingValidationsTest()
            throws Exception {
        doTestValidations();
        return;
    }

    @Override
    protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos() {
        ValidationsTestProblemMarkerInfo[] markerInfos =
                new ValidationsTestProblemMarkerInfo[] {

                        new ValidationsTestProblemMarkerInfo(
                                "/LocalCatchSignalEventMappingValidations/Process Packages/LocalCatchSignalEventMappingValidations.xpdl", //$NON-NLS-1$ 
                                "abstractMappingRule.incompatibleTypes", //$NON-NLS-1$ 
                                "//@package/@processes.0/@activities.3/@event/@triggerResultSignal/@otherElements.0/@dataMappings.0", //$NON-NLS-1$ 
                                "Process Manager  : Map From Signal: The data types are incompatible for mapping 'Field' to 'Field2'. (LocalCatchSignalEventMappingValidationsProcess:CatchSignal1:Field2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/LocalCatchSignalEventMappingValidations/Process Packages/LocalCatchSignalEventMappingValidations.xpdl", //$NON-NLS-1$ 
                                "abstractMappingRule.incompatibleTypes", //$NON-NLS-1$ 
                                "//@package/@processes.0/@activities.3/@event/@triggerResultSignal/@otherElements.0/@dataMappings.1", //$NON-NLS-1$ 
                                "Process Manager  : Map From Signal: The data types are incompatible for mapping 'Field3' to 'Field4'. (LocalCatchSignalEventMappingValidationsProcess:CatchSignal1:Field4)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/LocalCatchSignalEventMappingValidations/Process Packages/LocalCatchSignalEventMappingValidations.xpdl", //$NON-NLS-1$ 
                                "abstractMappingRule.incompatibleTypes", //$NON-NLS-1$ 
                                "//@package/@processes.0/@activities.3/@event/@triggerResultSignal/@otherElements.0/@dataMappings.2", //$NON-NLS-1$ 
                                "Process Manager  : Map From Signal: The data types are incompatible for mapping 'Field4' to 'Field5'. (LocalCatchSignalEventMappingValidationsProcess:CatchSignal1:Field5)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/LocalCatchSignalEventMappingValidations/Process Packages/LocalCatchSignalEventMappingValidations.xpdl", //$NON-NLS-1$ 
                                "abstractMappingRule.incompatibleTypes", //$NON-NLS-1$ 
                                "//@package/@processes.0/@activities.3/@event/@triggerResultSignal/@otherElements.0/@dataMappings.3", //$NON-NLS-1$ 
                                "Process Manager  : Map From Signal: The data types are incompatible for mapping 'Field4' to 'Field'. (LocalCatchSignalEventMappingValidationsProcess:CatchSignal1:Field)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/LocalCatchSignalEventMappingValidations/Process Packages/LocalCatchSignalEventMappingValidations.xpdl", //$NON-NLS-1$ 
                                "abstractMappingRule.incompatibleTypes", //$NON-NLS-1$ 
                                "//@package/@processes.0/@activities.3/@event/@triggerResultSignal/@otherElements.0/@dataMappings.4", //$NON-NLS-1$ 
                                "Process Manager  : Map From Signal: The data types are incompatible for mapping 'Field5' to 'Field3'. (LocalCatchSignalEventMappingValidationsProcess:CatchSignal1:Field3)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                };
        return markerInfos;
    }

    @Override
    protected String getTestName() {
        return "N2_39_LocalCatchSignalEventMappingValidationsTest"; //$NON-NLS-1$
    }

    @Override
    protected String getTestPlugInId() {
        return "com.tibco.xpd.n2.mappings.validation.test"; //$NON-NLS-1$
    }

    @Override
    protected TestResourceInfo[] getTestResources() {
        TestResourceInfo[] testResources =
                new TestResourceInfo[] { new TestResourceInfo(
                        "resources/N240LocalCatchSignalEventMappingValidationsTest", "LocalCatchSignalEventMappingValidations/Process Packages{processes}/LocalCatchSignalEventMappingValidations.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
                };

        return testResources;
    }

}
