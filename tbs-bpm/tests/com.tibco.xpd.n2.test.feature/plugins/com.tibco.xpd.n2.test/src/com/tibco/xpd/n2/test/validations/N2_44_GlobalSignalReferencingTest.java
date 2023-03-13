/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.n2.test.validations;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;

import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;
import com.tibco.xpd.n2.test.core.validation.AbstractN2BaseValidationTest;
import com.tibco.xpd.resources.util.ProjectUtil;

/**
 * Junit test to verify if all the Global Signal Referencing validations are
 * raised correctly.
 * 
 * 
 * @author kthombar
 * @since Feb 26, 2015
 */
public class N2_44_GlobalSignalReferencingTest extends
        AbstractN2BaseValidationTest {

    public N2_44_GlobalSignalReferencingTest() {
        super(true);
    }

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#configureProject(org.eclipse.core.resources.IProject)
     * 
     * @param testProject
     */
    @Override
    protected void configureProject(IProject testProject) {

        if (testProject.getName().equals("BpmDeveloperProject")) { //$NON-NLS-1$

            super.configureProject(testProject);
            /*
             * Add reference to Global Signal Project.
             */
            IProject projectToREference = getTestProject("GlobalSignalProject"); //$NON-NLS-1$

            try {
                ProjectUtil
                        .addReferenceProject(testProject, projectToREference);
            } catch (CoreException e) {
                e.printStackTrace();
            }
        } else {
            /*
             * Set the Id of the Global Signal Project because the Throw & Catch
             * Global Signals reference the Global Signal Definition Project by
             * project Id.
             */
            TestUtil.addGlobalDestinationToProject("com.example.globalsignalproject", //$NON-NLS-1$
                    "CE", //$NON-NLS-1$
                    testProject);
        }
    }

    /**
     * N2_44_GlobalSignalReferencingTest
     * 
     * @throws Exception
     */
    public void testN2_44_GlobalSignalReferencingTest() throws Exception {
        doTestValidations();
        return;
    }

    @Override
    protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos() {
        ValidationsTestProblemMarkerInfo[] markerInfos =
                new ValidationsTestProblemMarkerInfo[] {

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmDeveloperProject/Process Packages/BpmDeveloperProject.xpdl", //$NON-NLS-1$ 
                                "abstractMappingRule.incompatibleTypes", //$NON-NLS-1$ 
                                "//@package/@processes.0/@activities.11/@event/@triggerResultSignal/@otherElements.0/@correlationMappings/@dataMappings.0", //$NON-NLS-1$ 
                                "Process Manager  : 'Map From Signal' Global Signal Mapping: The data types are incompatible for mapping 'PayloadData' to 'CorrelationField'. (BpmDeveloperProjectProcess:CatchGlobalGlobalSignal_Thrid_changed:CorrelationField)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmDeveloperProject/Process Packages/BpmDeveloperProject.xpdl", //$NON-NLS-1$ 
                                "abstractMappingRule.sourceMissing", //$NON-NLS-1$ 
                                "//@package/@processes.0/@activities.11/@event/@triggerResultSignal/@otherElements.0/@correlationMappings/@dataMappings.1", //$NON-NLS-1$ 
                                "Process Manager  : 'Map From Signal' Global Signal Mapping: mapping source data 'PayloadData2' is no longer available. (BpmDeveloperProjectProcess:CatchGlobalGlobalSignal_Thrid_changed:CorrelationField2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmDeveloperProject/Process Packages/BpmDeveloperProject.xpdl", //$NON-NLS-1$ 
                                "abstractMappingRule.incompatibleTypes", //$NON-NLS-1$ 
                                "//@package/@processes.0/@activities.11/@event/@triggerResultSignal/@otherElements.0/@correlationMappings/@dataMappings.2", //$NON-NLS-1$ 
                                "Process Manager  : 'Map From Signal' Global Signal Mapping: The data types are incompatible for mapping 'PayloadData3' to 'CorrelationField3'. (BpmDeveloperProjectProcess:CatchGlobalGlobalSignal_Thrid_changed:CorrelationField3)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmDeveloperProject/Process Packages/BpmDeveloperProject.xpdl", //$NON-NLS-1$ 
                                "abstractMappingRule.sourceMissing", //$NON-NLS-1$ 
                                "//@package/@processes.0/@activities.11/@event/@triggerResultSignal/@otherElements.0/@dataMappings.0", //$NON-NLS-1$ 
                                "Process Manager  : 'Map From Signal' Global Signal Mapping: mapping source data 'Copy_Of_PayloadData' is no longer available. (BpmDeveloperProjectProcess:CatchGlobalGlobalSignal_Thrid_changed:Field)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmDeveloperProject/Process Packages/BpmDeveloperProject.xpdl", //$NON-NLS-1$ 
                                "abstractMappingRule.incompatibleTypes", //$NON-NLS-1$ 
                                "//@package/@processes.0/@activities.11/@event/@triggerResultSignal/@otherElements.0/@dataMappings.1", //$NON-NLS-1$ 
                                "Process Manager  : 'Map From Signal' Global Signal Mapping: The data types are incompatible for mapping 'Copy_Of_PayloadData2' to 'Field2'. (BpmDeveloperProjectProcess:CatchGlobalGlobalSignal_Thrid_changed:Field2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmDeveloperProject/Process Packages/BpmDeveloperProject.xpdl", //$NON-NLS-1$ 
                                "abstractMappingRule.sourceMissing", //$NON-NLS-1$ 
                                "//@package/@processes.0/@activities.11/@event/@triggerResultSignal/@otherElements.0/@dataMappings.2", //$NON-NLS-1$ 
                                "Process Manager  : 'Map From Signal' Global Signal Mapping: mapping source data 'Copy_Of_PayloadData3' is no longer available. (BpmDeveloperProjectProcess:CatchGlobalGlobalSignal_Thrid_changed:Field3)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmDeveloperProject/Process Packages/BpmDeveloperProject.xpdl", //$NON-NLS-1$ 
                                "abstractMappingRule.incompatibleTypes", //$NON-NLS-1$ 
                                "//@package/@processes.0/@activities.3/@event/@triggerResultSignal/@otherElements.0/@correlationMappings/@dataMappings.0", //$NON-NLS-1$ 
                                "Process Manager  : 'Map To Signal' Global Signal Mapping: The data types are incompatible for mapping 'CorrelationField' to 'PayloadData2'. (BpmDeveloperProjectProcess:ThrowGlobalGlobalSignal_Second2:PayloadData2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmDeveloperProject/Process Packages/BpmDeveloperProject.xpdl", //$NON-NLS-1$ 
                                "abstractMappingRule.incompatibleTypes", //$NON-NLS-1$ 
                                "//@package/@processes.0/@activities.3/@event/@triggerResultSignal/@otherElements.0/@correlationMappings/@dataMappings.2", //$NON-NLS-1$ 
                                "Process Manager  : 'Map To Signal' Global Signal Mapping: The data types are incompatible for mapping 'Field' to 'PayloadData3'. (BpmDeveloperProjectProcess:ThrowGlobalGlobalSignal_Second2:PayloadData3)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmDeveloperProject/Process Packages/BpmDeveloperProject.xpdl", //$NON-NLS-1$ 
                                "abstractMappingRule.incompatibleTypes", //$NON-NLS-1$ 
                                "//@package/@processes.0/@activities.3/@event/@triggerResultSignal/@otherElements.0/@dataMappings.2", //$NON-NLS-1$ 
                                "Process Manager  : 'Map To Signal' Global Signal Mapping: The data types are incompatible for mapping 'Field2' to 'Copy_Of_PayloadData2'. (BpmDeveloperProjectProcess:ThrowGlobalGlobalSignal_Second2:Copy_Of_PayloadData2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmDeveloperProject/Process Packages/BpmDeveloperProject.xpdl", //$NON-NLS-1$ 
                                "abstractMappingRule.incompatibleTypes", //$NON-NLS-1$ 
                                "//@package/@processes.0/@activities.5/@event/@triggerResultSignal/@otherElements.1/@correlationMappings/@dataMappings.0", //$NON-NLS-1$ 
                                "Process Manager  : 'Map From Signal' Global Signal Mapping: The data types are incompatible for mapping 'Copy_Of_PayloadData2' to 'CorrelationField3'. (BpmDeveloperProjectProcess:CatchGlobalGlobalSignal_Second2:CorrelationField3)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmDeveloperProject/Process Packages/BpmDeveloperProject.xpdl", //$NON-NLS-1$ 
                                "bx.emptyScriptNotSupported", //$NON-NLS-1$ 
                                "//@package/@processes.0/@activities.5/@event/@triggerResultSignal/@otherElements.1/@correlationMappings/@dataMappings.1", //$NON-NLS-1$ 
                                "Process Manager  : Empty User Defined Scripts are not supported (BpmDeveloperProjectProcess:CatchGlobalGlobalSignal_Second2:CorrelationField2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmDeveloperProject/Process Packages/BpmDeveloperProject.xpdl", //$NON-NLS-1$ 
                                "bx.OnlyCorrelationPayloadMappingSupportedToTargetCorrelationData", //$NON-NLS-1$ 
                                "//@package/@processes.0/@activities.5/@event/@triggerResultSignal/@otherElements.1/@correlationMappings/@dataMappings.1", //$NON-NLS-1$ 
                                "Process Manager  : 'Map From Signal' Global Signal Mapping: Correlation data (CorrelationField2) can only be mapped from signal payload correlation parameters. (BpmDeveloperProjectProcess:CatchGlobalGlobalSignal_Second2:CorrelationField2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmDeveloperProject/Process Packages/BpmDeveloperProject.xpdl", //$NON-NLS-1$ 
                                "abstractMappingRule.incompatibleTypes", //$NON-NLS-1$ 
                                "//@package/@processes.0/@activities.5/@event/@triggerResultSignal/@otherElements.1/@correlationMappings/@dataMappings.2", //$NON-NLS-1$ 
                                "Process Manager  : 'Map From Signal' Global Signal Mapping: The data types are incompatible for mapping 'PayloadData2' to 'CorrelationField'. (BpmDeveloperProjectProcess:CatchGlobalGlobalSignal_Second2:CorrelationField)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmDeveloperProject/Process Packages/BpmDeveloperProject.xpdl", //$NON-NLS-1$ 
                                "abstractMappingRule.incompatibleTypes", //$NON-NLS-1$ 
                                "//@package/@processes.0/@activities.5/@event/@triggerResultSignal/@otherElements.1/@dataMappings.0", //$NON-NLS-1$ 
                                "Process Manager  : 'Map From Signal' Global Signal Mapping: The data types are incompatible for mapping 'Copy_Of_PayloadData3' to 'Field2'. (BpmDeveloperProjectProcess:CatchGlobalGlobalSignal_Second2:Field2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmDeveloperProject/Process Packages/BpmDeveloperProject.xpdl", //$NON-NLS-1$ 
                                "abstractMappingRule.incompatibleTypes", //$NON-NLS-1$ 
                                "//@package/@processes.0/@activities.5/@event/@triggerResultSignal/@otherElements.1/@dataMappings.1", //$NON-NLS-1$ 
                                "Process Manager  : 'Map From Signal' Global Signal Mapping: The data types are incompatible for mapping 'Copy_Of_PayloadData' to 'Field'. (BpmDeveloperProjectProcess:CatchGlobalGlobalSignal_Second2:Field)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmDeveloperProject/Process Packages/BpmDeveloperProject.xpdl", //$NON-NLS-1$ 
                                "abstractMappingRule.incompatibleTypes", //$NON-NLS-1$ 
                                "//@package/@processes.0/@activities.5/@event/@triggerResultSignal/@otherElements.1/@dataMappings.2", //$NON-NLS-1$ 
                                "Process Manager  : 'Map From Signal' Global Signal Mapping: The data types are incompatible for mapping 'Copy_Of_PayloadData2' to 'Field3'. (BpmDeveloperProjectProcess:CatchGlobalGlobalSignal_Second2:Field3)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmDeveloperProject/Process Packages/BpmDeveloperProject.xpdl", //$NON-NLS-1$ 
                                "bx.catchGlobalSignalEventOnlySupportedForBusinessProcess", //$NON-NLS-1$ 
                                "_2CO3YMbkEeS279cMdP0CRA", //$NON-NLS-1$ 
                                "Process Manager  : Catch global signal events are only supported for Business Processes. (BpmDeveloperProjectProcess5:CatchGlobalGlobalSignal_First)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmDeveloperProject/Process Packages/BpmDeveloperProject.xpdl", //$NON-NLS-1$ 
                                "bpmn.dev.eventHandlerCorrelationDataInitialization", //$NON-NLS-1$ 
                                "_2gzNQMblEeS279cMdP0CRA", //$NON-NLS-1$ 
                                "BPMN  : Event-handlers must be initialized only after the correlation field 'CorrelationField' is assigned in the main process flow. Use the 'Event Handler->Initializers' configuration to specify the activity(s) that set the correlation data. (BpmDeveloperProjectProcess:CatchGlobalGlobalSignal_Thrid)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmDeveloperProject/Process Packages/BpmDeveloperProject.xpdl", //$NON-NLS-1$ 
                                "bpmn.dev.eventHandlerCorrelationDataInitialization", //$NON-NLS-1$ 
                                "_2gzNQMblEeS279cMdP0CRA", //$NON-NLS-1$ 
                                "BPMN  : Event-handlers must be initialized only after the correlation field 'CorrelationField2' is assigned in the main process flow. Use the 'Event Handler->Initializers' configuration to specify the activity(s) that set the correlation data. (BpmDeveloperProjectProcess:CatchGlobalGlobalSignal_Thrid)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmDeveloperProject/Process Packages/BpmDeveloperProject.xpdl", //$NON-NLS-1$ 
                                "bpmn.dev.eventHandlerCorrelationDataInitialization", //$NON-NLS-1$ 
                                "_2gzNQMblEeS279cMdP0CRA", //$NON-NLS-1$ 
                                "BPMN  : Event-handlers must be initialized only after the correlation field 'CorrelationField3' is assigned in the main process flow. Use the 'Event Handler->Initializers' configuration to specify the activity(s) that set the correlation data. (BpmDeveloperProjectProcess:CatchGlobalGlobalSignal_Thrid)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmDeveloperProject/Process Packages/BpmDeveloperProject.xpdl", //$NON-NLS-1$ 
                                "bx.unresolvedGlobalSignalReference", //$NON-NLS-1$ 
                                "_2gzNQMblEeS279cMdP0CRA", //$NON-NLS-1$ 
                                "Process Manager  : Global signal 'GlobalSignal_Thrid (com.example.globalsignalproject/GlobalSignalProject.gsd)' is no longer available. (BpmDeveloperProjectProcess:CatchGlobalGlobalSignal_Thrid)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmDeveloperProject/Process Packages/BpmDeveloperProject.xpdl", //$NON-NLS-1$ 
                                "abstractMappingRule.unmappedRequiredTarget", //$NON-NLS-1$ 
                                "_9-LaQMbkEeS279cMdP0CRA", //$NON-NLS-1$ 
                                "Process Manager  : 'Map To Signal' Global Signal Mapping: the mandatory target 'Copy_Of_PayloadData2' must be mapped to complete the target data. (BpmDeveloperProjectProcess:ThrowGlobalGlobalSignal_Second)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmDeveloperProject/Process Packages/BpmDeveloperProject.xpdl", //$NON-NLS-1$ 
                                "abstractMappingRule.unmappedRequiredTarget", //$NON-NLS-1$ 
                                "_9-LaQMbkEeS279cMdP0CRA", //$NON-NLS-1$ 
                                "Process Manager  : 'Map To Signal' Global Signal Mapping: the mandatory target 'PayloadData' must be mapped to complete the target data. (BpmDeveloperProjectProcess:ThrowGlobalGlobalSignal_Second)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmDeveloperProject/Process Packages/BpmDeveloperProject.xpdl", //$NON-NLS-1$ 
                                "abstractMappingRule.unmappedRequiredTarget", //$NON-NLS-1$ 
                                "_9-LaQMbkEeS279cMdP0CRA", //$NON-NLS-1$ 
                                "Process Manager  : 'Map To Signal' Global Signal Mapping: the mandatory target 'PayloadData2' must be mapped to complete the target data. (BpmDeveloperProjectProcess:ThrowGlobalGlobalSignal_Second)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmDeveloperProject/Process Packages/BpmDeveloperProject.xpdl", //$NON-NLS-1$ 
                                "abstractMappingRule.unmappedRequiredTarget", //$NON-NLS-1$ 
                                "_9-LaQMbkEeS279cMdP0CRA", //$NON-NLS-1$ 
                                "Process Manager  : 'Map To Signal' Global Signal Mapping: the mandatory target 'PayloadData3' must be mapped to complete the target data. (BpmDeveloperProjectProcess:ThrowGlobalGlobalSignal_Second)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmDeveloperProject/Process Packages/BpmDeveloperProject.xpdl", //$NON-NLS-1$ 
                                "bx.error.throwGlobalSignalMapToSignalScript", //$NON-NLS-1$ 
                                "_C7easMblEeS279cMdP0CRA", //$NON-NLS-1$ 
                                "Process Manager  : Throw Global Signal Map To Signal::At Line:1 column:19, Variable CorrelationField2d not defined or is not associated in the task interface. (BpmDeveloperProjectProcess:ThrowGlobalGlobalSignal_Second2:testScript)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmDeveloperProject/Process Packages/BpmDeveloperProject.xpdl", //$NON-NLS-1$ 
                                "bpmn.dev.eventHandlerCorrelationDataInitialization", //$NON-NLS-1$ 
                                "_IbwW8MbmEeS279cMdP0CRA", //$NON-NLS-1$ 
                                "BPMN  : Event-handlers must be initialized only after the correlation field 'CorrelationField' is assigned in the main process flow. Use the 'Event Handler->Initializers' configuration to specify the activity(s) that set the correlation data. (BpmDeveloperProjectProcess:CatchGlobalGlobalSignal_Thrid_changed)", //$NON-NLS-1$
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmDeveloperProject/Process Packages/BpmDeveloperProject.xpdl", //$NON-NLS-1$ 
                                "bpmn.dev.eventHandlerCorrelationDataInitialization", //$NON-NLS-1$ 
                                "_IbwW8MbmEeS279cMdP0CRA", //$NON-NLS-1$ 
                                "BPMN  : Event-handlers must be initialized only after the correlation field 'CorrelationField2' is assigned in the main process flow. Use the 'Event Handler->Initializers' configuration to specify the activity(s) that set the correlation data. (BpmDeveloperProjectProcess:CatchGlobalGlobalSignal_Thrid_changed)", //$NON-NLS-1$
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmDeveloperProject/Process Packages/BpmDeveloperProject.xpdl", //$NON-NLS-1$ 
                                "bpmn.dev.eventHandlerCorrelationDataInitialization", //$NON-NLS-1$ 
                                "_IbwW8MbmEeS279cMdP0CRA", //$NON-NLS-1$ 
                                "BPMN  : Event-handlers must be initialized only after the correlation field 'CorrelationField3' is assigned in the main process flow. Use the 'Event Handler->Initializers' configuration to specify the activity(s) that set the correlation data. (BpmDeveloperProjectProcess:CatchGlobalGlobalSignal_Thrid_changed)", //$NON-NLS-1$
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmDeveloperProject/Process Packages/BpmDeveloperProject.xpdl", //$NON-NLS-1$ 
                                "bx.SourceCorrelationPayloadMustBeMapped", //$NON-NLS-1$ 
                                "_IbwW8MbmEeS279cMdP0CRA", //$NON-NLS-1$ 
                                "Process Manager  : 'Map From Signal' Global Signal Mapping: Source correlation payload data (PayloadData21) must be mapped to a correlation data field. (BpmDeveloperProjectProcess:CatchGlobalGlobalSignal_Thrid_changed)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmDeveloperProject/Process Packages/BpmDeveloperProject.xpdl", //$NON-NLS-1$ 
                                "bx.catchGlobalSignalEventOnlySupportedForBusinessProcess", //$NON-NLS-1$ 
                                "_s6JRgcbkEeS279cMdP0CRA", //$NON-NLS-1$ 
                                "Process Manager  : Catch global signal events are only supported for Business Processes. (BpmDeveloperProjectProcess2:EventSubProcess:CatchGlobalGlobalSignal_First)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmDeveloperProject/Process Packages/BpmDeveloperProject.xpdl", //$NON-NLS-1$ 
                                "bx.interruptingEventSubProcessNotSupportedInPageflow", //$NON-NLS-1$ 
                                "_s6JRgcbkEeS279cMdP0CRA", //$NON-NLS-1$ 
                                "Process Manager  : Interrupting Event Sub-processes are not supported in Pageflows, Business Services and Case Actions. (BpmDeveloperProjectProcess2:EventSubProcess:CatchGlobalGlobalSignal_First)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmDeveloperProject/Process Packages/BpmDeveloperProject.xpdl", //$NON-NLS-1$ 
                                "abstractMappingRule.unmappedRequiredTarget", //$NON-NLS-1$ 
                                "_Sjt64sblEeS279cMdP0CRA", //$NON-NLS-1$ 
                                "Process Manager  : 'Map From Signal' Global Signal Mapping: the mandatory target 'CorrelationField' must be mapped to complete the target data. (BpmDeveloperProjectProcess:EventSubProcess:CatchGlobalGlobalSignal_Second)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmDeveloperProject/Process Packages/BpmDeveloperProject.xpdl", //$NON-NLS-1$ 
                                "abstractMappingRule.unmappedRequiredTarget", //$NON-NLS-1$ 
                                "_Sjt64sblEeS279cMdP0CRA", //$NON-NLS-1$ 
                                "Process Manager  : 'Map From Signal' Global Signal Mapping: the mandatory target 'CorrelationField2' must be mapped to complete the target data. (BpmDeveloperProjectProcess:EventSubProcess:CatchGlobalGlobalSignal_Second)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmDeveloperProject/Process Packages/BpmDeveloperProject.xpdl", //$NON-NLS-1$ 
                                "abstractMappingRule.unmappedRequiredTarget", //$NON-NLS-1$ 
                                "_Sjt64sblEeS279cMdP0CRA", //$NON-NLS-1$ 
                                "Process Manager  : 'Map From Signal' Global Signal Mapping: the mandatory target 'CorrelationField3' must be mapped to complete the target data. (BpmDeveloperProjectProcess:EventSubProcess:CatchGlobalGlobalSignal_Second)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmDeveloperProject/Process Packages/BpmDeveloperProject.xpdl", //$NON-NLS-1$ 
                                "bpmn.dev.eventHandlerCorrelationDataInitialization", //$NON-NLS-1$ 
                                "_Sjt64sblEeS279cMdP0CRA", //$NON-NLS-1$ 
                                "BPMN  : Event-handlers must be initialized only after the correlation field 'CorrelationField' is assigned in the main process flow. Use the 'Event Handler->Initializers' configuration to specify the activity(s) that set the correlation data. (BpmDeveloperProjectProcess:EventSubProcess:CatchGlobalGlobalSignal_Second)", //$NON-NLS-1$
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmDeveloperProject/Process Packages/BpmDeveloperProject.xpdl", //$NON-NLS-1$ 
                                "bpmn.dev.eventHandlerCorrelationDataInitialization", //$NON-NLS-1$ 
                                "_Sjt64sblEeS279cMdP0CRA", //$NON-NLS-1$ 
                                "BPMN  : Event-handlers must be initialized only after the correlation field 'CorrelationField2' is assigned in the main process flow. Use the 'Event Handler->Initializers' configuration to specify the activity(s) that set the correlation data. (BpmDeveloperProjectProcess:EventSubProcess:CatchGlobalGlobalSignal_Second)", //$NON-NLS-1$
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmDeveloperProject/Process Packages/BpmDeveloperProject.xpdl", //$NON-NLS-1$ 
                                "bpmn.dev.eventHandlerCorrelationDataInitialization", //$NON-NLS-1$ 
                                "_Sjt64sblEeS279cMdP0CRA", //$NON-NLS-1$ 
                                "BPMN  : Event-handlers must be initialized only after the correlation field 'CorrelationField3' is assigned in the main process flow. Use the 'Event Handler->Initializers' configuration to specify the activity(s) that set the correlation data. (BpmDeveloperProjectProcess:EventSubProcess:CatchGlobalGlobalSignal_Second)", //$NON-NLS-1$
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmDeveloperProject/Process Packages/BpmDeveloperProject.xpdl", //$NON-NLS-1$ 
                                "bx.SourceCorrelationPayloadMustBeMapped", //$NON-NLS-1$ 
                                "_Sjt64sblEeS279cMdP0CRA", //$NON-NLS-1$ 
                                "Process Manager  : 'Map From Signal' Global Signal Mapping: Source correlation payload data (PayloadData) must be mapped to a correlation data field. (BpmDeveloperProjectProcess:EventSubProcess:CatchGlobalGlobalSignal_Second)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmDeveloperProject/Process Packages/BpmDeveloperProject.xpdl", //$NON-NLS-1$ 
                                "bx.SourceCorrelationPayloadMustBeMapped", //$NON-NLS-1$ 
                                "_Sjt64sblEeS279cMdP0CRA", //$NON-NLS-1$ 
                                "Process Manager  : 'Map From Signal' Global Signal Mapping: Source correlation payload data (PayloadData2) must be mapped to a correlation data field. (BpmDeveloperProjectProcess:EventSubProcess:CatchGlobalGlobalSignal_Second)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmDeveloperProject/Process Packages/BpmDeveloperProject.xpdl", //$NON-NLS-1$ 
                                "bx.SourceCorrelationPayloadMustBeMapped", //$NON-NLS-1$ 
                                "_Sjt64sblEeS279cMdP0CRA", //$NON-NLS-1$ 
                                "Process Manager  : 'Map From Signal' Global Signal Mapping: Source correlation payload data (PayloadData3) must be mapped to a correlation data field. (BpmDeveloperProjectProcess:EventSubProcess:CatchGlobalGlobalSignal_Second)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmDeveloperProject/Process Packages/BpmDeveloperProject.xpdl", //$NON-NLS-1$ 
                                "bx.catchGlobalSignalEventOnlySupportedForBusinessProcess", //$NON-NLS-1$ 
                                "_wxZP4MbkEeS279cMdP0CRA", //$NON-NLS-1$ 
                                "Process Manager  : Catch global signal events are only supported for Business Processes. (BpmDeveloperProjectProcess3:CatchGlobalGlobalSignal_First)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmDeveloperProject/Process Packages/BpmDeveloperProject.xpdl", //$NON-NLS-1$ 
                                "bpmn.dev.eventHandlerCorrelationDataInitializeActivitiesMissing", //$NON-NLS-1$ 
                                "_Z5ICMMblEeS279cMdP0CRA", //$NON-NLS-1$ 
                                "BPMN  : 1 of the Event Handler's selected activities for initialization are no longer present. (BpmDeveloperProjectProcess:CatchGlobalGlobalSignal_Second2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmDeveloperProject/Process Packages/BpmDeveloperProject.xpdl", //$NON-NLS-1$ 
                                "bx.SourceCorrelationPayloadMustBeMapped", //$NON-NLS-1$ 
                                "_Z5ICMMblEeS279cMdP0CRA", //$NON-NLS-1$ 
                                "Process Manager  : 'Map From Signal' Global Signal Mapping: Source correlation payload data (PayloadData) must be mapped to a correlation data field. (BpmDeveloperProjectProcess:CatchGlobalGlobalSignal_Second2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmDeveloperProject/Process Packages/BpmDeveloperProject.xpdl", //$NON-NLS-1$ 
                                "bx.SourceCorrelationPayloadMustBeMapped", //$NON-NLS-1$ 
                                "_Z5ICMMblEeS279cMdP0CRA", //$NON-NLS-1$ 
                                "Process Manager  : 'Map From Signal' Global Signal Mapping: Source correlation payload data (PayloadData3) must be mapped to a correlation data field. (BpmDeveloperProjectProcess:CatchGlobalGlobalSignal_Second2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmDeveloperProject/Process Packages/BpmDeveloperProject.xpdl", //$NON-NLS-1$ 
                                "bx.catchGlobalSignalEventOnlySupportedForBusinessProcess", //$NON-NLS-1$ 
                                "_z9VAYMbkEeS279cMdP0CRA", //$NON-NLS-1$ 
                                "Process Manager  : Catch global signal events are only supported for Business Processes. (BpmDeveloperProjectProcess4:CatchGlobalGlobalSignal_First)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                };
        return markerInfos;
    }

    @Override
    protected String getTestName() {
        return "N2_44_GlobalSignalReferencingTest"; //$NON-NLS-1$
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
                                "resources/N244GlobalSignalReferencingTest", "BpmDeveloperProject/Process Packages{processes}/BpmDeveloperProject.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/N244GlobalSignalReferencingTest", "GlobalSignalProject/Global Signal Definitions{gsd}/GlobalSignalProject.gsd"), //$NON-NLS-1$ //$NON-NLS-2$
                };

        return testResources;
    }

}
