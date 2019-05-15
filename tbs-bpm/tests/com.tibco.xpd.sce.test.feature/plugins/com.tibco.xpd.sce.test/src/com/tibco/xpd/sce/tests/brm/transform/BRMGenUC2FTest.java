/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.sce.tests.brm.transform;

import java.util.Arrays;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;

import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.n2.brm.BRMGenerator;
import com.tibco.xpd.n2.brm.utils.BRMSchemaUtil;
import com.tibco.xpd.n2.resources.util.N2Utils;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.ProjectImporter;
import com.tibco.xpd.resources.util.ProjectUtil;

import junit.framework.TestCase;

/**
 * Revamped for ACE and moved from WM test feature
 * 
 * @author Jan Arciuchiewicz / aallway
 */
public class BRMGenUC2FTest extends TestCase {

    private ProjectImporter projectImporter;

    @Override
    protected void setUp() throws Exception {
        projectImporter =
                ProjectImporter
                        .createPluginProjectImporter("com.tibco.xpd.sce.test", //$NON-NLS-1$
                                Arrays
                                        .asList("resources/BRMModelTransformTest/UC2FWorkItemScripts.zip", //$NON-NLS-1$
                                                "resources/BRMModelTransformTest/UC2ANoneStartEventWithUserTasks.zip")); //$NON-NLS-1$
        assertTrue(projectImporter.performImport());
    }

    public void testWMandWTagainstSchema() throws Exception {
        final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        ResourcesPlugin.getWorkspace().run(new IWorkspaceRunnable() {
            @Override
            public void run(IProgressMonitor monitor) throws CoreException {
                migrateXpdls(root);
            }
        }, new NullProgressMonitor());

        // Wait for builds.
        TestUtil.waitForJobsEx(ResourcesPlugin.FAMILY_AUTO_BUILD);

        IProject project = root.getProject("UC2FWorkItemScripts"); //$NON-NLS-1$
        assertTrue(project.isAccessible());

        IFolder brmFolder = project.getFolder(".brmModules"); //$NON-NLS-1$
        ProjectUtil.createFolder(brmFolder, false, new NullProgressMonitor());

        Map<String, Resource> brmModels = BRMGenerator.getInstance()
                .generateBRMModels(project, "1.0.0.1234567890"); //$NON-NLS-1$

        Resource wmResource =
                brmModels.get(BRMGenerator.WORKMODEL_ARTIFACT_NAME);
        assertTrue(
                BRMGenerator.WORKMODEL_ARTIFACT_NAME
                        + " missing from generated resource map", //$NON-NLS-1$
                        wmResource != null);

        IFile wmFile = brmFolder.getFile(BRMGenerator.WORKMODEL_ARTIFACT_NAME);
        wmResource.setURI(URI.createURI(wmFile.getFullPath().toString()));

        wmResource.save(N2Utils.getDefaultXMLSaveOptions());

        assertTrue(wmFile.exists());
        assertTrue(BRMSchemaUtil
                .validateAgainstBRMXSD(wmFile) == Status.OK_STATUS);

        Resource wtResource =
                brmModels.get(BRMGenerator.WORKTYPE_ARTIFACT_NAME);
        assertTrue(
                BRMGenerator.WORKMODEL_ARTIFACT_NAME
                        + " missing from generated resource map", //$NON-NLS-1$
                        wtResource != null);

        IFile wtFile = brmFolder.getFile(BRMGenerator.WORKTYPE_ARTIFACT_NAME);
        wtResource.setURI(URI.createURI(wtFile.getFullPath().toString()));

        wtResource.save(N2Utils.getDefaultXMLSaveOptions());
        
        assertTrue(wtFile.exists());
        assertTrue(BRMSchemaUtil.validateAgainstBRMXSD(wtFile) == Status.OK_STATUS);

    }

    private void migrateXpdls(IWorkspaceRoot root) {
        WorkingCopy wc;
        wc =
                XpdResourcesPlugin
                        .getDefault()
                        .getWorkingCopy(root
                                .getFile(new Path(
                                        "/UC2FWorkItemScripts/Process Packages/WorkItemScriptsPackage.xpdl"))); //$NON-NLS-1$
        assertNotNull(wc);
        TestUtil.migratePackage(wc);

        wc =
                XpdResourcesPlugin
                        .getDefault()
                        .getWorkingCopy(root
                                .getFile(new Path(
                                        "/UC2ANoneStartEventWithUserTasks/Process Packages/UC2ANoneStartEventWithUserTask.xpdl"))); //$NON-NLS-1$
        assertNotNull(wc);
        TestUtil.migratePackage(wc);

    }

    @Override
    protected void tearDown() throws Exception {
        assertTrue(projectImporter.performDelete());
    }
}
