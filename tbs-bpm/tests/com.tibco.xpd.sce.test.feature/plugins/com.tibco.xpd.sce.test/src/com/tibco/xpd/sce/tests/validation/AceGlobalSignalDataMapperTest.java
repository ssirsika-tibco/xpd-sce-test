/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.sce.tests.validation;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;

import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;
import com.tibco.xpd.n2.test.core.validation.AbstractN2BaseValidationTest;
import com.tibco.xpd.resources.util.ProjectUtil;

/**
 * JUnits to protect validation rules for global signal data mapper.
 *
 * @author sajain
 * @since Jul 11, 2019
 */
@SuppressWarnings("nls")
public class AceGlobalSignalDataMapperTest extends AbstractN2BaseValidationTest {

    public AceGlobalSignalDataMapperTest() {
        super(true);
    }

    /**
     * AceGlobalSignalDataMapperTest
     * 
     * @throws Exception
     */
    public void testAceGlobalSignalDataMapperTest() throws Exception {
        doTestValidations();
        return;
    }
    
    /**
     * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#configureProject(org.eclipse.core.resources.IProject)
     * 
     * @param testProject
     */
    @Override
    protected void configureProject(IProject testProject) {

        if (testProject.getName().equals("BPMProj01")) { //$NON-NLS-1$

            super.configureProject(testProject);
            /*
             * Add references to Global Signal Project and Business Data
             * Project.
             */
            IProject projectToREference1 = getTestProject("BOMProj01"); //$NON-NLS-1$
            IProject projectToREference2 = getTestProject("GSDProj01"); //$NON-NLS-1$

            try {
                ProjectUtil.addReferenceProject(testProject, projectToREference1);
                ProjectUtil.addReferenceProject(testProject, projectToREference2);
            } catch (CoreException e) {
                e.printStackTrace();
            }
        } else if (testProject.getName().equals("GSDProj01")) { //$NON-NLS-1$

            super.configureProject(testProject);

            TestUtil.addGlobalDestinationToProject("com.example.gsdproj01", //$NON-NLS-1$
                    "CE", //$NON-NLS-1$
                    testProject);

            /*
             * Add reference to Business Data Project.
             */
            IProject projectToREference1 = getTestProject("BOMProj01"); //$NON-NLS-1$

            try {
                ProjectUtil.addReferenceProject(testProject, projectToREference1);
            } catch (CoreException e) {
                e.printStackTrace();
            }
        }
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
    protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos() {
        ValidationsTestProblemMarkerInfo[] markerInfos = new ValidationsTestProblemMarkerInfo[] { 
            
                new ValidationsTestProblemMarkerInfo(
                        "/BPMProj01/Process Packages/BPMProj01.xpdl",
                        "bx.OnlyCorrelationPayloadMappingSupportedToTargetCorrelationData1",
                        "//@package/@processes.0/@activities.0/@event/@triggerResultSignal/@otherElements.0/@outputScriptDataMapper/@dataMappings.1",
                        "BPM  : Data Mapper: Correlation data (CorrelationField) can only be mapped from signal payload correlation parameters. (BPMProj01Process:CatchGlobalGlobalSignal:CorrelationField)", 
                        "Remove mapping"), 

                new ValidationsTestProblemMarkerInfo("/BPMProj01/Process Packages/BPMProj01.xpdl",
                        "bx.SourceCorrelationPayloadMustBeMapped2",
                        "//@package/@processes.0/@activities.0/@event/@triggerResultSignal/@otherElements.0/@outputScriptDataMapper/@dataMappings.0",
                        "BPM  : Data Mapper: Source correlation payload data (PayloadData) must be mapped to a correlation data field. (BPMProj01Process:CatchGlobalGlobalSignal:Field)",
                        "Remove mapping"),

                new ValidationsTestProblemMarkerInfo("/BPMProj01/Process Packages/BPMProj01.xpdl",
                        "abstractMappingRule.incompatibleTypes",
                        "//@package/@processes.0/@activities.0/@event/@triggerResultSignal/@otherElements.0/@outputScriptDataMapper/@dataMappings.3",
                        "BPM  : Data Mapper: The data types are incompatible for mapping 'PayloadData2' to 'Field3'. (BPMProj01Process:CatchGlobalGlobalSignal:Field3)",
                        "Remove mapping"),

                new ValidationsTestProblemMarkerInfo("/BPMProj01/Process Packages/BPMProj01.xpdl",
                        "abstractMappingRule.incompatibleTypes",
                        "//@package/@processes.0/@activities.0/@event/@triggerResultSignal/@otherElements.0/@outputScriptDataMapper/@dataMappings.2",
                        "BPM  : Data Mapper: The data types are incompatible for mapping 'PayloadData3' to 'Field2'. (BPMProj01Process:CatchGlobalGlobalSignal:Field2)",
                        "Remove mapping"),

                new ValidationsTestProblemMarkerInfo("/BPMProj01/Process Packages/BPMProj01.xpdl",
                        "abstractMappingRule.incompatibleTypes",
                        "//@package/@processes.0/@activities.1/@event/@triggerResultSignal/@otherElements.0/@inputScriptDataMapper/@dataMappings.3",
                        "BPM  : Data Mapper: The data types are incompatible for mapping 'SampleScript' to 'PayloadData5'. (BPMProj01Process:ThrowGlobalGlobalSignal:PayloadData5)",
                        "Remove mapping"),

                new ValidationsTestProblemMarkerInfo("/BPMProj01/Process Packages/BPMProj01.xpdl",
                        "abstractMappingRule.incompatibleTypes",
                        "//@package/@processes.0/@activities.1/@event/@triggerResultSignal/@otherElements.0/@inputScriptDataMapper/@dataMappings.0",
                        "BPM  : Data Mapper: The data types are incompatible for mapping 'Field3' to 'PayloadData2'. (BPMProj01Process:ThrowGlobalGlobalSignal:PayloadData2)",
                        "Remove mapping"),

                new ValidationsTestProblemMarkerInfo("/BPMProj01/Process Packages/BPMProj01.xpdl",
                        "abstractMappingRule.incompatibleTypes",
                        "//@package/@processes.0/@activities.1/@event/@triggerResultSignal/@otherElements.0/@outputScriptDataMapper/@dataMappings.1",
                        "BPM  : Data Mapper: The data types are incompatible for mapping 'Field2' to 'PayloadData4'. (BPMProj01Process:ThrowGlobalGlobalSignal:PayloadData4)",
                        "Remove mapping"),

                new ValidationsTestProblemMarkerInfo("/BPMProj01/Process Packages/BPMProj01.xpdl",
                        "abstractMappingRule.incompatibleTypes",
                        "//@package/@processes.0/@activities.0/@event/@triggerResultSignal/@otherElements.0/@outputScriptDataMapper/@dataMappings.0",
                        "BPM  : Data Mapper: Source correlation payload data (PayloadData) must be mapped to a correlation data field. (BPMProj01Process:CatchGlobalGlobalSignal:Field)",
                        "Remove mapping")
        };
        return markerInfos;
    }

    @Override
    protected String getTestName() {
        return "AceGlobalSignalDataMapperTest";
    }

    @Override
    protected String getTestPlugInId() {
        return "com.tibco.xpd.sce.test";
    }

    @Override
    protected TestResourceInfo[] getTestResources() {
        TestResourceInfo[] testResources = new TestResourceInfo[] {
                new TestResourceInfo("resources/AceGlobalSignalDataMapperTest",
                        "BOMProj01/Business Objects{bom}/BOMProj01.bom"),
                new TestResourceInfo("resources/AceGlobalSignalDataMapperTest",
                        "GSDProj01/Global Signal Definitions{gsd}/GSDProj01.gsd"),
                new TestResourceInfo("resources/AceGlobalSignalDataMapperTest",
                        "BPMProj01/Process Packages{processes}/BPMProj01.xpdl")
        };

        return testResources;
    }
}