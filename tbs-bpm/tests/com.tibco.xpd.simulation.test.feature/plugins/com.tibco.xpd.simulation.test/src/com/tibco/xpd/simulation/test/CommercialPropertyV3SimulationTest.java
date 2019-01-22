/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.simulation.test;

import java.util.Arrays;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;

import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.resources.util.ProjectImporter;

/**
 * Test simulation of a commercial property process XPDL process.
 * 
 * @author Jan Arciuchiewicz
 */
public class CommercialPropertyV3SimulationTest extends BaseSimulationTest {

    private ProjectImporter projectImporter;

    @Override
    protected void setUp() throws Exception {
        projectImporter =
                ProjectImporter
                        .createPluginProjectImporter(SimulationTestActivator.PLUGIN_ID,
                                Arrays.asList("resources/CommercialPropertyV3SimTest.zip")); //$NON-NLS-1$
        assertTrue(projectImporter.performImport());
    }

    @Override
    protected void tearDown() throws Exception {
        TestUtil.waitForJobs();
        assertTrue(projectImporter.performDelete());
    }

    public void testSimulation() throws Exception {
        long timeout = 40L * 60L * 1000L; // 30 min.
        simulateProcess("CommercialPropertyV3SimTest/Process Packages/Commercial Property V3.xpdl", //$NON-NLS-1$
                "MasterV3", //$NON-NLS-1$
                timeout);

        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();

        // make sure that simulation folder is in sync with workspace.
        root.getProject("CommercialPropertyV3SimTest") //$NON-NLS-1$
                .refreshLocal(IResource.DEPTH_INFINITE,
                        new NullProgressMonitor());
        IFolder folder =
                root.getFolder(new Path(
                        "CommercialPropertyV3SimTest/Simulation/ProcessPackage/MasterV3")); //$NON-NLS-1$
        IFile resultFile = getNewestFile(folder);

        assertNotNull(resultFile);
        IFile goldResult =
                root.getFile(new Path(
                        "CommercialPropertyV3SimTest/MasterV3Gold.sim")); //$NON-NLS-1$

        assertTrue(goldResult.exists());

        assertTrue(equals(goldResult, resultFile));

    }
}
