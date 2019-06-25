/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.sce.tests.validation;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.ResourcesPlugin;

import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;
import com.tibco.xpd.n2.test.core.validation.AbstractN2BaseValidationTest;
import com.tibco.xpd.resources.util.ProjectImporter;

/**
 * AceIntegerWorkItemAttributeMappingTest
 * <p>
 * Sid ACE-1755 ensure that Only Fixed Point Number types with zero decimal
 * places defined (not float (no decimla places) or different number of decimals
 * defined) can be mapped to Work Item Attributes.
 * 
 * Also includes re-checking that other restrictions that should be in place are
 * still in place - see
 * ProceDataToWorkItemAttributeMappingRule.checkTypeCompatibility() for more
 * detail.
 * </p>
 *
 * @author aallway
 * @since 25 Jun 2019
 */
public class AceIntegerWorkItemAttributeMappingTest
        extends AbstractN2BaseValidationTest {

    private ProjectImporter projectImporter;

    public AceIntegerWorkItemAttributeMappingTest() {
        super(true);
    }

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#customTestResourceSetup()
     *
     */
    @Override
    protected void customTestResourceSetup() {
        /*
         * This test doesn't create files via the normal getTestResources()
         * because we want to import and migrate a whole project from AMX BPM.
         */
        projectImporter = TestUtil.importProjectsFromZip(
                "com.tibco.xpd.sce.test", //$NON-NLS-1$
                new String[] {
                        "resources/AceIntegerWorkItemAttributeMappingTest/MapNumberToIntegerTestData/", //$NON-NLS-1$
                        "resources/AceIntegerWorkItemAttributeMappingTest/MapNumberToIntegerTestREST/", //$NON-NLS-1$
                        "resources/AceIntegerWorkItemAttributeMappingTest/MapNumberToIntegerTestWLF/", //$NON-NLS-1$
                        "resources/AceIntegerWorkItemAttributeMappingTest/MapNumberToIntegerTestProcess/" }, //$NON-NLS-1$
                new String[] { "MapNumberToIntegerTestData", //$NON-NLS-1$
                        "MapNumberToIntegerTestREST", //$NON-NLS-1$
                        "MapNumberToIntegerTestWLF", //$NON-NLS-1$
                        "MapNumberToIntegerTestProcess" }); //$NON-NLS-1$

        assertTrue(
                "Failed to load projects from \"resources/AceIntegerWorkItemAttributeMappingTest/", //$NON-NLS-1$
                projectImporter != null);
    }

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#tearDown()
     *
     * @throws Exception
     */
    @Override
    protected void tearDown() throws Exception {
        projectImporter.performDelete();
        super.tearDown();
    }

    /**
     * AceIntegerWorkItemAttributeMappingTest
     * 
     * @throws Exception
     */
    public void testAceIntegerWorkItemAttributeMappingTest() throws Exception {
        doTestValidations();

        /*
         * Check that the XPDL that has valid use of class factories and enum
         * packages i free from error markers.
         */
        IFile noErrorsXpdl = ResourcesPlugin.getWorkspace().getRoot()
                .getProject("MapNumberToIntegerTestProcess") //$NON-NLS-1$
                .getFolder("Process Packages") //$NON-NLS-1$
                .getFile("ValidProcess.xpdl"); //$NON-NLS-1$

        assertTrue("ValidProcess.xpdl should exist.", //$NON-NLS-1$
                noErrorsXpdl != null && noErrorsXpdl.isAccessible());

        List<ValidationsTestProblemMarkerInfo> problemMarkers =
                getProblemMarkers(noErrorsXpdl);

        for (ValidationsTestProblemMarkerInfo markerInfo : problemMarkers) {
            assertFalse(
                    "ValidProcess.xpdl should not have error level problem markers (has at least one)", //$NON-NLS-1$
                    markerInfo.getSourceMarker().getAttribute(IMarker.SEVERITY,
                            -1) == IMarker.SEVERITY_ERROR);

        }
        return;
    }

    @Override
    protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos() {
        ValidationsTestProblemMarkerInfo[] markerInfos =
                new ValidationsTestProblemMarkerInfo[] {

                        new ValidationsTestProblemMarkerInfo(
                                "/MapNumberToIntegerTestProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
                                "wlf.mapping.numberToInteger", //$NON-NLS-1$
                                "//@package/@processes.0/@otherElements.0/@dataWorkItemAttributeMapping.0", //$NON-NLS-1$
                                "BPM  : Work Item Attribute Mapping: Only fixed point numbers with decimal places set to zero can be mapped to integer attribute 'An integer attribute (attribute1)'. (InvalidProcess_ProcessFields:)", //$NON-NLS-1$
                                "Remove mapping"), //$NON-NLS-1$

                        new ValidationsTestProblemMarkerInfo(
                                "/MapNumberToIntegerTestProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
                                "wlf.mapping.numberToInteger", //$NON-NLS-1$
                                "//@package/@processes.0/@otherElements.0/@dataWorkItemAttributeMapping.1", //$NON-NLS-1$
                                "BPM  : Work Item Attribute Mapping: Only fixed point numbers with decimal places set to zero can be mapped to integer attribute 'attribute15'. (InvalidProcess_ProcessFields:)", //$NON-NLS-1$
                                "Remove mapping"), //$NON-NLS-1$

                        new ValidationsTestProblemMarkerInfo(
                                "/MapNumberToIntegerTestProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
                                "wlf.mapping.numberToInteger", //$NON-NLS-1$
                                "//@package/@processes.1/@otherElements.0/@dataWorkItemAttributeMapping.0", //$NON-NLS-1$
                                "BPM  : Work Item Attribute Mapping: Only fixed point numbers with decimal places set to zero can be mapped to integer attribute 'An integer attribute (attribute1)'. (InvalidProcess_BOMAttributes:)", //$NON-NLS-1$
                                "Remove mapping"), //$NON-NLS-1$

                        new ValidationsTestProblemMarkerInfo(
                                "/MapNumberToIntegerTestProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
                                "wlf.mapping.numberToInteger", //$NON-NLS-1$
                                "//@package/@processes.1/@otherElements.0/@dataWorkItemAttributeMapping.1", //$NON-NLS-1$
                                "BPM  : Work Item Attribute Mapping: Only fixed point numbers with decimal places set to zero can be mapped to integer attribute 'attribute15'. (InvalidProcess_BOMAttributes:)", //$NON-NLS-1$
                                "Remove mapping"), //$NON-NLS-1$

                        new ValidationsTestProblemMarkerInfo(
                                "/MapNumberToIntegerTestProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
                                "wlf.mapping.numberToInteger", //$NON-NLS-1$
                                "//@package/@processes.2/@otherElements.0/@dataWorkItemAttributeMapping.0", //$NON-NLS-1$
                                "BPM  : Work Item Attribute Mapping: Only fixed point numbers with decimal places set to zero can be mapped to integer attribute 'An integer attribute (attribute1)'. (InvalidProcess_BOMAttributes2:)", //$NON-NLS-1$
                                "Remove mapping"), //$NON-NLS-1$

                        new ValidationsTestProblemMarkerInfo(
                                "/MapNumberToIntegerTestProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
                                "wlf.mapping.numberToInteger", //$NON-NLS-1$
                                "//@package/@processes.2/@otherElements.0/@dataWorkItemAttributeMapping.1", //$NON-NLS-1$
                                "BPM  : Work Item Attribute Mapping: Only fixed point numbers with decimal places set to zero can be mapped to integer attribute 'attribute15'. (InvalidProcess_BOMAttributes2:)", //$NON-NLS-1$
                                "Remove mapping"), //$NON-NLS-1$

                        new ValidationsTestProblemMarkerInfo(
                                "/MapNumberToIntegerTestProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
                                "abstractMappingRule.incompatibleTypes", //$NON-NLS-1$
                                "//@package/@processes.2/@otherElements.0/@dataWorkItemAttributeMapping.2", //$NON-NLS-1$
                                "BPM  : Work Item Attribute Mapping: The data types are incompatible for mapping 'BOMField.textAttrib' to 'attribute6'. (InvalidProcess_BOMAttributes2:)", //$NON-NLS-1$
                                "Remove mapping"), //$NON-NLS-1$

                        new ValidationsTestProblemMarkerInfo(
                                "/MapNumberToIntegerTestProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
                                "abstractMappingRule.incompatibleTypes", //$NON-NLS-1$
                                "//@package/@processes.2/@otherElements.0/@dataWorkItemAttributeMapping.3", //$NON-NLS-1$
                                "BPM  : Work Item Attribute Mapping: The data types are incompatible for mapping 'BOMField.dateTimeAttrib' to 'attribute5'. (InvalidProcess_BOMAttributes2:)", //$NON-NLS-1$
                                "Remove mapping"), //$NON-NLS-1$

                        new ValidationsTestProblemMarkerInfo(
                                "/MapNumberToIntegerTestProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
                                "abstractMappingRule.incompatibleTypes", //$NON-NLS-1$
                                "//@package/@processes.2/@otherElements.0/@dataWorkItemAttributeMapping.4", //$NON-NLS-1$
                                "BPM  : Work Item Attribute Mapping: The data types are incompatible for mapping 'BOMField.enumAttribute' to 'attribute17'. (InvalidProcess_BOMAttributes2:)", //$NON-NLS-1$
                                "Remove mapping"), //$NON-NLS-1$

                        new ValidationsTestProblemMarkerInfo(
                                "/MapNumberToIntegerTestProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
                                "wlf.mapping.numberToInteger", //$NON-NLS-1$
                                "//@package/@processes.3/@otherElements.0/@dataWorkItemAttributeMapping.0", //$NON-NLS-1$
                                "BPM  : Work Item Attribute Mapping: Only fixed point numbers with decimal places set to zero can be mapped to integer attribute 'attribute15'. (InvalidProcess_ProcessFields2:)", //$NON-NLS-1$
                                "Remove mapping"), //$NON-NLS-1$

                        new ValidationsTestProblemMarkerInfo(
                                "/MapNumberToIntegerTestProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
                                "wlf.mapping.numberToInteger", //$NON-NLS-1$
                                "//@package/@processes.3/@otherElements.0/@dataWorkItemAttributeMapping.1", //$NON-NLS-1$
                                "BPM  : Work Item Attribute Mapping: Only fixed point numbers with decimal places set to zero can be mapped to integer attribute 'An integer attribute (attribute1)'. (InvalidProcess_ProcessFields2:)", //$NON-NLS-1$
                                "Remove mapping"), //$NON-NLS-1$

                        new ValidationsTestProblemMarkerInfo(
                                "/MapNumberToIntegerTestProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
                                "abstractMappingRule.incompatibleTypes", //$NON-NLS-1$
                                "//@package/@processes.3/@otherElements.0/@dataWorkItemAttributeMapping.2", //$NON-NLS-1$
                                "BPM  : Work Item Attribute Mapping: The data types are incompatible for mapping 'TextProcessField' to 'attribute5'. (InvalidProcess_ProcessFields2:)", //$NON-NLS-1$
                                "Remove mapping"), //$NON-NLS-1$

                        new ValidationsTestProblemMarkerInfo(
                                "/MapNumberToIntegerTestProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
                                "abstractMappingRule.incompatibleTypes", //$NON-NLS-1$
                                "//@package/@processes.3/@otherElements.0/@dataWorkItemAttributeMapping.3", //$NON-NLS-1$
                                "BPM  : Work Item Attribute Mapping: The data types are incompatible for mapping 'DateTimeProcessField' to 'attribute17'. (InvalidProcess_ProcessFields2:)", //$NON-NLS-1$
                                "Remove mapping"), //$NON-NLS-1$

                };
        return markerInfos;
    }

    @Override
    protected String getTestName() {
        return "AceIntegerWorkItemAttributeMappingTest"; //$NON-NLS-1$
    }

    @Override
    protected String getTestPlugInId() {
        return "com.tibco.xpd.sce.test"; //$NON-NLS-1$
    }

    @Override
    protected TestResourceInfo[] getTestResources() {
        TestResourceInfo[] testResources = new TestResourceInfo[] {};

        return testResources;
    }

}
