/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.sce.tests.validation;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;

import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;
import com.tibco.xpd.n2.test.core.validation.AbstractN2BaseValidationTest;
import com.tibco.xpd.resources.util.ProjectUtil;

/**
 * JUnit suite to make sure that we raise validations against tasks having JS
 * grammar mappings.
 *
 * @author sajain
 * @since Sep 13, 2019
 */
@SuppressWarnings("nls")
public class AceJSMappingsNotSupportedValidationTest extends AbstractN2BaseValidationTest {

    public AceJSMappingsNotSupportedValidationTest() {
        super(true);
    }

    /**
     * AceLocalSignalDataMapperTest
     * 
     * @throws Exception
     */
    public void testAceLocalSignalDataMapperTest() throws Exception {
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

        if (testProject.getName().equals("TestJSMappingNotSupportedValidation")) { //$NON-NLS-1$

            super.configureProject(testProject);
            /*
             * Add references to Global Signal Project.
             */
            IProject projectToREference = getTestProject("SimpleGSDProject"); //$NON-NLS-1$

            try {
                ProjectUtil.addReferenceProject(testProject, projectToREference);
            } catch (CoreException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos() {
        ValidationsTestProblemMarkerInfo[] markerInfos = new ValidationsTestProblemMarkerInfo[] {

                new ValidationsTestProblemMarkerInfo(
                        "/TestJSMappingNotSupportedValidation/Process Packages/TestJSMappingNotSupportedValidation.xpdl",
                        "ace.jsMappingsNotSupported",
                        "_9NXuENX3EemTVcOpVXCVwg",
                        "BPM  : JavaScript grammar mappings are no longer supported. Use Data Mapper grammar instead. (TestJSMappingNotSupportedValidationProcess:CallSubProcess)",
                        "Convert to Data Mapper"),
                new ValidationsTestProblemMarkerInfo(
                        "/TestJSMappingNotSupportedValidation/Process Packages/TestJSMappingNotSupportedValidation.xpdl",
                        "ace.jsMappingsNotSupported",
                        "_W5qikNX3EemTVcOpVXCVwg",
                        "BPM  : JavaScript grammar mappings are no longer supported. Use Data Mapper grammar instead. (TestJSMappingNotSupportedValidationProcess:CatchSignal1)",
                        "Convert to Data Mapper"),
                new ValidationsTestProblemMarkerInfo(
                        "/TestJSMappingNotSupportedValidation/Process Packages/TestJSMappingNotSupportedValidation.xpdl",
                        "ace.jsMappingsNotSupported",
                        "_P77e8tX4EemTVcOpVXCVwg",
                        "BPM  : JavaScript grammar mappings are no longer supported. Use Data Mapper grammar instead. (TestJSMappingNotSupportedValidationProcess:EndEvent3)",
                        "Convert to Data Mapper"),
                new ValidationsTestProblemMarkerInfo(
                        "/TestJSMappingNotSupportedValidation/Process Packages/TestJSMappingNotSupportedValidation.xpdl",
                        "ace.jsMappingsNotSupported",
                        "_M3uuINX4EemTVcOpVXCVwg",
                        "BPM  : JavaScript grammar mappings are no longer supported. Use Data Mapper grammar instead. (TestJSMappingNotSupportedValidationProcess:ErrorEvent)",
                        "Convert to Data Mapper"),
                new ValidationsTestProblemMarkerInfo(
                        "/TestJSMappingNotSupportedValidation/Process Packages/TestJSMappingNotSupportedValidation.xpdl",
                        "ace.jsMappingsNotSupported",
                        "_ndKxINX3EemTVcOpVXCVwg",
                        "BPM  : JavaScript grammar mappings are no longer supported. Use Data Mapper grammar instead. (TestJSMappingNotSupportedValidationProcess:EventSubProcess)",
                        "Convert to Data Mapper"),
                new ValidationsTestProblemMarkerInfo(
                        "/TestJSMappingNotSupportedValidation/Process Packages/TestJSMappingNotSupportedValidation.xpdl",
                        "ace.jsMappingsNotSupported",
                        "_5nWpoNX3EemTVcOpVXCVwg",
                        "BPM  : JavaScript grammar mappings are no longer supported. Use Data Mapper grammar instead. (TestJSMappingNotSupportedValidationProcess:ThrowGlobalGlobalSignal)",
                        "Convert to Data Mapper") };
        return markerInfos;
    }

    @Override
    protected String getTestName() {
        return "AceJSMappingsNotSupportedValidationTest";
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
    protected String getTestPlugInId() {
        return "com.tibco.xpd.sce.test";
    }

    @Override
    protected TestResourceInfo[] getTestResources() {
        TestResourceInfo[] testResources = new TestResourceInfo[] {
                new TestResourceInfo("resources/TestJSMappingNotSupportedError",
                        "SimpleGSDProject/Global Signal Definitions{gsd}/SimpleGSDProject.gsd"),
                new TestResourceInfo("resources/TestJSMappingNotSupportedError",
                        "TestJSMappingNotSupportedValidation/Process Packages{processes}/TestJSMappingNotSupportedValidation.xpdl") };

        return testResources;
    }
}