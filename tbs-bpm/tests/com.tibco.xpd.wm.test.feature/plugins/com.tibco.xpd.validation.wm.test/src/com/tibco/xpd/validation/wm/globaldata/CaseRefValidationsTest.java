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
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;
import com.tibco.xpd.resources.ui.util.PostImportUtil;
import com.tibco.xpd.resources.util.ProjectImporter;
import com.tibco.xpd.resources.util.SubProgressMonitorEx;
import com.tibco.xpd.validation.wm.test.AbstractJavaScriptValidatorTest;
import com.tibco.xpd.validation.wm.test.WMValidationTestActivator;

/**
 * CaseRefValidationsTest
 * <p>
 * CaseRefValidationsTest - Test selected validations are correctly raised.
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
public class CaseRefValidationsTest extends AbstractJavaScriptValidatorTest {

    public CaseRefValidationsTest() {
        super(true);
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
                                        .singletonList("resources/CaseRefValidations/BDProj.zip")); //$NON-NLS-1$
        importBizDataProj.performImport();

        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();

        IProject createdBizDataProject = root.getProject("BDProj"); //$NON-NLS-1$

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

        IProject testProject = getTestProject("CaseRefValidations");//$NON-NLS-1$

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

    /**
     * CaseRefValidationsTest
     * 
     * @throws Exception
     */
    public void testCaseRefValidationsTest() throws Exception {
        doTestValidations();
        return;
    }

    @Override
    protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos() {
        ValidationsTestProblemMarkerInfo[] markerInfos =
                new ValidationsTestProblemMarkerInfo[] {

                        new ValidationsTestProblemMarkerInfo(
                                "/CaseRefValidations/Process Packages/CaseRefValidations.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_zEVm0JjAEeOiZ41mVqvtaA", //$NON-NLS-1$ 
                                "Process Manager 2.x : At Line:8 column:85, DQL Query Error: Class Customer has no property called orderLines  (CaseRefValidationsProcess:ScriptTask)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/CaseRefValidations/Process Packages/CaseRefValidations.xpdl", //$NON-NLS-1$ 
                                "bx.warning.validateScriptTask", //$NON-NLS-1$ 
                                "_zEVm0JjAEeOiZ41mVqvtaA", //$NON-NLS-1$ 
                                "Process Manager 2.x : At Line:4 column:93, DQL Query Warning:Conditions based on an attribute (quantity) that is neither a case identifier nor searchable may harm performance (CaseRefValidationsProcess:ScriptTask)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                };
        return markerInfos;
    }

    @Override
    protected String getTestName() {
        return "CaseRefValidationsTest"; //$NON-NLS-1$
    }

    @Override
    protected String getTestPlugInId() {
        return "com.tibco.xpd.validation.wm.test"; //$NON-NLS-1$
    }

    @Override
    protected TestResourceInfo[] getTestResources() {
        TestResourceInfo[] testResources =
                new TestResourceInfo[] { new TestResourceInfo(
                        "resources/CaseRefValidations", "CaseRefValidations/Process Packages{processes}/CaseRefValidations.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
                };

        return testResources;
    }

}
