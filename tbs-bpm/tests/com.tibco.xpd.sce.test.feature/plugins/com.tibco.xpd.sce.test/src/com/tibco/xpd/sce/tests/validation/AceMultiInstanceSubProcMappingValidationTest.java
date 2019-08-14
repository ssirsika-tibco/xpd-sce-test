/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.sce.tests.validation;

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
public class AceMultiInstanceSubProcMappingValidationTest
        extends AbstractN2BaseValidationTest {

    private ProjectImporter projectImporter;

    public AceMultiInstanceSubProcMappingValidationTest() {
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
                        "resources/WrappedProcessDataTests/DataWrappingData/", //$NON-NLS-1$
                        "resources/WrappedProcessDataTests/MultiInstanceMappingValidationTest/" }, //$NON-NLS-1$
                new String[] { "DataWrappingData", //$NON-NLS-1$
                        "MultiInstanceMappingValidationTest" }); //$NON-NLS-1$

        assertTrue(
                "Failed to load projects from \"resources/WrappedProcessDataTests/", //$NON-NLS-1$
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
    }

    @Override
    protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos() {
        ValidationsTestProblemMarkerInfo[] markerInfos =
                new ValidationsTestProblemMarkerInfo[] {
                        new ValidationsTestProblemMarkerInfo(
                                "/MultiInstanceMappingValidationTest/Process Packages/MultiInstanceMappingValidationTest.xpdl", //$NON-NLS-1$
                                "ace.single2multi.mapping.target.must.be.append", //$NON-NLS-1$
                                "//@package/@processes.0/@activities.2/@implementation/@otherElements.1/@dataMappings.0", //$NON-NLS-1$
                                "BPM  : Sub-process instances can complete in any order so output must be appended to target list 'ComplexArrayToSingleField'. For single to multi-instance output mappings, target list must be configured 'Append to target list'. (SimpleMainProcess:Singletomultitargetnotappend:ComplexArrayToSingleField)", //$NON-NLS-1$
                                "Set list mapping strategy to 'Append to target list'"), //$NON-NLS-1$

                        new ValidationsTestProblemMarkerInfo(
                                "/MultiInstanceMappingValidationTest/Process Packages/MultiInstanceMappingValidationTest.xpdl", //$NON-NLS-1$
                                "ace.single2multi.mapping.target.must.be.append", //$NON-NLS-1$
                                "//@package/@processes.0/@activities.2/@implementation/@otherElements.1/@dataMappings.1", //$NON-NLS-1$
                                "BPM  : Sub-process instances can complete in any order so output must be appended to target list 'TextArrayToSingleField'. For single to multi-instance output mappings, target list must be configured 'Append to target list'. (SimpleMainProcess:Singletomultitargetnotappend:TextArrayToSingleField)", //$NON-NLS-1$
                                "Set list mapping strategy to 'Append to target list'"), //$NON-NLS-1$

                        new ValidationsTestProblemMarkerInfo(
                                "/MultiInstanceMappingValidationTest/Process Packages/MultiInstanceMappingValidationTest.xpdl", //$NON-NLS-1$
                                "ace.single2multi.mapping.target.must.be.append", //$NON-NLS-1$
                                "//@package/@processes.0/@activities.2/@implementation/@otherElements.1/@dataMappings.2", //$NON-NLS-1$
                                "BPM  : Sub-process instances can complete in any order so output must be appended to target list 'ComplexField.textList'. For single to multi-instance output mappings, target list must be configured 'Append to target list'. (SimpleMainProcess:Singletomultitargetnotappend:ComplexField.textList)", //$NON-NLS-1$
                                "Set list mapping strategy to 'Append to target list'"), //$NON-NLS-1$

                        new ValidationsTestProblemMarkerInfo(
                                "/MultiInstanceMappingValidationTest/Process Packages/MultiInstanceMappingValidationTest.xpdl", //$NON-NLS-1$
                                "abstractMappingRule.multiToSingleUnsupported", //$NON-NLS-1$
                                "//@package/@processes.0/@activities.3/@implementation/@otherElements.0/@dataMappings.0", //$NON-NLS-1$
                                "BPM  : Map To Sub-Process: Multiple to single instance data mapping is not supported for 'TextArrayToSingleField' to 'ComplexArrayParameter.text'. (SimpleMainProcess:Incorrectarraylevelformultisingle:ComplexArrayParameter.text)", //$NON-NLS-1$
                                ""), //$NON-NLS-1$

                        new ValidationsTestProblemMarkerInfo(
                                "/MultiInstanceMappingValidationTest/Process Packages/MultiInstanceMappingValidationTest.xpdl", //$NON-NLS-1$
                                "datamapper.arrayMapping.levelMismatch", //$NON-NLS-1$
                                "//@package/@processes.0/@activities.3/@implementation/@otherElements.0/@dataMappings.1", //$NON-NLS-1$
                                "BPM  : Mappings to/from nested arrays must be mapped from the same array nesting level in the source and target tree. (SimpleMainProcess:Incorrectarraylevelformultisingle:ComplexArrayParameter.complexChild)", //$NON-NLS-1$
                                ""), //$NON-NLS-1$

                        new ValidationsTestProblemMarkerInfo(
                                "/MultiInstanceMappingValidationTest/Process Packages/MultiInstanceMappingValidationTest.xpdl", //$NON-NLS-1$
                                "datamapper.arrayMapping.levelMismatch", //$NON-NLS-1$
                                "//@package/@processes.0/@activities.3/@implementation/@otherElements.1/@dataMappings.0", //$NON-NLS-1$
                                "BPM  : Mappings to/from nested arrays must be mapped from the same array nesting level in the source and target tree. (SimpleMainProcess:Incorrectarraylevelformultisingle:ComplexArrayToSingleField.text)", //$NON-NLS-1$
                                ""), //$NON-NLS-1$

                        new ValidationsTestProblemMarkerInfo(
                                "/MultiInstanceMappingValidationTest/Process Packages/MultiInstanceMappingValidationTest.xpdl", //$NON-NLS-1$
                                "datamapper.arrayMapping.levelMismatch", //$NON-NLS-1$
                                "//@package/@processes.0/@activities.3/@implementation/@otherElements.1/@dataMappings.1", //$NON-NLS-1$
                                "BPM  : Mappings to/from nested arrays must be mapped from the same array nesting level in the source and target tree. (SimpleMainProcess:Incorrectarraylevelformultisingle:ComplexArrayToSingleField.textList)", //$NON-NLS-1$
                                ""), //$NON-NLS-1$

                        new ValidationsTestProblemMarkerInfo(
                                "/MultiInstanceMappingValidationTest/Process Packages/MultiInstanceMappingValidationTest.xpdl", //$NON-NLS-1$
                                "abstractMappingRule.multiToSingleUnsupported", //$NON-NLS-1$
                                "//@package/@processes.0/@activities.3/@implementation/@otherElements.1/@dataMappings.2", //$NON-NLS-1$
                                "BPM  : Map From Sub-Process: Multiple to single instance data mapping is not supported for 'ComplexArrayParameter' to 'ComplexField'. (SimpleMainProcess:Incorrectarraylevelformultisingle:ComplexField)", //$NON-NLS-1$
                                ""), //$NON-NLS-1$

                        new ValidationsTestProblemMarkerInfo(
                                "/MultiInstanceMappingValidationTest/Process Packages/MultiInstanceMappingValidationTest.xpdl", //$NON-NLS-1$
                                "datamapper.arrayMapping.levelMismatch", //$NON-NLS-1$
                                "//@package/@processes.0/@activities.3/@implementation/@otherElements.1/@dataMappings.2", //$NON-NLS-1$
                                "BPM  : Mappings to/from nested arrays must be mapped from the same array nesting level in the source and target tree. (SimpleMainProcess:Incorrectarraylevelformultisingle:ComplexField)", //$NON-NLS-1$
                                ""), //$NON-NLS-1$

                };
        return markerInfos;
    }

    @Override
    protected String getTestName() {
        return "AceMultiInstanceSubProcMappingValidationTest"; //$NON-NLS-1$
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
