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
import com.tibco.xpd.resources.precompile.EnablePreCompileRunner;
import com.tibco.xpd.resources.precompile.PreCompileContributorManager;
import com.tibco.xpd.resources.ui.util.PostImportUtil;
import com.tibco.xpd.resources.util.ProjectImporter;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.SubProgressMonitorEx;

/**
 * Test to check if precompile is enabled successfully and the required
 * artifacts are preserved under .precompiled folder
 * 
 * @author bharge
 * @since 18 Jun 2015
 */
public class N2_10_EnablePreCompileTest extends AbstractN2BaseResourceTest {

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
                                        .singletonList("resources/N2_10_EnablePreCompileTest/N2_10_EnablePreCompileTest.zip")); //$NON-NLS-1$
        importBizDataProj.performImport();

        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();

        testProject = root.getProject("N2_10_EnablePreCompileTest"); //$NON-NLS-1$

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
        /* Ensure that project is not already precompiled */
        boolean isPrecompiled = ProjectUtil.isPrecompiledProject(testProject);
        assertFalse("Project is already precompiled", isPrecompiled); //$NON-NLS-1$

        /* Execute the enable precompile */
        EnablePreCompileRunner enablePreCompileRunner =
                new EnablePreCompileRunner(projects);
        enablePreCompileRunner.run(new NullProgressMonitor());

        /* Check if the project is pre-compiled */
        isPrecompiled = ProjectUtil.isPrecompiledProject(testProject);
        if (!isPrecompiled) {

            fail("Project is not pre-compiled. Precompile Enable failed"); //$NON-NLS-1$
        }

        /*
         * Check if the .precompiled folder is created and has the right
         * contents under it
         */
        IFolder preCompiledFolder =
                testProject.getFolder(PreCompileContributorManager
                        .getInstance().PRECOMPILE_OUTPUTFOLDER_NAME);
        if (!preCompiledFolder.isAccessible()) {

            fail(".precompiledFolder is not available. Precompile Enable failed"); //$NON-NLS-1$
        }

        /* Check if the xsd files are preserved */
        IFolder precompileBom2XsdFolder =
                preCompiledFolder
                        .getFolder(Bom2XsdUtil.PRECOMPILE_BOM_2_XSD_FOLDER);
        if (!precompileBom2XsdFolder.isAccessible()) {

            fail("bom2xsd precompile failed or not have been preserved"); //$NON-NLS-1$
        }
        int numOfBom2XsdFiles = precompileBom2XsdFolder.members().length;
        if (numOfBom2XsdFiles != 1) {

            fail("bom2xsd have not been preserved"); //$NON-NLS-1$
        }

        /* Check if the bom jars are preserved */
        IFolder precompileBomJarsFolder =
                preCompiledFolder
                        .getFolder(N2PENamingUtils.PRECOMPILE_BOM_JARS_CACHE_FOLDER);
        if (!precompileBomJarsFolder.isAccessible()) {

            fail("bom jars precompile failed or not have been preserved"); //$NON-NLS-1$
        }
        int numOfBomJars = precompileBomJarsFolder.members().length;
        if (numOfBomJars != 3) {

            fail("bom jars have not been preserved"); //$NON-NLS-1$
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
