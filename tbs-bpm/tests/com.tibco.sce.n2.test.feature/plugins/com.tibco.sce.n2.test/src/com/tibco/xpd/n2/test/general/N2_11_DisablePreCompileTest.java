/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.test.general;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;

import com.tibco.xpd.bom.xsdtransform.utils.Bom2XsdUtil;
import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.n2.daa.utils.N2PENamingUtils;
import com.tibco.xpd.n2.test.core.AbstractN2BaseResourceTest;
import com.tibco.xpd.resources.precompile.DisablePreCompileRunner;
import com.tibco.xpd.resources.precompile.PreCompileContributorManager;
import com.tibco.xpd.resources.ui.util.PostImportUtil;
import com.tibco.xpd.resources.util.ProjectImporter;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.SubProgressMonitorEx;

/**
 * Test to check if disable precompile is successful and the .precompiled folder
 * is empty
 * 
 * @author bharge
 * @since 18 Jun 2015
 */
public class N2_11_DisablePreCompileTest extends AbstractN2BaseResourceTest {

    IProject testProject;

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
                        .createPluginProjectImporter("com.tibco.xpd.n2.test", //$NON-NLS-1$
                                Collections
                                        .singletonList("resources/N2_11_DisablePreCompileTest/N2_11_DisablePreCompileTest.zip")); //$NON-NLS-1$
        importBizDataProj.performImport();

        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();

        testProject = root.getProject("N2_11_DisablePreCompileTest"); //$NON-NLS-1$

        /*
         * Perform post import task so that if the bom project or resource is
         * older version it can be migrated
         */
        PostImportUtil
                .getInstance()
                .performPostImportTasks(Collections.singletonList(testProject),
                        SubProgressMonitorEx
                                .createMainProgressMonitor(new NullProgressMonitor(),
                                        1));
    }

    public void testPreCompileEnabled() throws Exception {

        /* Get the project */
        List<IProject> projects = new ArrayList<>();
        projects.add(testProject);
        /* Ensuring that it is a pre-compiled project that we will disable now */
        boolean isPrecompiled = ProjectUtil.isPrecompiledProject(testProject);
        assertTrue("Project is precompiled", isPrecompiled); //$NON-NLS-1$

        /* Execute the disable precompile */
        DisablePreCompileRunner disablePreCompileRunner =
                new DisablePreCompileRunner(projects);
        disablePreCompileRunner.run(new NullProgressMonitor());

        /* Check if the project is still pre-compiled */
        isPrecompiled = ProjectUtil.isPrecompiledProject(testProject);
        if (isPrecompiled) {

            fail("Project is still pre-compiled. Disable Precompile failed"); //$NON-NLS-1$
        }

        /*
         * Check if clean build is run and xsd files are available under default
         * bom2xsd folder
         */
        IFolder defaultBom2XsdFolder =
                testProject.getFolder(Bom2XsdUtil.DEFAULT_BOM_2_XSD_FOLDER);
        if (defaultBom2XsdFolder.isAccessible()) {

            int numOfBom2XsdFiles = defaultBom2XsdFolder.members().length;
            if (numOfBom2XsdFiles != 1) {

                fail("Disable precompile failed to clean build the project. Xsd files are not re-generated"); //$NON-NLS-1$
            }
        }

        /*
         * Get the .precompiled folder to check if there are still any artifacts
         * (bom jars) preserved under it after precompile is disabled
         */
        IFolder preCompiledFolder =
                testProject.getFolder(PreCompileContributorManager
                        .getInstance().PRECOMPILE_OUTPUTFOLDER_NAME);

        /* Check if the bom jars are still preserved under precompiled folder */
        IFolder precompileBomJarsFolder =
                preCompiledFolder
                        .getFolder(N2PENamingUtils.PRECOMPILE_BOM_JARS_CACHE_FOLDER);
        if (precompileBomJarsFolder.isAccessible()) {

            int numOfBomJars = precompileBomJarsFolder.members().length;
            if (numOfBomJars != 0) {

                fail("bom jars are still preserved under precompiled folder"); //$NON-NLS-1$
            }
        }

    }

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#getTestName()
     * 
     * @return
     */
    @Override
    protected String getTestName() {

        return "EnablePreCompileTest"; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#getTestResources()
     * 
     * @return
     */
    @Override
    protected TestResourceInfo[] getTestResources() {

        return null;
    }

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#getTestPlugInId()
     * 
     * @return
     */
    @Override
    protected String getTestPlugInId() {

        return "com.tibco.xpd.n2.test"; //$NON-NLS-1$
    }

}
