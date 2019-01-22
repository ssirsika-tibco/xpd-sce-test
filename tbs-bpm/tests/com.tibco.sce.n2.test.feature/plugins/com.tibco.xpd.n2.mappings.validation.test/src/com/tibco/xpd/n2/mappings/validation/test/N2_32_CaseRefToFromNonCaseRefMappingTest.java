/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.n2.mappings.validation.test;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;

import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.validations.AbstractBaseValidationTest;
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;
import com.tibco.xpd.resources.util.ProjectUtil;

/**
 * JUnit test to protect the fix gone under XPD-7929 which was to validate
 * against case-ref to non-case-ref mappings.
 * 
 * @author sajain
 * @since 16-Oct-2015
 */
public class N2_32_CaseRefToFromNonCaseRefMappingTest extends
        AbstractBaseValidationTest {

    public N2_32_CaseRefToFromNonCaseRefMappingTest() {
        super(true);
    }

    /**
     * N2_32_CaseRefToFromNonCaseRefMappingTest
     * 
     * @throws Exception
     */
    public void testN2_32_CaseRefToFromNonCaseRefMappingTest() throws Exception {
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

        if (testProject.getName().equals("N232_Bpmproj")) { //$NON-NLS-1$          
            /*
             * Add reference to BDP project.
             */
            IProject projectToREference = getTestProject("N232_BdpProj"); //$NON-NLS-1$

            try {
                ProjectUtil
                        .addReferenceProject(testProject, projectToREference);
            } catch (CoreException e) {
                e.printStackTrace();
            }
        }

        super.configureProject(testProject);
    }

    @Override
    protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos() {
        ValidationsTestProblemMarkerInfo[] markerInfos =
                new ValidationsTestProblemMarkerInfo[] {

                        new ValidationsTestProblemMarkerInfo(
                                "/N232_Bpmproj/Process Packages/N232_Bpmproj.xpdl", //$NON-NLS-1$ 
                                "abstractMappingRule.incompatibleTypes", //$NON-NLS-1$ 
                                "//@package/@processes.0/@activities.2/@implementation/@taskScript/@script/@mixed.0/@dataMappings.0", //$NON-NLS-1$ 
                                "Process Manager  : Data Mapper: The data types are incompatible for mapping 'Script01' to 'CaseClassField01'. (N232_BpmprojProcess:ScriptTask:CaseClassField01)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/N232_Bpmproj/Process Packages/N232_Bpmproj.xpdl", //$NON-NLS-1$ 
                                "abstractMappingRule.incompatibleTypes", //$NON-NLS-1$ 
                                "//@package/@processes.0/@activities.2/@implementation/@taskScript/@script/@mixed.0/@dataMappings.1", //$NON-NLS-1$ 
                                "Process Manager  : Data Mapper: The data types are incompatible for mapping 'Script02' to 'BOMClassField02.attribute1'. (N232_BpmprojProcess:ScriptTask:BOMClassField02.attribute1)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/N232_Bpmproj/Process Packages/N232_Bpmproj.xpdl", //$NON-NLS-1$ 
                                "abstractMappingRule.incompatibleTypes", //$NON-NLS-1$ 
                                "//@package/@processes.0/@activities.2/@implementation/@taskScript/@script/@mixed.0/@dataMappings.2", //$NON-NLS-1$ 
                                "Process Manager  : Data Mapper: The data types are incompatible for mapping 'BOMClassField02.attribute1' to 'CaseField02'. (N232_BpmprojProcess:ScriptTask:CaseField02)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/N232_Bpmproj/Process Packages/N232_Bpmproj.xpdl", //$NON-NLS-1$ 
                                "abstractMappingRule.incompatibleTypes", //$NON-NLS-1$ 
                                "//@package/@processes.0/@activities.2/@implementation/@taskScript/@script/@mixed.0/@dataMappings.3", //$NON-NLS-1$ 
                                "Process Manager  : Data Mapper: The data types are incompatible for mapping 'CaseClassField01' to 'CaseField01'. (N232_BpmprojProcess:ScriptTask:CaseField01)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/N232_Bpmproj/Process Packages/N232_Bpmproj.xpdl", //$NON-NLS-1$ 
                                "abstractMappingRule.incompatibleTypes", //$NON-NLS-1$ 
                                "//@package/@processes.0/@activities.2/@implementation/@taskScript/@script/@mixed.0/@dataMappings.4", //$NON-NLS-1$ 
                                "Process Manager  : Data Mapper: The data types are incompatible for mapping 'CaseField01' to 'BOMClassField01.attribute1'. (N232_BpmprojProcess:ScriptTask:BOMClassField01.attribute1)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/N232_Bpmproj/Process Packages/N232_Bpmproj.xpdl", //$NON-NLS-1$ 
                                "abstractMappingRule.incompatibleTypes", //$NON-NLS-1$ 
                                "//@package/@processes.0/@activities.2/@implementation/@taskScript/@script/@mixed.0/@dataMappings.5", //$NON-NLS-1$ 
                                "Process Manager  : Data Mapper: The data types are incompatible for mapping 'CaseField02' to 'CaseClassField02'. (N232_BpmprojProcess:ScriptTask:CaseClassField02)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                };
        return markerInfos;
    }

    @Override
    protected String getTestName() {
        return "N2_32_CaseRefToFromNonCaseRefMappingTest"; //$NON-NLS-1$
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
                                "resources/N232CaseRefToFromNonCaseRefMappingTest", "N232_BdpProj/Business Objects{bom}/N232_BdpProj.bom"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/N232CaseRefToFromNonCaseRefMappingTest", "N232_Bpmproj/Process Packages{processes}/N232_Bpmproj.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
                };

        return testResources;
    }

}
