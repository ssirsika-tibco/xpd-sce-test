/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.validation.wm.globaldata;

import java.util.Collections;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;

import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.validations.AbstractBaseValidationTest;
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;
import com.tibco.xpd.resources.ui.util.PostImportUtil;
import com.tibco.xpd.resources.util.ProjectImporter;
import com.tibco.xpd.resources.util.SubProgressMonitorEx;
import com.tibco.xpd.validation.wm.test.WMValidationTestActivator;

/**
 * CaseServiceValidationTest
 * <p>
 * CaseServiceValidationTest - Test selected validations are correctly raised.
 * </p>
 * <p>
 * Generated by: createBaseTest.javajet
 * </p>
 * 
 * 
 * 
 * @author
 * @since
 */
public class CaseServiceValidationTest extends AbstractBaseValidationTest {

    public CaseServiceValidationTest() {
        super(true);
    }

    /**
     * CaseServiceValidationTest
     * 
     * @throws Exception
     */
    public void testCaseServiceValidationTest() throws Exception {
        doTestValidations();
        return;
    }

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBuildingBaseResourceTest#setUp()
     * 
     * @throws Exception
     */
    @Override
    protected void setUp() throws Exception {

        /*
         * Import business data project before running test
         */
        ProjectImporter importBizDataProj =
                ProjectImporter
                        .createPluginProjectImporter(WMValidationTestActivator.PLUGIN_ID,
                                Collections
                                        .singletonList("resources/CaseServiceValidationTest/CaseServiceData.zip")); //$NON-NLS-1$
        importBizDataProj.performImport();

        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();

        IProject createdBizDataProject = root.getProject("CaseServiceData"); //$NON-NLS-1$

        /*
         * Perform post import task so that if the bom project or resource is
         * older version it can be migrated
         */
        PostImportUtil
                .getInstance()
                .performPostImportTasks(Collections.singletonList(createdBizDataProject),
                        SubProgressMonitorEx
                                .createMainProgressMonitor(new NullProgressMonitor(),
                                        1));

        /* call super.setUp so that the test project is created */
        super.setUp();

        IProject testProject = getTestProject("CaseServiceValidationTest");//$NON-NLS-1$

        /* add project references on test project to biz data project */
        if (testProject != null && testProject.exists()
                && createdBizDataProject != null) {

            IProjectDescription description = testProject.getDescription();
            if (description != null) {

                IProject[] referencedProjects =
                        description.getReferencedProjects();
                for (IProject ref : referencedProjects) {

                    if (ref.equals(createdBizDataProject)) {
                        return;
                    }
                }
                IProject[] newList =
                        new IProject[referencedProjects.length + 1];
                System.arraycopy(referencedProjects,
                        0,
                        newList,
                        0,
                        referencedProjects.length);
                newList[referencedProjects.length] = createdBizDataProject;
                description.setReferencedProjects(newList);
                testProject.setDescription(description,
                        new NullProgressMonitor());
                testProject.touch(new NullProgressMonitor());
                buildAndWait();
            }
        }

        return;
    }

    @Override
    protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos() {
        ValidationsTestProblemMarkerInfo[] markerInfos =
                new ValidationsTestProblemMarkerInfo[] {

                        new ValidationsTestProblemMarkerInfo(
                                "/CaseServiceValidationTest/Process Packages/CaseServiceValidationTest.xpdl", //$NON-NLS-1$ 
                                "wm.caseservice.CaseClassCaseStateAttributeNotFound", //$NON-NLS-1$ 
                                "_4w9xcDKaEeSqWIBOgQDirA", //$NON-NLS-1$ 
                                "Work Manager  : The case class 'com.example.caseservicedata::Claim' selected for a case action must have a case state attribute (of text enumeration type). (ClaimViewCaseCaseClassWithNoCaseState)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/CaseServiceValidationTest/Process Packages/CaseServiceValidationTest.xpdl", //$NON-NLS-1$ 
                                "wm.caseservice.NoSpecificCaseStateIsSelected", //$NON-NLS-1$ 
                                "_7jnNUDKcEeSqWIBOgQDirA", //$NON-NLS-1$ 
                                "Work Manager  : No specific case states selected. (Claim2ViewCase_NoSpecificState)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/CaseServiceValidationTest/Process Packages/CaseServiceValidationTest.xpdl", //$NON-NLS-1$ 
                                "wm.caseservice.InvalidCaseStateAttributeFoundInCaseService", //$NON-NLS-1$ 
                                "_lFW3kDKcEeSqWIBOgQDirA", //$NON-NLS-1$ 
                                "Work Manager  : Case state attribute value(s) selected for the case action don't exist. (Claim2ViewCaseCaseStateAttrDoesntExist)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/CaseServiceValidationTest/Process Packages/CaseServiceValidationTest.xpdl", //$NON-NLS-1$ 
                                "wm.caseservice.MultipleParamsNotAllowedInCaseService", //$NON-NLS-1$ 
                                "_M8q1wDKbEeSqWIBOgQDirA", //$NON-NLS-1$ 
                                "Work Manager  : Case action must have a single input parameter (that is case class reference type 'com.example.caseservicedata::Order'). (OrderViewCaseWithMoreThanOneParam)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/CaseServiceValidationTest/Process Packages/CaseServiceValidationTest.xpdl", //$NON-NLS-1$ 
                                "wm.caseservice.paramInCaseServiceMustBeCaseClassCaseRefType", //$NON-NLS-1$ 
                                "_od1EvjKbEeSqWIBOgQDirA", //$NON-NLS-1$ 
                                "Work Manager  : Case action must have a single input parameter (that is case class reference type 'com.example.caseservicedata::Order'). (OrderViewCaseWithNoCaseRefType:OrderRef)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/CaseServiceValidationTest/Process Packages/CaseServiceValidationTest.xpdl", //$NON-NLS-1$ 
                                "wm.caseservice.NoCaseClassSelectedForCaseService", //$NON-NLS-1$ 
                                "_P5uK0DKdEeSqWIBOgQDirA", //$NON-NLS-1$ 
                                "Work Manager  : No case class type selected for case action. (CaseServiceNoCaseClassSelected)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/CaseServiceValidationTest/Process Packages/CaseServiceValidationTest.xpdl", //$NON-NLS-1$ 
                                "wm.caseservice.SingleParamInCaseServiceRequired", //$NON-NLS-1$ 
                                "_ZmwiwDKbEeSqWIBOgQDirA", //$NON-NLS-1$ 
                                "Work Manager  : Case action must have a single input parameter (that is case class reference type 'com.example.caseservicedata::Order'). (OrderViewCaseData2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                };
        return markerInfos;
    }

    @Override
    protected String getTestName() {
        return "CaseServiceValidationTest"; //$NON-NLS-1$
    }

    @Override
    protected String getTestPlugInId() {
        return "com.tibco.xpd.validation.wm.test"; //$NON-NLS-1$
    }

    @Override
    protected TestResourceInfo[] getTestResources() {
        TestResourceInfo[] testResources =
                new TestResourceInfo[] { new TestResourceInfo(
                        "resources/CaseServiceValidationTest", "CaseServiceValidationTest/Process Packages{processes}/CaseServiceValidationTest.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
                };

        return testResources;
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

}
