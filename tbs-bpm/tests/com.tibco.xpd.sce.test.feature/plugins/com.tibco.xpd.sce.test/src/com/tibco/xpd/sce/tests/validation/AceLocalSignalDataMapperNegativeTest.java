/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.sce.tests.validation;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;

import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;
import com.tibco.xpd.n2.test.core.validation.AbstractN2BaseValidationTest;
import com.tibco.xpd.resources.util.ProjectUtil;

/**
 * JUnits to protect validation rules for local signal data mapper.
 *
 * @author sajain
 * @since Jul 19, 2019
 */
@SuppressWarnings("nls")
public class AceLocalSignalDataMapperNegativeTest extends AbstractN2BaseValidationTest {

    public AceLocalSignalDataMapperNegativeTest() {
        super(false);
    }

    /**
     * AceLocalSignalDataMapperNegativeTest
     * 
     * @throws Exception
     */
    public void testAceLocalSignalDataMapperNegativeTest() throws Exception {
        doTestValidations();
        List<ValidationsTestProblemMarkerInfo> allProblemMarkers =
                getProblemMarkers(getTestFile("BPMLocalSignalProj01.xpdl"));

        for (ValidationsTestProblemMarkerInfo eachProblemMarker : allProblemMarkers) {
            fail(eachProblemMarker.getProblemText() + "should not exist!");
        }
        return;
    }

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#configureProject(org.eclipse.core.resources.IProject)
     * 
     * @param testProject
     */
    @Override
    protected void configureProject(IProject testProject) {

        if (testProject.getName().equals("BPMLocalSignalProj01")) { //$NON-NLS-1$

            super.configureProject(testProject);
            /*
             * Add references to Business Data Project.
             */
            IProject projectToREference = getTestProject("BOMLocalSignalProj01"); //$NON-NLS-1$

            try {
                ProjectUtil.addReferenceProject(testProject, projectToREference);
            } catch (CoreException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected String getTestName() {
        return "AceLocalSignalDataMapperNegativeTest";
    }

    @Override
    protected String getTestPlugInId() {
        return "com.tibco.xpd.sce.test";
    }

    @Override
    protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos() {
        ValidationsTestProblemMarkerInfo[] markerInfos = new ValidationsTestProblemMarkerInfo[] {

                new ValidationsTestProblemMarkerInfo("/BPMLocalSignalProj01/Process Packages/BPMLocalSignalProj01.xpdl",
                        "abstractMappingRule.mappingToReadOnly",
                        "//@package/@processes.0/@activities.4/@event/@triggerResultSignal/@otherElements.0/@outputScriptDataMapper/@dataMappings.0",
                        "BPM  : Data Mapper: Mapping is not permitted to read-only target 'CorrelationField2'. (BPMLocalSignalProj01Process:CatchSignal1:CorrelationField2)",
                        "Remove mapping"),
                new ValidationsTestProblemMarkerInfo("/BPMLocalSignalProj01/Process Packages/BPMLocalSignalProj01.xpdl",
                        "abstractMappingRule.incompatibleTypes",
                        "//@package/@processes.0/@activities.4/@event/@triggerResultSignal/@otherElements.0/@outputScriptDataMapper/@dataMappings.1",
                        "BPM  : Data Mapper: The data types are incompatible for mapping 'Field' to 'Field2'. (BPMLocalSignalProj01Process:CatchSignal1:Field2)",
                        "Remove mapping"),
                new ValidationsTestProblemMarkerInfo("/BPMLocalSignalProj01/Process Packages/BPMLocalSignalProj01.xpdl",
                        "abstractMappingRule.incompatibleTypes",
                        "//@package/@processes.0/@activities.4/@event/@triggerResultSignal/@otherElements.0/@outputScriptDataMapper/@dataMappings.3",
                        "BPM  : Data Mapper: The data types are incompatible for mapping 'Parameter' to 'Field4'. (BPMLocalSignalProj01Process:CatchSignal1:Field4)",
                        "Remove mapping"),
                new ValidationsTestProblemMarkerInfo("/BPMLocalSignalProj01/Process Packages/BPMLocalSignalProj01.xpdl",
                        "abstractMappingRule.incompatibleTypes",
                        "//@package/@processes.0/@activities.4/@event/@triggerResultSignal/@otherElements.0/@outputScriptDataMapper/@dataMappings.6",
                        "BPM  : Data Mapper: The data types are incompatible for mapping 'Script3' to 'Field5.attribute3'. (BPMLocalSignalProj01Process:CatchSignal1:Field5.attribute3)",
                        "Remove mapping"),
                new ValidationsTestProblemMarkerInfo("/BPMLocalSignalProj01/Process Packages/BPMLocalSignalProj01.xpdl",
                        "dataMapper.emptyUserDefinedScript", "_w_XEoKn7EemWDZgShfumSw",
                        "BPM  : Empty user defined mapping scripts ('Script1') are not supported (BPMLocalSignalProj01Process:CatchSignal1:Script1)",
                        ""),
                new ValidationsTestProblemMarkerInfo("/BPMLocalSignalProj01/Process Packages/BPMLocalSignalProj01.xpdl",
                        "bx.emptyScriptNotSupported",
                        "//@package/@processes.0/@activities.4/@event/@triggerResultSignal/@otherElements.0/@outputScriptDataMapper/@dataMappings.4",
                        "BPM  : Empty User Defined Scripts are not supported (BPMLocalSignalProj01Process:CatchSignal1:Field5.attribute1)",
                        ""),
                new ValidationsTestProblemMarkerInfo("/BPMLocalSignalProj01/Process Packages/BPMLocalSignalProj01.xpdl",
                        "bx.signalMappingOnUserTaskBoundaryOnly", "_e57JQKn7EemWDZgShfumSw",
                        "BPM  : Catch signal data mapping is only supported for non-cancelling signal events on a user task boundary. (BPMLocalSignalProj01Process:CatchSignal1)",
                        "Remove signal data mappings."),
                new ValidationsTestProblemMarkerInfo("/BPMLocalSignalProj01/Process Packages/BPMLocalSignalProj01.xpdl",
                        "N2ValidationStrategy_VariableNotDefined", "_FKMRgKy1Eempdu3-iUQcOw",
                        "BPM  : Catch Local Signal Map From Signal::At Line:1 column:29, Variable SIGNAL_InvalidPayload not defined or is not associated in the task interface. (BPMLocalSignalProj01Process:CatchSignal1:Script4)",
                        ""),
                new ValidationsTestProblemMarkerInfo("/BPMLocalSignalProj01/Process Packages/BPMLocalSignalProj01.xpdl",
                        "N2FunctionStatementValidator_LastStatementReturn", "_I5vscKy1Eempdu3-iUQcOw",
                        "BPM  : Catch Local Signal Map From Signal::At Line:2 column:1, Last statement must be a return. (BPMLocalSignalProj01Process:CatchSignal1:Script5)",
                        "") };
        return markerInfos;
    }

    @Override
    protected TestResourceInfo[] getTestResources() {
        TestResourceInfo[] testResources = new TestResourceInfo[] {
                new TestResourceInfo("resources/AceLocalSignalDataMapperNegativeTest",
                        "BOMLocalSignalProj01/Business Objects{bom}/BOMLocalSignalProj01.bom"),
                new TestResourceInfo("resources/AceLocalSignalDataMapperNegativeTest",
                        "BPMLocalSignalProj01/Process Packages{processes}/BPMLocalSignalProj01.xpdl"),
                new TestResourceInfo("resources/AceLocalSignalDataMapperNegativeTest",
                        "BPMLocalSignalProj01/Forms{forms}/Untitled.form") };

        return testResources;
    }
}