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
 *
 *
 * @author sajain
 * @since Sep 10, 2019
 */
@SuppressWarnings("nls")
public class AceImplicitTypeConversionTest extends AbstractN2BaseValidationTest {

    public AceImplicitTypeConversionTest() {
        super(false);
    }

    /**
     * AceImplicitTypeConversionTest
     * 
     * @throws Exception
     */
    public void testAceImplicitTypeConversion() throws Exception {
        doTestValidations();
        List<ValidationsTestProblemMarkerInfo> allProblemMarkers =
                getProblemMarkers(getTestFile("TestImplicitTypeConversionBPMProj.xpdl"));

        for (ValidationsTestProblemMarkerInfo eachProblemMarker : allProblemMarkers) {
            if (!(eachProblemMarker.getProblemId().equals("bx.queryWillRunOnLatestOrgModel.issue")
                    || eachProblemMarker.getProblemId().equals("bpmn.notAssociatedCorrelationData")))
            fail(eachProblemMarker.getProblemText() + " should not exist!");
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

        if (testProject.getName().equals("TestImplicitTypeConversionBPMProj")) { //$NON-NLS-1$

            super.configureProject(testProject);
            /*
             * Add references to Global Signal Definition Project.
             */
            IProject projectToREference = getTestProject("TestImplicitTypeConversionGSDProj"); //$NON-NLS-1$

            try {
                ProjectUtil.addReferenceProject(testProject, projectToREference);
            } catch (CoreException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected String getTestName() {
        return "AceImplicitTypeConversionTest";
    }

    @Override
    protected String getTestPlugInId() {
        return "com.tibco.xpd.sce.test";
    }

    @Override
    protected TestResourceInfo[] getTestResources() {
        TestResourceInfo[] testResources = new TestResourceInfo[] {
                new TestResourceInfo("resources/TestImplicitTypeAndScriptConversion",
                        "TestImplicitTypeConversionGSDProj/Global Signal Definitions{gsd}/TestImplicitTypeConversionGSDProj.gsd"),
                new TestResourceInfo("resources/TestImplicitTypeAndScriptConversion",
                        "TestImplicitTypeConversionBPMProj/Process Packages{processes}/TestImplicitTypeConversionBPMProj.xpdl"),
                new TestResourceInfo("resources/TestImplicitTypeAndScriptConversion",
                        "TestImplicitTypeConversionBPMProj/Forms{forms}/Untitled.form") };

        return testResources;
    }

    /**
     * @see com.tibco.xpd.core.test.validations.AbstractBaseValidationTest#getValidationProblemMarkerInfos()
     *
     * @return
     */
    @Override
    protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos() {
        ValidationsTestProblemMarkerInfo[] markerInfos = new ValidationsTestProblemMarkerInfo[] {};
        return markerInfos;
    }
}