/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.n2.mappings.validation.newtests;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;

import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;
import com.tibco.xpd.n2.test.core.validation.AbstractN2BaseValidationTest;
import com.tibco.xpd.resources.util.ProjectUtil;

/**
 * Junit test that checks that validations are raised if we try to Map Multi to
 * Signal Instance and vice versa in global signal events.
 * 
 * 
 * @author kthombar
 * @since Mar 23, 2015
 */
public class N2_40_GlobalSignalReferencingMappingValidationTest extends
        AbstractN2BaseValidationTest {

    public N2_40_GlobalSignalReferencingMappingValidationTest() {
        super(true);
    }

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#configureProject(org.eclipse.core.resources.IProject)
     * 
     * @param testProject
     */
    @Override
    protected void configureProject(IProject testProject) {

        if (testProject.getName().equals("GlobalSignalTest")) { //$NON-NLS-1$          
            /*
             * Add reference to Global Signal Project.
             */
            IProject projectToREference = getTestProject("Signals"); //$NON-NLS-1$

            try {
                ProjectUtil
                        .addReferenceProject(testProject, projectToREference);
            } catch (CoreException e) {
                e.printStackTrace();
            }
        } else if (testProject.getName().equals("Signals")) { //$NON-NLS-1$

            IProject projectToREference = getTestProject("test"); //$NON-NLS-1$
            /*
             * Add reference to BOM Project.
             */
            try {
                ProjectUtil
                        .addReferenceProject(testProject, projectToREference);
            } catch (CoreException e) {
                e.printStackTrace();
            }
            /*
             * Set the Id of the Global Signal Project because the Throw & Catch
             * Global Signals reference the Global Signal Definition Project by
             * project Id.
             */
            TestUtil.addGlobalDestinationToProject("com.example.signals", //$NON-NLS-1$
                    "BPM", //$NON-NLS-1$
                    testProject);

            return;
        }

        super.configureProject(testProject);
    }

    /**
     * N2_40_GlobalSignalReferencingMappingValidationTest
     * 
     * @throws Exception
     */
    public void testN2_40_GlobalSignalReferencingMappingValidationTest()
            throws Exception {
        doTestValidations();
        return;
    }

    @Override
    protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos() {
        ValidationsTestProblemMarkerInfo[] markerInfos =
                new ValidationsTestProblemMarkerInfo[] {

                        new ValidationsTestProblemMarkerInfo(
                                "/GlobalSignalTest/Process Packages/GlobalSignalTest.xpdl", //$NON-NLS-1$ 
                                "abstractMappingRule.singleToMultiUnsupported", //$NON-NLS-1$ 
                                "//@package/@processes.0/@activities.2/@event/@triggerResultSignal/@otherElements.0/@dataMappings.10", //$NON-NLS-1$ 
                                "Process Manager  : 'Map To Signal' Global Signal Mapping: Single to multiple instance data mapping is not supported for 'Field' to 'PayloadData_array2'. (GlobalSignalThrow:ThrowGlobalGlobalSignal:PayloadData_array2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/GlobalSignalTest/Process Packages/GlobalSignalTest.xpdl", //$NON-NLS-1$ 
                                "abstractMappingRule.singleToMultiUnsupported", //$NON-NLS-1$ 
                                "//@package/@processes.0/@activities.2/@event/@triggerResultSignal/@otherElements.0/@dataMappings.11", //$NON-NLS-1$ 
                                "Process Manager  : 'Map To Signal' Global Signal Mapping: Single to multiple instance data mapping is not supported for 'Script1' to 'PayloadData_array'. (GlobalSignalThrow:ThrowGlobalGlobalSignal:PayloadData_array)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/GlobalSignalTest/Process Packages/GlobalSignalTest.xpdl", //$NON-NLS-1$ 
                                "abstractMappingRule.singleToMultiUnsupported", //$NON-NLS-1$ 
                                "//@package/@processes.0/@activities.2/@event/@triggerResultSignal/@otherElements.0/@dataMappings.9", //$NON-NLS-1$ 
                                "Process Manager  : 'Map To Signal' Global Signal Mapping: Single to multiple instance data mapping is not supported for 'Field9' to 'PayloadData_array10'. (GlobalSignalThrow:ThrowGlobalGlobalSignal:PayloadData_array10)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/GlobalSignalTest/Process Packages/GlobalSignalTest.xpdl", //$NON-NLS-1$ 
                                "abstractMappingRule.multiToSingleUnsupported", //$NON-NLS-1$ 
                                "//@package/@processes.1/@activitySets.0/@activities.0/@event/@triggerResultSignal/@otherElements.0/@dataMappings.1", //$NON-NLS-1$ 
                                "Process Manager  : 'Map From Signal' Global Signal Mapping: Multiple to single instance data mapping is not supported for 'PayloadData_array10' to 'Field9'. (GlobalSignalCatch:EventSubProcess:CatchGlobalGlobalSignal2:Field9)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/GlobalSignalTest/Process Packages/GlobalSignalTest.xpdl", //$NON-NLS-1$ 
                                "abstractMappingRule.singleToMultiUnsupported", //$NON-NLS-1$ 
                                "//@package/@processes.1/@activitySets.0/@activities.0/@event/@triggerResultSignal/@otherElements.0/@dataMappings.2", //$NON-NLS-1$ 
                                "Process Manager  : 'Map From Signal' Global Signal Mapping: Single to multiple instance data mapping is not supported for 'PayloadData' to 'Field10'. (GlobalSignalCatch:EventSubProcess:CatchGlobalGlobalSignal2:Field10)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/GlobalSignalTest/Process Packages/GlobalSignalTest.xpdl", //$NON-NLS-1$ 
                                "abstractMappingRule.multiToSingleUnsupported", //$NON-NLS-1$ 
                                "//@package/@processes.1/@activitySets.0/@activities.0/@event/@triggerResultSignal/@otherElements.0/@dataMappings.3", //$NON-NLS-1$ 
                                "Process Manager  : 'Map From Signal' Global Signal Mapping: Multiple to single instance data mapping is not supported for 'PayloadData_array6' to 'Field5'. (GlobalSignalCatch:EventSubProcess:CatchGlobalGlobalSignal2:Field5)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/GlobalSignalTest/Process Packages/GlobalSignalTest.xpdl", //$NON-NLS-1$ 
                                "abstractMappingRule.multiToSingleUnsupported", //$NON-NLS-1$ 
                                "//@package/@processes.1/@activitySets.0/@activities.0/@event/@triggerResultSignal/@otherElements.0/@dataMappings.4", //$NON-NLS-1$ 
                                "Process Manager  : 'Map From Signal' Global Signal Mapping: Multiple to single instance data mapping is not supported for 'PayloadData_array9' to 'Field8'. (GlobalSignalCatch:EventSubProcess:CatchGlobalGlobalSignal2:Field8)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/GlobalSignalTest/Process Packages/GlobalSignalTest.xpdl", //$NON-NLS-1$ 
                                "abstractMappingRule.multiToSingleUnsupported", //$NON-NLS-1$ 
                                "//@package/@processes.1/@activitySets.0/@activities.0/@event/@triggerResultSignal/@otherElements.0/@dataMappings.5", //$NON-NLS-1$ 
                                "Process Manager  : 'Map From Signal' Global Signal Mapping: Multiple to single instance data mapping is not supported for 'PayloadData_array8' to 'Field7'. (GlobalSignalCatch:EventSubProcess:CatchGlobalGlobalSignal2:Field7)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/GlobalSignalTest/Process Packages/GlobalSignalTest.xpdl", //$NON-NLS-1$ 
                                "abstractMappingRule.incompatibleTypes", //$NON-NLS-1$ 
                                "//@package/@processes.1/@activitySets.0/@activities.0/@event/@triggerResultSignal/@otherElements.0/@dataMappings.7", //$NON-NLS-1$ 
                                "Process Manager  : 'Map From Signal' Global Signal Mapping: The data types are incompatible for mapping 'PayloadData3' to 'Field3'. (GlobalSignalCatch:EventSubProcess:CatchGlobalGlobalSignal2:Field3)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/GlobalSignalTest/Process Packages/GlobalSignalTest.xpdl", //$NON-NLS-1$ 
                                "abstractMappingRule.incompatibleTypes", //$NON-NLS-1$ 
                                "//@package/@processes.1/@activitySets.0/@activities.0/@event/@triggerResultSignal/@otherElements.0/@dataMappings.8", //$NON-NLS-1$ 
                                "Process Manager  : 'Map From Signal' Global Signal Mapping: The data types are incompatible for mapping 'f' to 'Field6'. (GlobalSignalCatch:EventSubProcess:CatchGlobalGlobalSignal2:Field6)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/GlobalSignalTest/Process Packages/GlobalSignalTest.xpdl", //$NON-NLS-1$ 
                                "abstractMappingRule.multiToSingleUnsupported", //$NON-NLS-1$ 
                                "//@package/@processes.1/@activitySets.0/@activities.0/@event/@triggerResultSignal/@otherElements.0/@dataMappings.8", //$NON-NLS-1$ 
                                "Process Manager  : 'Map From Signal' Global Signal Mapping: Multiple to single instance data mapping is not supported for 'f' to 'Field6'. (GlobalSignalCatch:EventSubProcess:CatchGlobalGlobalSignal2:Field6)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/GlobalSignalTest/Process Packages/GlobalSignalTest.xpdl", //$NON-NLS-1$ 
                                "abstractMappingRule.unmappedRequiredTarget", //$NON-NLS-1$ 
                                "_owJPcMnHEeSVI4K6PcOs9w", //$NON-NLS-1$ 
                                "Process Manager  : 'Map To Signal' Global Signal Mapping: the mandatory target 'PayloadData' must be mapped to complete the target data. (GlobalSignalThrow:ThrowGlobalGlobalSignal)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                };
        return markerInfos;
    }

    @Override
    protected String getTestName() {
        return "N2_40_GlobalSignalReferencingMappingValidationTest"; //$NON-NLS-1$
    }

    @Override
    protected String getTestPlugInId() {
        return "com.tibco.xpd.n2.mappings.validation.test"; //$NON-NLS-1$
    }

    @Override
    protected TestResourceInfo[] getTestResources() {
        TestResourceInfo[] testResources =
                new TestResourceInfo[] {
                        new TestResourceInfo(
                                "resources/N240GlobalSignalReferencingMappingValidation", "GlobalSignalTest/Process Packages{processes}/GlobalSignalTest.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/N240GlobalSignalReferencingMappingValidation", "Signals/Global Signal Definitions{gsd}/Signals.gsd"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/N240GlobalSignalReferencingMappingValidation", "Signals/Global Signal Definitions{gsd}/SubFolder/Signals.gsd"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/N240GlobalSignalReferencingMappingValidation", "test/Business Objects{bom}/test.bom"), //$NON-NLS-1$ //$NON-NLS-2$
                };

        return testResources;
    }

}
