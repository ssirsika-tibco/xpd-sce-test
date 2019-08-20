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
 * AceBomFactoryValidationsTest
 * <p>
 * Tests that the new "factory" and "pkg" wrapper classes for BOM class creation
 * factory and Enumeration packages function correctly. We can tell this by
 * checking that validations (which work of the same thing as content assist)
 * are raised or not raised according to valid/invalid usage.
 * 
 * @author aallway
 * @since 20 Jun 19
 */
public class AceBomFactoryValidationsTest extends AbstractN2BaseValidationTest {

    private ProjectImporter projectImporter;

    public AceBomFactoryValidationsTest() {
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
                        "resources/AceBomFactoryValidationsTest/DataFactoryTestsData2/", //$NON-NLS-1$
                        "resources/AceBomFactoryValidationsTest/DataFactoryTestsData1/", //$NON-NLS-1$
                        "resources/AceBomFactoryValidationsTest/DataFactoryTests/" }, //$NON-NLS-1$
                new String[] { "DataFactoryTestsData2", "DataFactoryTestsData1", //$NON-NLS-1$ //$NON-NLS-2$
                        "DataFactoryTests" }); //$NON-NLS-1$

        assertTrue(
                "Failed to load projects from \"resources/AceBomFactoryValidationsTest/", //$NON-NLS-1$
                projectImporter != null);
    }

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#tearDown()
     *
     * @throws Exception
     */
    @Override
    protected void tearDown() throws Exception {
        if (projectImporter != null) {
            projectImporter.performDelete();
        }
        super.tearDown();
    }

    /**
     * AceBomFactoryValidationsTest
     * 
     * @throws Exception
     */
    public void testAceBomFactoryValidationsTest() throws Exception {
        doTestValidations();

        /*
         * Check that the XPDL that has valid use of class factories and enum
         * packages is free from error markers.
         */
        IFile noErrorsXpdl = ResourcesPlugin.getWorkspace().getRoot()
                .getProject("DataFactoryTests").getFolder("Process Packages") //$NON-NLS-1$ //$NON-NLS-2$
                .getFile("CheckCorrectUsage.xpdl"); //$NON-NLS-1$

        assertTrue("CheckCorrectUsage.xpdl should exist.", //$NON-NLS-1$
                noErrorsXpdl != null && noErrorsXpdl.isAccessible());

        List<ValidationsTestProblemMarkerInfo> problemMarkers =
                getProblemMarkers(noErrorsXpdl);

        for (ValidationsTestProblemMarkerInfo markerInfo : problemMarkers) {
            assertFalse(
                    "CheckCorrectUsage.xpdl should not have error level problem markers (has at least one)", //$NON-NLS-1$
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
                                "/DataFactoryTests/Process Packages/CheckIncorrectUsage.xpdl", //$NON-NLS-1$
                                "abstractMappingRule.incompatibleTypes", //$NON-NLS-1$
                                "//@package/@processes.0/@activities.3/@implementation/@otherElements.0/@dataMappings.0", //$NON-NLS-1$
                                "BPM  : Map To Sub-Process: The data types are incompatible for mapping 'Script1' to 'DataType2param'. (CheckIncorrectUsageProcess:Subprocess:DataType2param)", //$NON-NLS-1$
                                ""), //$NON-NLS-1$

                        new ValidationsTestProblemMarkerInfo(
                                "/DataFactoryTests/Process Packages/CheckIncorrectUsage.xpdl", //$NON-NLS-1$
                                "abstractMappingRule.incompatibleTypes", //$NON-NLS-1$
                                "//@package/@processes.0/@activities.3/@implementation/@otherElements.0/@dataMappings.1", //$NON-NLS-1$
                                "BPM  : Map To Sub-Process: The data types are incompatible for mapping 'Script2' to 'DataType1param.coloursEnum'. (CheckIncorrectUsageProcess:Subprocess:DataType1param.coloursEnum)", //$NON-NLS-1$
                                ""), //$NON-NLS-1$

                        new ValidationsTestProblemMarkerInfo(
                                "/DataFactoryTests/Process Packages/CheckIncorrectUsage.xpdl", //$NON-NLS-1$
                                "abstractMappingRule.unmappedRequiredTarget", //$NON-NLS-1$
                                "_0I74YZN4Eem5T98uS32IsA", //$NON-NLS-1$
                                "BPM  : Map To Sub-Process: the mandatory target 'DataType1param.complexChild' must be mapped to complete the target data. (CheckIncorrectUsageProcess:Subprocess)", //$NON-NLS-1$
                                ""), //$NON-NLS-1$

                        new ValidationsTestProblemMarkerInfo(
                                "/DataFactoryTests/Process Packages/CheckIncorrectUsage.xpdl", //$NON-NLS-1$
                                "bx.validateScriptTask", //$NON-NLS-1$
                                "_C75Q4ZJ9EemsHsINLISM4w", //$NON-NLS-1$
                                "BPM  : At Line:12 column:75, Assignment of Colours from com_example_datafactorytestsdata1_Colours is not supported (CheckIncorrectUsageProcess:ScriptTask)", //$NON-NLS-1$
                                ""), //$NON-NLS-1$

                        new ValidationsTestProblemMarkerInfo(
                                "/DataFactoryTests/Process Packages/CheckIncorrectUsage.xpdl", //$NON-NLS-1$
                                "bx.validateScriptTask", //$NON-NLS-1$
                                "_C75Q4ZJ9EemsHsINLISM4w", //$NON-NLS-1$
                                "BPM  : At Line:15 column:84, Property RED is invalid for the current context (CheckIncorrectUsageProcess:ScriptTask)", //$NON-NLS-1$
                                ""), //$NON-NLS-1$

                        new ValidationsTestProblemMarkerInfo(
                                "/DataFactoryTests/Process Packages/CheckIncorrectUsage.xpdl", //$NON-NLS-1$
                                "bx.validateScriptTask", //$NON-NLS-1$
                                "_C75Q4ZJ9EemsHsINLISM4w", //$NON-NLS-1$
                                "BPM  : At Line:16 column:86, Method get is invalid for the current context (CheckIncorrectUsageProcess:ScriptTask)", //$NON-NLS-1$
                                ""), //$NON-NLS-1$

                        new ValidationsTestProblemMarkerInfo(
                                "/DataFactoryTests/Process Packages/CheckIncorrectUsage.xpdl", //$NON-NLS-1$
                                "bx.validateScriptTask", //$NON-NLS-1$
                                "_C75Q4ZJ9EemsHsINLISM4w", //$NON-NLS-1$
                                "BPM  : At Line:19 column:79, Assignment of Colours from Colours is not supported (CheckIncorrectUsageProcess:ScriptTask)", //$NON-NLS-1$
                                ""), //$NON-NLS-1$

                        new ValidationsTestProblemMarkerInfo(
                                "/DataFactoryTests/Process Packages/CheckIncorrectUsage.xpdl", //$NON-NLS-1$
                                "bx.validateScriptTask", //$NON-NLS-1$
                                "_C75Q4ZJ9EemsHsINLISM4w", //$NON-NLS-1$
                                "BPM  : At Line:2 column:78, Variable com_example_datafactorytestsdata1_Factory not defined or is not associated in the task interface. (CheckIncorrectUsageProcess:ScriptTask)", //$NON-NLS-1$
                                ""), //$NON-NLS-1$

                        new ValidationsTestProblemMarkerInfo(
                                "/DataFactoryTests/Process Packages/CheckIncorrectUsage.xpdl", //$NON-NLS-1$
                                "bx.validateScriptTask", //$NON-NLS-1$
                                "_C75Q4ZJ9EemsHsINLISM4w", //$NON-NLS-1$
                                "BPM  : At Line:20 column:78, Assignment of com.example.datafactorytestsdata1.data from com.example.datafactorytestsdata2.data is not supported (CheckIncorrectUsageProcess:ScriptTask)", //$NON-NLS-1$
                                ""), //$NON-NLS-1$

                        new ValidationsTestProblemMarkerInfo(
                                "/DataFactoryTests/Process Packages/CheckIncorrectUsage.xpdl", //$NON-NLS-1$
                                "bx.validateScriptTask", //$NON-NLS-1$
                                "_C75Q4ZJ9EemsHsINLISM4w", //$NON-NLS-1$
                                "BPM  : At Line:3 column:75, Variable com_example_datafactorytestsdata1_Colours not defined or is not associated in the task interface. (CheckIncorrectUsageProcess:ScriptTask)", //$NON-NLS-1$
                                ""), //$NON-NLS-1$

                        new ValidationsTestProblemMarkerInfo(
                                "/DataFactoryTests/Process Packages/CheckIncorrectUsage.xpdl", //$NON-NLS-1$
                                "bx.validateScriptTask", //$NON-NLS-1$
                                "_C75Q4ZJ9EemsHsINLISM4w", //$NON-NLS-1$
                                "BPM  : At Line:6 column:10, Assignment of data from String is not supported (CheckIncorrectUsageProcess:ScriptTask)", //$NON-NLS-1$
                                ""), //$NON-NLS-1$

                        new ValidationsTestProblemMarkerInfo(
                                "/DataFactoryTests/Process Packages/CheckIncorrectUsage.xpdl", //$NON-NLS-1$
                                "bx.validateScriptTask", //$NON-NLS-1$
                                "_C75Q4ZJ9EemsHsINLISM4w", //$NON-NLS-1$
                                "BPM  : At Line:7 column:13, Assignment of factory from String is not supported (CheckIncorrectUsageProcess:ScriptTask)", //$NON-NLS-1$
                                ""), //$NON-NLS-1$

                        new ValidationsTestProblemMarkerInfo(
                                "/DataFactoryTests/Process Packages/CheckIncorrectUsage.xpdl", //$NON-NLS-1$
                                "bx.validateScriptTask", //$NON-NLS-1$
                                "_C75Q4ZJ9EemsHsINLISM4w", //$NON-NLS-1$
                                "BPM  : At Line:7 column:13, Assignment of read only elements is not allowed (CheckIncorrectUsageProcess:ScriptTask)", //$NON-NLS-1$
                                ""), //$NON-NLS-1$

                        new ValidationsTestProblemMarkerInfo(
                                "/DataFactoryTests/Process Packages/CheckIncorrectUsage.xpdl", //$NON-NLS-1$
                                "bx.validateScriptTask", //$NON-NLS-1$
                                "_C75Q4ZJ9EemsHsINLISM4w", //$NON-NLS-1$
                                "BPM  : At Line:8 column:9, Assignment of pkg from String is not supported (CheckIncorrectUsageProcess:ScriptTask)", //$NON-NLS-1$
                                ""), //$NON-NLS-1$

                        new ValidationsTestProblemMarkerInfo(
                                "/DataFactoryTests/Process Packages/CheckIncorrectUsage.xpdl", //$NON-NLS-1$
                                "bx.validateScriptTask", //$NON-NLS-1$
                                "_C75Q4ZJ9EemsHsINLISM4w", //$NON-NLS-1$
                                "BPM  : At Line:8 column:9, Assignment of read only elements is not allowed (CheckIncorrectUsageProcess:ScriptTask)", //$NON-NLS-1$
                                ""), //$NON-NLS-1$

                        new ValidationsTestProblemMarkerInfo(
                                "/DataFactoryTests/Process Packages/CheckIncorrectUsage.xpdl", //$NON-NLS-1$
                                "bx.validateScriptTask", //$NON-NLS-1$
                                "_C75Q4ZJ9EemsHsINLISM4w", //$NON-NLS-1$
                                "BPM  : At Line:9 column:80, Assignment of read only elements is not allowed (CheckIncorrectUsageProcess:ScriptTask)", //$NON-NLS-1$
                                ""), //$NON-NLS-1$

                };
        return markerInfos;
    }

    @Override
    protected String getTestName() {
        return "AceBomFactoryValidationsTest"; //$NON-NLS-1$
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
